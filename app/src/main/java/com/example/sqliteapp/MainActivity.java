/**
 * Android app to store, update, show and delete data from database
 * Date : 25.09.19
 * Last Modified : 25.09.19
 */

package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.SoftReference;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;

    private EditText name, surname, marks, id;
    private Button add_data, viewData, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        marks = (EditText) findViewById(R.id.marks);
        id = (EditText) findViewById(R.id.ID);
        add_data = (Button) findViewById(R.id.add_button);
        viewData = (Button) findViewById(R.id.view_button);
        update = (Button) findViewById(R.id.update_button);
        delete = (Button) findViewById(R.id.delete_button);


        addData();
        viewAll();
        updateDate();
    }

    public void addData(){
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insertData(name.getText().toString(),
                        surname.getText().toString(),
                        marks.getText().toString());

                if(isInserted) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    name.setText("");
                    surname.setText("");
                    marks.setText("");
                    id.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void viewAll() {
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();
                if(res.getCount() == 0){
                    //  Show error
                    ShowMessage("Error", "No data is found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(res.moveToNext()){
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Surname: " + res.getString(2) + "\n");
                    buffer.append("Marks: " + res.getString(3) + "\n\n");
                }

                //  Show all data
                ShowMessage("Data", buffer.toString());
            }
        });
    }

    public  void  updateDate(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = mydb.updateData(id.getText().toString(), name.getText().toString(), surname.getText().toString(), marks.getText().toString());
                if(isUpdate){ Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                    name.setText("");
                    surname.setText("");
                    marks.setText("");
                    id.setText("");
                }
                else { Toast.makeText(MainActivity.this, "Data is not Updated", Toast.LENGTH_LONG).show(); }
            }
        });
    }

    public void ShowMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
