package is.hi.hbv.conpay;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class PaymentActivity extends AppCompatActivity {

    private EditText mCardNumber, mExpMonth, mExpYear, mCvc;
    private Button mSubmitButton;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.payment_layout);

        mCardNumber = findViewById(R.id.cardNumber);
        mExpMonth = findViewById(R.id.month);
        mExpYear = findViewById(R.id.year);
        mCvc = findViewById(R.id.cvc);
        mSubmitButton = findViewById(R.id.submitButton);

    }



}
