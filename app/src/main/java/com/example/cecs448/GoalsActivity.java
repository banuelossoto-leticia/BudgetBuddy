package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GoalsActivity extends AppCompatActivity
{

    private ConstraintLayout goal_list;
    private ConstraintLayout pop_up;
    private TextView no_goals, main_goal, edit_goal_name, curr_limit, edit_limit, goal_category;
    private EditText new_limit;
    private Button edit_goal,submit_edit,back, delete;
    private RecyclerView goalsRecycler;
    private ImageView createGoal;
    private GoalAdapter goalsAdapter=null;
    private ArrayList<Goal> goals;
    Goal selected_goal=null;

    private void loadGoals()
    {
        Scanner read = null;

        try
        {
            read = new Scanner(new File(getFilesDir()+"goals.txt"));
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
                String curArr[] = cur.split(",");

                String limit = curArr[0];
                String name = curArr[1];
                String timestamp = curArr[2];

                Goal goal = new Goal(name, limit, timestamp);

                goals.add(goal);
            }
        }
    }

    public void deleteFile()
    {
        File file = new  File(getFilesDir()+"goals.txt");

        if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
    }

    public void rewriteFile()
    {
        try
        {
            //writes all the goals to the file
            for(int i=0;i<goals.size();i++)
            {
                //define the file to save
                File file = new File(getFilesDir() + "goals.txt");
                //true indicates to append instead of overwrite
                FileOutputStream fileout = new FileOutputStream(file, true);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                //write goal info. Placing the goal name and limit, separated by a comma as a delimiter
                outputWriter.write(goals.get(i).getLimit() + "," + goals.get(i).getCategory() + "," + goals.get(i).getStartDate() + "\n");
                outputWriter.close();
            }

            //successful write toast
            Toast.makeText(getApplicationContext(), "Text file Saved to!" + getFilesDir(), Toast.LENGTH_LONG).show();
        }
        catch (java.io.IOException e)
        {
            //do something if an IOException occurs.
            Toast.makeText(getApplicationContext(),"ERROR - Text could't be added",Toast.LENGTH_LONG).show();
        }
    }

    public void checkIfNoGoalsExist()
    {
        if(goals.isEmpty())
        {
            no_goals.setVisibility(View.VISIBLE);
            goalsRecycler.setVisibility(View.INVISIBLE);
        }
        else
        {
            no_goals.setVisibility(View.INVISIBLE);
            goalsRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        //allocating memory for goals
        goals=new ArrayList<>();

        //load goals from txt file
        loadGoals();

        //binds xml
        goal_list=findViewById(R.id.recycler_background);
        pop_up=findViewById(R.id.goal_popup);
        no_goals=findViewById(R.id.no_goals_error);
        createGoal=findViewById(R.id.create_goal);
        main_goal=findViewById(R.id.main_name);
        edit_goal_name=findViewById(R.id.edit_goal_name);
        goalsRecycler=findViewById(R.id.goals_recycler);
        edit_goal=findViewById(R.id.edit_goal);
        submit_edit=findViewById(R.id.submit_button);
        back=findViewById(R.id.back_button);
        curr_limit=findViewById(R.id.goal_lim);
        edit_limit=findViewById(R.id.new_lim);
        goal_category=findViewById(R.id.goal_category);
        new_limit=findViewById(R.id.new_limit);
        delete=findViewById(R.id.delete_button);

        //checks whether to display the error message or not
        checkIfNoGoalsExist();

        //defining properties of recycler
        goalsRecycler.setHasFixedSize(true);
        goalsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //using our arraylist of Goals to initialize the adapter
        goalsAdapter=new GoalAdapter(goals,this);

        //using the adapter in our recyclerview
        goalsRecycler.setAdapter(goalsAdapter);

        //closes the popup for a goal
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goal_list.setVisibility(View.VISIBLE);
                pop_up.setVisibility(View.INVISIBLE);
                createGoal.setVisibility(View.VISIBLE);
            }
        });

        //submits edits to a goal
        submit_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //replace the old limit amount of the goal
                selected_goal.setLimit(new_limit.getText().toString());
                //deletes the goals txt file
                deleteFile();
                //rewrites it to save the edit
                rewriteFile();
                //opens the recycler and hides the popup
                goalsAdapter.notifyDataSetChanged();
                goal_list.setVisibility(View.VISIBLE);
                pop_up.setVisibility(View.INVISIBLE);
                createGoal.setVisibility(View.VISIBLE);
            }
        });

        //deletes a goal
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //deletes the goal from the list
                goals.remove(selected_goal);
                //deletes the goals txt file
                deleteFile();
                //rewrites it to save the edit
                rewriteFile();
                //opens the recycler and hides the popup
                goalsAdapter.notifyDataSetChanged();
                goal_list.setVisibility(View.VISIBLE);
                pop_up.setVisibility(View.INVISIBLE);
                createGoal.setVisibility(View.VISIBLE);
                //checks whether to display the error message or not
                checkIfNoGoalsExist();
            }
        });

        //allows the limit of a goal to be edited
        edit_goal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new_limit.setEnabled(true);
                delete.setVisibility(View.VISIBLE);
                submit_edit.setVisibility(View.VISIBLE);
                edit_goal.setVisibility(View.INVISIBLE);
                edit_goal_name.setVisibility(View.VISIBLE);
                main_goal.setVisibility(View.INVISIBLE);
                curr_limit.setVisibility(View.INVISIBLE);
                edit_limit.setVisibility(View.VISIBLE);
            }
        });

        //handling clicks on each item
        goalsAdapter.setOnItemClickListener(new GoalAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                edit_goal.setVisibility(View.VISIBLE);
                selected_goal=goals.get(position);
                new_limit.setText(selected_goal.getLimit());
                new_limit.setEnabled(false);
                goal_category.setText(selected_goal.getCategory());
                goal_category.setEnabled(false);
                delete.setVisibility(View.INVISIBLE);
                curr_limit.setVisibility(View.VISIBLE);
                edit_limit.setVisibility(View.INVISIBLE);
                goal_list.setVisibility(View.INVISIBLE);
                pop_up.setVisibility(View.VISIBLE);
                edit_goal_name.setVisibility(View.INVISIBLE);
                main_goal.setVisibility(View.VISIBLE);
                createGoal.setVisibility(View.INVISIBLE);
                submit_edit.setVisibility(View.INVISIBLE);
            }
        });

        //initiates activity for creating a new goal
        createGoal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),CreateGoalActivity.class));
            }
        });

        //this is the button for the pieChartActivity from AddTransactionActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton);
        pieChartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GoalsActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from AddTransactionActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton);
        transactionListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GoalsActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the HomeScreenActivity from addBudgetActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GoalsActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}
