package is.hi.hbv.conpay;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbv.conpay.Model.Customer;

public class MyAccountActivity extends AppCompatActivity {

    private Customer customer;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.my_account_layout);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            customer = (Customer) extras.get("Customer");
        } else {
            Toast.makeText(MyAccountActivity.this, "You have to be logged in to create events", Toast.LENGTH_LONG).show();
        }
    }
}
