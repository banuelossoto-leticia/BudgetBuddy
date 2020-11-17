package com.example.cecs448;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder>
{
    private ArrayList<Goal> goals;
    private Context context;

    //variable that will make each item in the recyclerview clickable
    private GoalAdapter.OnItemClickListener mListener;

    //if your recyclerview items have multiple functions, you add functionality for each in this interface
    public interface OnItemClickListener
    {
        //will expand review info
        void onItemClick(int position);
    }

    public void setOnItemClickListener(GoalAdapter.OnItemClickListener listener)
    {
        mListener=listener;
    }

    @NonNull
    @Override
    public GoalAdapter.GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_item, parent, false);
        GoalAdapter.GoalViewHolder ps_vh = new GoalAdapter.GoalViewHolder(view, mListener);
        return ps_vh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalAdapter.GoalViewHolder holder, int position)
    {
        Goal curr_goal = goals.get(position);
        float currentExpense;

        //sets holder fields with review attributes
        holder.name.setText(curr_goal.getCategory());
        holder.limit.setText("$"+curr_goal.getLimit());

        //need to calculate the expenses for that goal to determine the right progress icon
        currentExpense=calculateProgress(curr_goal.getCategory(),curr_goal.getStartDate());
        holder.expenses.setText("Progress: $"+currentExpense);

        if(Float.parseFloat(curr_goal.getLimit())>currentExpense)
        {
            holder.progress.setImageResource(R.drawable.on_track);
        }
        else
        {
            holder.progress.setImageResource(R.drawable.overspent);
        }
    }

    private float calculateProgress(String category, String date)
    {
        Scanner read = null;
        float expense=0;

        try
        {
            read = new Scanner(new File(context.getFilesDir()+"expenses.txt"));
        }
        catch (Exception e)
        {
            System.out.println("");
        }

        if(read!=null)
        {
            while (read.hasNext())
            {
                String cur = read.nextLine();
                //splits string using delimiter (ignoring the commas in quotes)
                String[] curArr = cur.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //expense info
                String amountSpentString = curArr[0];
                String expense_category = curArr[1];
                String expense_date = curArr[2];
                String note = curArr[3];

                //expense date info (month,year,day)
                String exp_year=expense_date.substring(0,4);
                String exp_month=expense_date.substring(5,7);

                //goal date info
                String goal_year=date.substring(0,4);
                String goal_month=date.substring(5,7);

                //if the goal category, year, and month matches with the expenses for that year and month...
                if(expense_category.equals(category) && exp_year.equals(goal_year) && exp_month.equals(goal_month))
                {
                    expense+=Float.parseFloat(amountSpentString);
                }
            }
            read.close();
        }

        return expense;
    }

    @Override
    public int getItemCount()
    {
        return goals.size();
    }

    public GoalAdapter(ArrayList<Goal> goals, Context context) { this.goals = goals; this.context=context;}

    public static class GoalViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView limit;
        public TextView expenses;
        private ImageView progress;


        public GoalViewHolder(@NonNull View itemView, final GoalAdapter.OnItemClickListener listener)
        {
            super(itemView);
            //binding xml elements to members of the class
            name = itemView.findViewById(R.id.name);
            limit = itemView.findViewById(R.id.amount);
            progress=itemView.findViewById(R.id.progress);
            expenses=itemView.findViewById(R.id.goal_progress);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(listener !=null)
                    {
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}



