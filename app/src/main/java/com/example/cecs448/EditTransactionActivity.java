package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class EditTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        //for the two place decimal
        DecimalFormat df = new DecimalFormat("0.00");

        //to get the index of the transaction that the user clicks on
        final Bundle bundle = getIntent().getExtras();
        final int transactionIndex = bundle.getInt("indexToBeEdited");

        //creating the drop down menu in order to use it in code.
        final Spinner categoriesDropDownMenu = (Spinner) findViewById(R.id.categoryDropDownMenu2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, HomeScreenActivity.categories);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        categoriesDropDownMenu.setAdapter(adapter);

        //creating the text views and edit texts to show if the user does not input all information needed to edit a transaction
        final TextView invalidInputAmountText = (TextView) findViewById(R.id.invalidInputAmountText2);
        final TextView oppsText = (TextView) findViewById(R.id.oppsText2);
        final TextView noNoteInputText = (TextView) findViewById(R.id.noNoteInputText2);
        final EditText noteTextField = (EditText) findViewById(R.id.noteTextField2);
        final EditText amountTextField = (EditText) findViewById(R.id.amountTextField2);

        //buttons for the popup window
        final ImageButton submitButtonPopUp = (ImageButton) findViewById(R.id.submit_button3);
        final ImageButton backButtonPopup = (ImageButton) findViewById(R.id.back_button3);

        //updates the text to the correct TextView/EditText
        amountTextField.setText(df.format(HomeScreenActivity.transactions.get(transactionIndex).getAmount()));
        noteTextField.setText(HomeScreenActivity.transactions.get(transactionIndex).getNote());
        //saves the index of the category name from categories
        int categoryIndex = HomeScreenActivity.categories.indexOf(HomeScreenActivity.transactions.get(transactionIndex).getCategory());
        categoriesDropDownMenu.setSelection(categoryIndex);

        //this is the button for the HomeScreenActivity from EditTransactionActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton7);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EditTransactionActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        //this is the button for the PieChartActivity from EditTransactionActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton7);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EditTransactionActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this is the button for the back_Button2
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button2);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EditTransactionActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this is the submit_button2
        ImageButton submitButton = (ImageButton) findViewById(R.id.submit_button2);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                oppsText.setVisibility(View.INVISIBLE);
                noNoteInputText.setVisibility(View.INVISIBLE);
                invalidInputAmountText.setVisibility(View.INVISIBLE);

                //checking to see that edit text is filled out.
                if((TextUtils.isEmpty(noteTextField.getText().toString())) || (TextUtils.isEmpty(amountTextField.getText().toString()))){
                    //oppsText becomes visible because a user did not meet requirements.
                    oppsText.setVisibility(View.VISIBLE);
                    //checks to see which text field was invalid
                    if(TextUtils.isEmpty(noteTextField.getText().toString())){
                        noNoteInputText.setVisibility(View.VISIBLE);
                    }
                    if((TextUtils.isEmpty(amountTextField.getText().toString()))){
                        invalidInputAmountText.setVisibility(View.VISIBLE);
                    }

                }else{
                    //changes all errors to be invisible again
                    oppsText.setVisibility(View.INVISIBLE);
                    noNoteInputText.setVisibility(View.INVISIBLE);
                    invalidInputAmountText.setVisibility(View.INVISIBLE);

                    //updates the transaction in the arrayList
                    HomeScreenActivity.transactions.get(transactionIndex).setCategory(HomeScreenActivity.categories.get(categoriesDropDownMenu.getSelectedItemPosition()));
                    //updates the amount in the arrayList
                    HomeScreenActivity.transactions.get(transactionIndex).setAmount(Double.parseDouble(amountTextField.getText().toString()));
                    //updates the note in the arrayList
                    HomeScreenActivity.transactions.get(transactionIndex).setNote(noteTextField.getText().toString());

                    //moves onto the next page
                    Intent intent = new Intent(EditTransactionActivity.this, TransactionUpdatedConfirmationActivity.class);
                    startActivity(intent);

                    //TODO: update the transaction into the textfile and save into text file
                }
            }
        });

        //this is the clickable create new category
        TextView newCategory = (TextView) findViewById(R.id.createNewCategoryTextView);
        newCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onCreateNewCategoryClick(v);
            }
        });
    }

    public void onCreateNewCategoryClick(View v){
        //inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.create_new_category_popup, null);

        //creates the popup window
        //int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; //lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, 1280, 720, focusable);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        //this is the button for the submit_button3
        /**ImageButton submitButtonPopUp = (ImageButton) findViewById(R.id.submit_button3);
        submitButtonPopUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /**
                //creates the objects to be usable
                final TextView errorTextView = (TextView) findViewById(R.id.errorTextView);
                final EditText newCategoryNameEditText = (EditText) findViewById(R.id.newCategoryNameEditText);

                //sets it to be invisible
                errorTextView.setVisibility(View.INVISIBLE);

                //checks to see that the new category name is not already in categories.
                //category does not exist then it is added into categories
                if(!HomeScreenActivity.categories.contains(newCategoryNameEditText.toString())){
                    //sets it to be invisible
                    errorTextView.setVisibility(View.INVISIBLE);
                    //adds the new category into categories
                    HomeScreenActivity.categories.add(newCategoryNameEditText.toString().toUpperCase());

                    //exits the popup menu
                    popupWindow.dismiss();

                //category already exists in categories
                }else{
                    //sets it to be visible
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }
        });
         */
    }
}