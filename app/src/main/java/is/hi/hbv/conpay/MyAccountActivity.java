package is.hi.hbv.conpay;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import is.hi.hbv.conpay.Model.Customer;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.CustomerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends AppCompatActivity {

    private Customer customer;
    private TextView mAccountName;
    private TextView mAccountEmail;
    private TextView mAccountEventNumber;
    private TextView mAccountRating;
    private CustomerAPI mCustomerAPI;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.my_account_layout);

        mAccountName = findViewById(R.id.accountNamePlaceTextView);
        mAccountEmail = findViewById(R.id.accountEmailPlaceTextView);
        mAccountEventNumber = findViewById(R.id.accountEventNumberPlaceTextView);
        mAccountRating = findViewById(R.id.accountRatingPlaceTextView);
        // mCustomerAPI = new APIClient.getCustomerClient().create(CustomerAPI.class);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            customer = (Customer) extras.get("Customer");
        } else {
            Toast.makeText(MyAccountActivity.this, "You have to be logged in to create events", Toast.LENGTH_LONG).show();
        }

        updateAccountName();
        updateAccountEmail();
        updateAccountEventNumber();
        updateAccountRating();
    }

    protected void updateAccountName() {
        String name = customer.getName();
        mAccountName.setText(name);
    }

    protected void updateAccountEmail() {
        String email = customer.getEmail();
        mAccountEmail.setText(email);
    }

    protected void updateAccountEventNumber() {
        int eventNumber = customer.getNumOfEvents();
        String eventNumString = eventNumber + "";
        mAccountEventNumber.setText(eventNumString);
    }

    protected void updateAccountRating() {
        double rating = customer.getRating();
        String ratingString = rating + "";
        mAccountRating.setText(ratingString);
    }
}
