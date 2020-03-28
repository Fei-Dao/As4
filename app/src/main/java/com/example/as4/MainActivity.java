package com.example.as4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    DataBaseManagement DB;
    TextView balance;
    EditText Date, Price, Item;
    Button Add, Sub;
    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DB = new DataBaseManagement(this);
        balance = (TextView) findViewById(R.id.balance);
        Date = (EditText) findViewById(R.id.Date);
        Price = (EditText) findViewById(R.id.Price);
        Item = (EditText) findViewById(R.id.Item);
        Add = (Button) findViewById(R.id.Add);
        Sub = (Button) findViewById(R.id.Sub);
        history = (TextView) findViewById(R.id.history);
        add();
        set();
    }

    public void add(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString());
                        boolean result = DB.addHistory(Item.getText().toString(), Date.getText().toString(), price);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        set();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                    }
                }
        );

        Sub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = -1 * Double.parseDouble(Price.getText().toString());
                        boolean result = DB.addHistory(Item.getText().toString(), Date.getText().toString(), price);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        set();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                    }
                }
        );
    }

    public void set(){
        Cursor result = DB.pullData();
        StringBuffer str = new StringBuffer();
        Double balance = 0.0;

        while(result.moveToNext()){
            String priceString = result.getString(3);
            double price = Double.parseDouble(result.getString(3));
            balance += price;

            if (price < 0) {
                str.append("Spent $");
                priceString = priceString.substring(1);
            }
            else {
                str.append("Added $");
            }
            str.append(priceString + " on " + result.getString(2)
                    + " for " + result.getString(1) + "\n");
        }
        MainActivity.this.balance.setText("Current Balance: $" + balance);
        MainActivity.this.history.setText(str);
    }
}