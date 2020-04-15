package is.hi.hbv.conpay;



import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import is.hi.hbv.conpay.Model.Event;
import is.hi.hbv.conpay.Model.PaymentMethod;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.EventAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentActivity extends AppCompatActivity implements Serializable {

    private EditText mCardNumber, mExpMonth, mExpYear, mCvc, mSSN, mNameOfPayer, mNameOfParticipant, mEmail;
    private TextView mTextViewTitle, mTextViewDesc, mTextViewPrice, mTextViewMaxPart, mTextViewMinPart, mTextViewExpDate;
    private Button mSubmitButton;
    private Event event;
    private PaymentMethod paymentMethod;
    private EventAPI eventAPI;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.payment_layout);

        Bundle extras = getIntent().getExtras();
        if (extras!= null){
            event = (Event) extras.get("event");
        }

        mNameOfParticipant = findViewById(R.id.nameofparticipant);
        mEmail = findViewById(R.id.email);
        mNameOfPayer = findViewById(R.id.nameofpayer);
        mSSN = findViewById(R.id.ssn);
        mCardNumber = findViewById(R.id.cardNumber);
        mExpMonth = findViewById(R.id.month);
        mExpYear = findViewById(R.id.year);
        mCvc = findViewById(R.id.cvc);
        mSubmitButton = findViewById(R.id.submitButton);

        updateDescription();

        eventAPI = APIClient.getEventClient().create(EventAPI.class);
        mSubmitButton.setOnClickListener(l -> createPayment());
    }

    private void updateDescription(){
        mTextViewTitle = findViewById(R.id.textViewTitle);
        mTextViewDesc = findViewById(R.id.textViewShortDesc);
        mTextViewPrice = findViewById(R.id.textViewPrice);
        mTextViewMaxPart = findViewById(R.id.textViewMaxPart);
        mTextViewMinPart = findViewById(R.id.textViewMinPart);
        mTextViewExpDate = findViewById(R.id.textViewExpDate);

        mTextViewTitle.setText(event.getName());
        mTextViewExpDate.setText(String.valueOf(event.geteDate()));
        mTextViewDesc.setText(event.getDescription());
        mTextViewPrice.setText(String.valueOf(event.getPriceCat()));
        mTextViewMaxPart.setText(String.valueOf(event.getMaxParticipants()));
        mTextViewMinPart.setText(String.valueOf(event.getMinParticipants()));
    }

    private void createPayment(){
        PaymentMethod newPaymentMethod = new PaymentMethod();
        String nameOfPayer = mNameOfPayer.getText().toString();
        if(nameOfPayer.isEmpty()){
            makeToast("name field is empty");
            return;
        }
        String SSN = mSSN.getText().toString();
        if(SSN.isEmpty()){
            makeToast("Social security number field is empty");
            return;
        }
        String cardNo = mCardNumber.getText().toString();
        if(cardNo.isEmpty()){
            makeToast("Card number field is empty");
            return;
        }
        String eMonth = mExpMonth.getText().toString();
        if(eMonth.isEmpty()){
            makeToast("Expiration date is not filled out");
            return;
        }
        String eYear = mExpYear.getText().toString();
        if(eYear.isEmpty()){
            makeToast("Expiration date is not filled out");
            return;
        }
        String expirationDate = eMonth + eYear;
        String securityNo = mCvc.getText().toString();
        if(securityNo.isEmpty()){
            makeToast("CVC is missing");
        }
        String email = mEmail.getText().toString();
        if(email.isEmpty()){
            makeToast("Email address missing");
        }
        String pName = mNameOfParticipant.getText().toString();
        if(pName.isEmpty()){
            makeToast("Name of participant is empty");
        }

        newPaymentMethod.setName(pName);
        newPaymentMethod.setNameOfPayer(nameOfPayer);
        newPaymentMethod.setSSN(SSN);
        newPaymentMethod.setCardNo(cardNo);
        newPaymentMethod.setExpirationDate(expirationDate);
        newPaymentMethod.setSecurityNo(securityNo);
        newPaymentMethod.setEmail(email);

        makePayment(newPaymentMethod);
    }

    private void makePayment(PaymentMethod newPaymentMethod) {
        Call<PaymentMethod> call = eventAPI.payEvent(event.getId(), newPaymentMethod);
        System.out.println("call request" + call.request().body());
        call.enqueue(new Callback<PaymentMethod>() {
            @Override
            public void onResponse(Call<PaymentMethod> call, Response<PaymentMethod> response) {
                Log.d("HttpStatus", String.valueOf(response.code()));
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(PaymentActivity.this, "Event Created and can be found under My Events", Toast.LENGTH_LONG).show();
                    finish();
                }
                makeToast("Something went wrong with creation.");
            }

            @Override
            public void onFailure(Call<PaymentMethod> call, Throwable t) {
                Log.e("Network error", t.getMessage() != null ? t.getMessage() : "Error in netzwerk");
                Toast.makeText(PaymentActivity.this, "Event creation failed: network error", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void makeToast(String msg) {
        Toast.makeText(PaymentActivity.this, msg, Toast.LENGTH_LONG).show();

    }




}
