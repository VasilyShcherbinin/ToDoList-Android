package com1032.cw1.vs00162.vs00162_todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Class to display a ListView item in a separate activity.
 */
public class ItemDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameText = (TextView) findViewById(R.id.itemDetailTaskName);
        TextView descText = (TextView) findViewById(R.id.itemDetailTaskDescription);
        TextView dateCreatedText = (TextView) findViewById(R.id.itemDetailTaskDateCreated);
        TextView dateDueText = (TextView) findViewById(R.id.itemDetailTaskDateDue);

        //Receiving String objects from ListActivity.
        String name = getIntent().getStringExtra("name");
        String desc = getIntent().getStringExtra("desc");
        String dateCreated = getIntent().getStringExtra("date_created");
        String dateDue = getIntent().getStringExtra("date_due");

        //Setting them in the activity TextViews.
        nameText.setText("Task Name: " + name);
        descText.setText("Description: " + desc);
        dateCreatedText.setText("Date created: " + dateCreated);
        dateDueText.setText("Date due: " + dateDue);
    }
}