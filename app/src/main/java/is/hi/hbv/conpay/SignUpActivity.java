package is.hi.hbv.conpay;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import is.hi.hbv.conpay.Model.Customer;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.CustomerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SignUpActivity extends AppCompatActivity {
    private EditText mNameField;
    private EditText mPasswordField;
    private EditText mEmailField;
    private Button mSignUpButton;
    private CustomerAPI customerAPI;

    // Creates a new user and stores it on the backend.
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.signup_layout);

        mNameField = findViewById(R.id.mNameField);
        mPasswordField = findViewById(R.id.mPasswordField);
        mEmailField = findViewById(R.id.mEmailField);
        mSignUpButton = findViewById(R.id.mSignUpButton);

        customerAPI = APIClient.getCustomerClient().create(CustomerAPI.class);

        mSignUpButton.setOnClickListener(l -> SignUp());
    }

    // Creates a user(customer) object and sends it to the backend
    // for storing.
    private void SignUp() {
        String name = mNameField.getText().toString();
        String password = mPasswordField.getText().toString();
        String email = mEmailField.getText().toString();
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(password);
        customer.setEmail(email);
        Call<Customer> call = customerAPI.saveCustomer(customer);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Log.d("HttpStatus", String.valueOf(response.code()));

                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(SignUpActivity.this, "You have made an account. Now you can log in!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("Network error", t.getMessage() != null ? t.getMessage() : "Error in netzwerk");
                Toast.makeText(SignUpActivity.this, "Signup Failed because of a network error", Toast.LENGTH_LONG).show();
                call.cancel();

            }
        });

    }
}
