package is.hi.hbv.conpay;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import is.hi.hbv.conpay.Model.Customer;
import is.hi.hbv.conpay.Model.Event;
import is.hi.hbv.conpay.Model.PaymentMethod;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.EventAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventActivity extends AppCompatActivity {

    private Customer customer;
    private EditText mNameField;
    private EditText mPriceCat;
    private EditText mDescription;
    private EditText mMinParticipants;
    private EditText mMaxParticipants;
    private DatePicker datePicker1;
    private EditText mCardType;
    private EditText mCardNo;
    private EditText mMonth;
    private EditText mYear;
    private EditText mSecurityNo;
    private Switch mRefundSwitch;
    private Switch mPublicSwitch;
    private Button mCreateEventButton;
    private EventAPI eventAPI;

    @Override
    protected void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.create_event_layout);
        
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            customer = (Customer) extras.get("Customer");
        } else {
            Toast.makeText(CreateEventActivity.this, "You have to be logged in to create events", Toast.LENGTH_LONG).show();
        }
        mNameField = findViewById(R.id.mNameField);
        mPriceCat = findViewById(R.id.mPriceCat);
        mDescription = findViewById(R.id.mDescription);
        mMinParticipants = findViewById(R.id.mMinParticipants);
        mMaxParticipants = findViewById(R.id.mMaxParticipants);
        datePicker1 = findViewById(R.id.datePicker1);
        mCardType = findViewById(R.id.mCardType);
        mCardNo = findViewById(R.id.mCardNo);
        mMonth = findViewById(R.id.mMonth);
        mYear = findViewById(R.id.mYear);
        mSecurityNo = findViewById(R.id.mSecurityNo);
        mRefundSwitch = findViewById(R.id.mRefundSwitch);
        mPublicSwitch = findViewById(R.id.mPublicSwitch);
        mCreateEventButton = findViewById(R.id.mCreateEventButton);

        eventAPI = APIClient.getEventClient().create(EventAPI.class);
        mCreateEventButton.setOnClickListener(l -> createEvent());
    }

    private void createEvent() {
        Event newEvent = new Event();
        String name = mNameField.getText().toString();
        if(name.isEmpty()){
            makeToast("Name field is empty");
            return;
        }
        System.out.println("name: " + name);
        if(mPriceCat.getText().toString().isEmpty()){
            makeToast("Price field is empty");
            return;
        }
        double priceCat = Double.parseDouble(mPriceCat.getText().toString());
        System.out.println("priceCat: " + priceCat);

        String description = mDescription.getText().toString();
        if(description.isEmpty()){
            makeToast("Description field is empty");
            return;
        }
        System.out.println("Description: " + description);
        int minP = 0;
        if(mMinParticipants.getText().toString().isEmpty()){
            minP = -1;
        } else {
            minP = Integer.parseInt(mMinParticipants.getText().toString());
        }
        System.out.println("minP: "+ minP);
        int maxP = 0;
        if(mMaxParticipants.getText().toString().isEmpty()){
            maxP = -1;
        } else {
            maxP = Integer.parseInt(mMaxParticipants.getText().toString());
        }
        System.out.println("maxP: " + maxP);
        int day = datePicker1.getDayOfMonth();
        int month = datePicker1.getMonth();
        int year = datePicker1.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        Date date = calendar.getTime();
        System.out.println("date: " + calendar.getTime().toString());
        String cardType = mCardType.getText().toString();
        if(cardType.isEmpty()){
            makeToast("Card type is empty");
            return;
        }
        System.out.println("CardType: " + cardType);
        String cardNo = mCardNo.getText().toString();
        if(cardNo.isEmpty()){
            makeToast("Card number is empty or on the wrong format");
            return;
        }
        System.out.println("Cardno: " + cardNo);

        String eMonth = mMonth.getText().toString();
        if(eMonth.isEmpty()){
            makeToast("Expiration date is not filled out");
            return;
        }
        System.out.println("eMonth: " + eMonth);
        String eYear = mYear.getText().toString();
        if(eYear.isEmpty()){
            makeToast("Expiration date is not filled out");
            return;
        }
        System.out.println("eYear: " + eYear);
        String expDate = eMonth+eYear;
        System.out.println(expDate);
        String secNo = mSecurityNo.getText().toString();
        if(secNo.isEmpty()){
            makeToast("Security number is not filled out");
            return;
        }
        System.out.println(secNo);
        boolean refund = false;
        if(mRefundSwitch.isChecked()){
            refund = true;
        }
        boolean isPublic = false;
        if (mPublicSwitch.isChecked()) {
            isPublic = true;
        }
        List<PaymentMethod> paymentMethods = new ArrayList<>();


        newEvent.setName(name);
        newEvent.setPriceCat(priceCat);
        newEvent.setMinParticipants(minP);
        newEvent.setMaxParticipants(maxP);
        newEvent.seteDate(new Date(date.getTime()));
        newEvent.setDescription(description);
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setCardNo(cardNo);
        paymentMethod.setCardType(cardType);
        paymentMethod.setExpirationDate(expDate);
        paymentMethod.setName(customer.getName());
        paymentMethod.setNameOfPayer(customer.getName());
        paymentMethod.setpDate(new Date());
        //paymentMethod.setOwnerId(customer.getId());
        paymentMethod.setEmail(customer.getEmail());
        paymentMethod.setSSN("Owner");
        paymentMethod.setSecurityNo(secNo);
        newEvent.setPaymentMethod(paymentMethod);
        newEvent.setOwnerId(customer.getId());
        newEvent.setActive(true);
        newEvent.setRefundPossible(refund);
        newEvent.setPublic(isPublic);
        newEvent.setPayments(paymentMethods);
        Gson gson = new Gson();
        String json = gson.toJson(newEvent);
        System.out.println(json);
        makeEvent(newEvent);
    }

    private void makeEvent(Event newEvent) {
        Call<Event> call = eventAPI.saveEvent(newEvent);
        System.out.println("call request" + call.request().body());
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Log.d("HttpStatus", String.valueOf(response.code()));
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(CreateEventActivity.this, "Event Created and can be found under My Events", Toast.LENGTH_LONG).show();
                    finish();
                }
                makeToast("Something went wrong with creation.");
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e("Network error", t.getMessage() != null ? t.getMessage() : "Error in netzwerk");
                Toast.makeText(CreateEventActivity.this, "Event creation failed: network error", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void makeToast(String msg) {
        Toast.makeText(CreateEventActivity.this, msg, Toast.LENGTH_LONG).show();

    }
}
