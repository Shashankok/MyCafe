package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView quantity, price, linkedIn;
    private EditText name, email;
    private CheckBox whipped, chocolate;
    private Button order_summary_btn, plus, minus;
    int counter = 5;
    double final_price = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        order_summary_btn = findViewById(R.id.order_summary_btn);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        name = findViewById(R.id.name);
        linkedIn = findViewById(R.id.linkedIn);
        whipped = findViewById(R.id.whipped_cream);
        chocolate = findViewById(R.id.chocolate_cream);
        email = findViewById(R.id.email);
        order_summary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] email_address = new String[1];
                email_address[0] = email.getText().toString();
                composeEmail(email_address);
            }
        });

        whipped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int quant = Integer.parseInt(quantity.getText().toString());
                if (whipped.isChecked()) {
                    final_price += quant;
                    price.setText("$" + final_price);
                } else {
                    final_price -= quant;
                    price.setText("$" + final_price);
                }
            }
        });

        chocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int quant = Integer.parseInt(quantity.getText().toString());
                if (chocolate.isChecked()) {
                    final_price += quant * 2;
                    price.setText("$" + final_price);
                } else {
                    final_price -= quant * 2;
                    price.setText("$" + final_price);
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcPlus();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcMinus();
            }
        });

        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.linkedin.com/in/shashank-sharma-6279b5188/"));
                startActivity(browserIntent);
            }
        });

    }

    public void funcPlus() {
        counter = Integer.parseInt(quantity.getText().toString()) + 1;
        quantity.setText("" + counter);
        final_price = counter * 5.0;
        if (whipped.isChecked()) {
            final_price += counter * 1;
        }
        if (chocolate.isChecked())
            final_price += counter * 2;
        price.setText("$" + final_price);
    }

    public void funcMinus() {
        counter = Integer.parseInt(quantity.getText().toString()) - 1;
        if (counter > 0) {
            quantity.setText("" + counter);

            final_price = counter * 5.0;
            if (whipped.isChecked()) {
                final_price += counter * 1;
            }
            if (chocolate.isChecked())
                final_price += counter * 2;
            price.setText("$" + final_price);
        }
    }

    public void composeEmail(String[] addresses) {
        String your_name = name.getText().toString();
        String your_email = email.getText().toString();
        String whipped_checker = "No";
        if (whipped.isChecked())
            whipped_checker = "Yes";
        String chocolate_checker = "No";
        if (chocolate.isChecked())
            chocolate_checker = "Yes";
        int quantity_checker = Integer.parseInt(quantity.getText().toString());
        String price_checker = price.getText().toString();
        String msg = "Name: " + your_name + "\nQuantity: " + quantity_checker +
                "\nWhipped Cream: " + whipped_checker + "\nChocolate Cream: " + chocolate_checker +
                "\nTotal Price: " + price_checker + "\n\n\nThank You, Have A Nice Day";

        if (TextUtils.isEmpty(your_name)) {
            name.setError("Name field cannot be empty");
        } else if (TextUtils.isEmpty(your_email)) {
            email.setError("Email field cannot be empty");
        } else {
            name.setError(null);
            email.setError(null);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Summary Of Your Order At My Cafe");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

}
