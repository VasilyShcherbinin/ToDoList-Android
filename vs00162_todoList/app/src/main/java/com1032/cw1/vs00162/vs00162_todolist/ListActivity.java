package com1032.cw1.vs00162.vs00162_todolist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;

/**
 * Responsible for launching and operating the ListActivity after launch from MainActivity.
 * Contains the main functionality of the app.
 */
public class ListActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;
    private static final int CM_UPDATE_ID = 2;
    private ListView listViewData;
    private SQLiteDB db;
    private SimpleCursorAdapter simpleCursorAdapter;
    private Cursor cursor;
    private int day;
    private int month;
    private int year;
    private Calendar cal;
    private EditText editDateCreated;
    private EditText editDateDue;

    /**
     * Called when ListActivity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** Extract String objects from a created ListView item using a Cursor.
         *  Send them to ItemDetail activity using Intent.putExtra() method.
         */
        listViewData = (ListView) findViewById(R.id.listViewData);

        //Implementing a OnItemClickListener for an item in the ListView.
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = ((SimpleCursorAdapter) listViewData.getAdapter()).getCursor();
                cursor.moveToPosition(position);

                //Extracting String objects from a created ListView item using a Cursor.
                String clickedName = cursor.getString(cursor.getColumnIndex("txt"));
                String clickedDesc = cursor.getString(cursor.getColumnIndex("des"));
                String clickedDateCreated = cursor.getString(cursor.getColumnIndex("date_created"));
                String clickedDateDue = cursor.getString(cursor.getColumnIndex("date_due"));

                //Creating Intent to launch ItemDetail activity.
                Intent itemDetail = new Intent(ListActivity.this, ItemDetail.class);

                itemDetail.putExtra("name", clickedName);
                itemDetail.putExtra("desc", clickedDesc);
                itemDetail.putExtra("date_created", clickedDateCreated);
                itemDetail.putExtra("date_due", clickedDateDue);
                startActivity(itemDetail);
            }
        });

        //Opening connection to SQLite Database.
        db = new SQLiteDB(this);
        db.open();

        // Receiving cursor
        cursor = db.getAllData();
        //noinspection deprecation
        startManagingCursor(cursor);

        // Forming correspondence columns.
        String[] from = new String[]{SQLiteDB.COLUMN_TEXT, SQLiteDB.COLUMN_DES, SQLiteDB.COLUMN_DATE_CREATED, SQLiteDB.COLUMN_DATE_DUE};
        int[] to = new int[]{R.id.itemText, R.id.itemDescription, R.id.itemDateCreated, R.id.itemDateDue};

        // Creating a SimpleCursorAdapter and setting it to ListView.
        //noinspection deprecation
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to);
        listViewData = (ListView) findViewById(R.id.listViewData);
        listViewData.setAdapter(simpleCursorAdapter);

        // Adding a ContextMenu to ListView.
        registerForContextMenu(listViewData);
    }

    /**
     * Forming the ContextMenu.
     */
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CM_UPDATE_ID, 0, R.string.update_record);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    /**
     * Method to execute when a ContextMenu item is selected.
     */
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // Receiving data from the ContextMenu for an item.
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // Extracting id and deleting the corresponding record in the database.
            db.delRec(info.id);
            // Refreshing the cursor.
            //noinspection deprecation
            cursor.requery();
            return true;
        }

        if (item.getItemId() == CM_UPDATE_ID) {
            updateTaskDialog();
        }

        return super.onContextItemSelected(item);
    }

    /**
     * To be executed as app is destroyed.
     */
    protected void onDestroy() {
        super.onDestroy();
        // Closing connection to database.
        db.close();
    }

    /**
     * Inflate the menu as OptionsMenu is created;
     * this adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    /**
     * To be executed as an item in the ActionBar is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //If add button is pressed.
            case R.id.action_add:
                addTaskDialog();
                break;
            //If clear button is pressed.
            case R.id.action_clear:
                db.clearDatabase(getString(R.string.dbTable));
                cursor.requery();
        }
        return true;
    }

    /**
     * Method to create a dialog when adding a task to ListView.
     */
    public void addTaskDialog() {

        //Create AlertDialog and inflate it in the View.
        AlertDialog.Builder addDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addtask_dialog, null);
        addDialog.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.dialogEditText);
        final EditText editDesc = (EditText) dialogView.findViewById(R.id.dialogEditDescription);
        editDateCreated = (EditText) dialogView.findViewById(R.id.dialogEditDateCreated);
        editDateDue = (EditText) dialogView.findViewById(R.id.dialogEditDateDue);

        //Get the system's Calendar settings.
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        editDateCreated.setText(day + "/" + month + "/" + year);

        //ClickListener for corresponding EditText to choose a date.
        editDateCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogCreated();
            }
        });

        //Set AlertDialog title.
        addDialog.setTitle(R.string.textEditDialogTitle);

        //Set AlertDialog affirmative button.
        addDialog.setPositiveButton(R.string.positiveButtonAdd, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //If EditText is not empty, get and store String objects in the database. Else, close dialog.
                if (!editText.getText().toString().matches("")) {
                    db.addText(editText.getText().toString(), editDesc.getText().toString(), editDateCreated.getText().toString(),
                            editDateDue.getText().toString());
                    cursor.requery();
                } else {
                    dialog.dismiss();
                }
            }
        });

        //Code identical to above, just for a (editDateDue EditText) instead of (editDateCreated EditText).
        editDateDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogDue();
            }
        });

        addDialog.setTitle(R.string.textEditDialogTitle);
        addDialog.setPositiveButton(R.string.positiveButtonAdd, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!editText.getText().toString().matches("")) {
                    db.addText(editText.getText().toString(), editDesc.getText().toString(), editDateCreated.getText().toString(),
                            editDateDue.getText().toString());
                    cursor.requery();
                } else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = addDialog.create();
        alert.show();
    }

    /**
     * Method to create a dialog when updating a task.
     */
    public void updateTaskDialog() {
        final AlertDialog.Builder updateDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.updatetask_dialog, null);
        updateDialog.setView(dialogView);

        //Set AlertDialog title and negative button.
        updateDialog.setTitle(R.string.update_record);
        updateDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //Set AlertDialog positive button.
        updateDialog.setPositiveButton(R.string.affirmative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTaskDialogFromUpdate();
            }
        });
        AlertDialog alert = updateDialog.create();
        alert.show();
    }

    /**
     * Identical to addTaskDialog, except shows old values from the created ListView item to be updated.
     */
    public void addTaskDialogFromUpdate() {
        AlertDialog.Builder addDialogFromUpdate = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.addtask_dialog, null);
        addDialogFromUpdate.setView(dialogView);

        //String objects are extracted from database using cursor and set in the EditText.
        final EditText editText = (EditText) dialogView.findViewById(R.id.dialogEditText);
        editText.setText(cursor.getString(cursor.getColumnIndex("txt")));
        final EditText editDesc = (EditText) dialogView.findViewById(R.id.dialogEditDescription);
        editDesc.setText(cursor.getString(cursor.getColumnIndex("des")));
        editDateCreated = (EditText) dialogView.findViewById(R.id.dialogEditDateCreated);
        editDateCreated.setText(cursor.getString(cursor.getColumnIndex("date_created")));
        editDateDue = (EditText) dialogView.findViewById(R.id.dialogEditDateDue);
        editDateDue.setText(cursor.getString(cursor.getColumnIndex("date_due")));

        editDateCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogCreated();
            }
        });

        editDateDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialogDue();
            }
        });

        addDialogFromUpdate.setTitle(R.string.textEditDialogTitle);
        addDialogFromUpdate.setPositiveButton(R.string.update_record, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!editText.getText().toString().matches("")) {
                    db.addText(editText.getText().toString(), editDesc.getText().toString(), editDateCreated.getText().toString(),
                            editDateDue.getText().toString());
                    cursor.requery();
                } else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = addDialogFromUpdate.create();
        alert.show();
    }

    /**
     * Creating a DatePickerDialog and setting the chosen date in the corresponding EditText.
     */
    public void DateDialogCreated() {

        //Implementing an OnDateSetListener to set date from the DatePickerDialog.
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String yearStr = String.valueOf(year);
                String monthStr = String.valueOf(monthOfYear);
                String dayStr = String.valueOf(dayOfMonth);
                editDateCreated.setText(dayStr + "/" + monthStr + "/" + yearStr);
            }
        };
        //Creating and showing a DatePickerDialog.
        DatePickerDialog datePicker = new DatePickerDialog(this, listener, year, month, day);
        datePicker.show();
    }

    /**
     * Identical to DateDialogCreated, except made for editDateDue EditText.
     */
    public void DateDialogDue() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String yearStr = String.valueOf(year);
                String monthStr = String.valueOf(monthOfYear);
                String dayStr = String.valueOf(dayOfMonth);
                editDateDue.setText(dayStr + "/" + monthStr + "/" + yearStr);
            }
        };
        DatePickerDialog datePicker = new DatePickerDialog(this, listener, year, month, day);
        datePicker.show();
    }
}
