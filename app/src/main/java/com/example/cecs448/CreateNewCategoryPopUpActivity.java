package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class CreateNewCategoryPopUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_category_pop_up);

        final TextView errorTextView = (TextView) findViewById(R.id.errorTextView);
        final EditText categoryNameEditText = (EditText) findViewById(R.id.categoryNameEditText);

        //this is the button for the back button in popup menu
        ImageButton backButton = (ImageButton) findViewById(R.id.backButtonPop);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closes the activity
                finish();
            }
        });

        //this is the button for the submit button in popup menu
        ImageButton submitButton = (ImageButton) findViewById(R.id.submitButtonPop);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                errorTextView.setVisibility(View.INVISIBLE);

                /**checks to see if the input is already contained in categories or if its null or if its empty. If it is then it will display an error message.
                 * if the input is not empty not "" or it is not in categories then the new category will be put into categories and saved on the categories.txt*/
                //does not meet requirements
                if((categoryNameEditText.getText().toString() == "") || (TextUtils.isEmpty(categoryNameEditText.getText().toString()) || HomeScreenActivity.categories.contains(categoryNameEditText))){

                    //error is displayed
                    errorTextView.setVisibility(View.VISIBLE);
                }else{
                    //adds the new category into categories
                    HomeScreenActivity.categories.add(categoryNameEditText.getText().toString().toUpperCase());

                    //updates the text file categories.txt
                    updateCategoriesTextFile(categoryNameEditText.getText().toString().toUpperCase());

                    //closes the activity
                    finish();
                }
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.2));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private void updateCategoriesTextFile(String category){
        File file;

        try{
            //define the file to save
            file = new File(getFilesDir()+"categories.txt");
            //true indicates to append instead of overwrite
            FileOutputStream fileOut = new FileOutputStream(file, true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);

            //writes new category into the file
            outputWriter.write(category + "\n");

            //closes the file
            outputWriter.close();

        } catch (java.io.IOException e) {
            //do something if an IOException occurs.
            Toast.makeText(getApplicationContext(),"ERROR with adding to categories.txt",Toast.LENGTH_LONG).show();
        }
    }
}