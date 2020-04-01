package is.hi.hbv.conpay;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.Serializable;

import is.hi.hbv.conpay.Model.Customer;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.CustomerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private CustomerAPI customerAPI;
    private static final String CUSTOMER_URI = "is.hi.hbv.conpay.MainActivity";
    private int LOGIN_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.login_layout);

        mNameField = findViewById(R.id.mNameField);
        mPasswordField = findViewById(R.id.mPasswordField);
        mLoginButton = findViewById(R.id.mLoginButton);
        customerAPI = APIClient.getCustomerClient().create(CustomerAPI.class);
        mLoginButton.setOnClickListener(l -> LogIn());
    }

    private void LogIn() {
        String name = mNameField.getText().toString();
        String password = mPasswordField.getText().toString();
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(password);
        Call<Customer> call = customerAPI.login(customer);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Log.d("HttpStatus", String.valueOf(response.code()));

                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(LoginActivity.this, "LoggedIn", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("Customer", (Serializable) response.body());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("Network error", t.getMessage() != null ? t.getMessage() : "Error");
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}
