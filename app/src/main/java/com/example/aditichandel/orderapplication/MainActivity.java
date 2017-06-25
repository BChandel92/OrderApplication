package com.example.aditichandel.orderapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.aditichandel.orderapplication.R.id.chocolate;

public class MainActivity extends AppCompatActivity {

    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void addIncrement(View view)
    {
        if(quantity==100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }
    public void adddecrement(View view)
    {
        if(quantity==1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }
    private int calculatePrice(int quantity,boolean cream,boolean chocolate)
    {
        int price=quantity*5;
        if(cream)
        {
            price=price+1;
        }
        if(chocolate)
        {
            price=price+2;
        }
        return price;
    }
    public String createOrderSummary(int price,boolean whippedcream,boolean haschocolate,String name)
    {
        String namemessage=getString(R.string.order_summary_name,name);
     return namemessage+": "+ "\n"+getString(R.string.add_whipped_cream)+" ? "+whippedcream+"\n"+getString(R.string.add_chocolate)+" ? "+haschocolate+"\n"+getString(R.string.quantity)+" : "+quantity+"\n"+getString(R.string.total)+" : $"+price+"\n"+getString(R.string.thank_you);
    }
    public void submitOrder(View view)
    {

        CheckBox checkstate=(CheckBox)findViewById(R.id.whippedcream);
        boolean hasWhippedCream=checkstate.isChecked();
        CheckBox checkstate2=(CheckBox)findViewById(chocolate);
        boolean haschocolate=checkstate2.isChecked();
        EditText textname=(EditText)findViewById(R.id.name_field);
        String name=textname.getText().toString();
        int price=calculatePrice(quantity,hasWhippedCream,haschocolate);
       String pricemessage=createOrderSummary(price,hasWhippedCream,haschocolate,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, pricemessage);
        intent.putExtra(Intent.EXTRA_TEXT,pricemessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffee_order));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    private void displayQuantity(int number)
    {
        TextView quantityTextView=(TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }


}
