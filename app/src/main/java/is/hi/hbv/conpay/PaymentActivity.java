package is.hi.hbv.conpay;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import is.hi.hbv.conpay.Model.Event;


public class PaymentActivity extends AppCompatActivity implements Serializable {

    private EditText mCardNumber, mExpMonth, mExpYear, mCvc;
    private TextView mTextViewTitle;
    private Button mSubmitButton;
    private Event event;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.payment_layout);

        Bundle extras = getIntent().getExtras();
        if (extras!= null){
            event = (Event) extras.get("event");
        }

        mTextViewTitle = findViewById(R.id.textViewTitle);
        mCardNumber = findViewById(R.id.cardNumber);
        mExpMonth = findViewById(R.id.month);
        mExpYear = findViewById(R.id.year);
        mCvc = findViewById(R.id.cvc);
        mSubmitButton = findViewById(R.id.submitButton);

        //update();

    }





}
