package is.hi.hbv.conpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbv.conpay.Model.Event;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.EventAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindEventActivity extends AppCompatActivity {
    private EditText mEventId;
    private Button mGetEventButton;
    private EventAPI eventAPI;


    // Pretty straight forward activity.
    // Fetches and displays an event by id with payment_layout,
    // same as is used in Payment activity
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.find_event_layout);

        mEventId = findViewById(R.id.mEventId);
        mGetEventButton = findViewById(R.id.mGetEventButton);
        eventAPI = APIClient.getEventClient().create(EventAPI.class);

        mGetEventButton.setOnClickListener(l -> findEvent());

    }

    private void findEvent() {
        long id = Long.parseLong(mEventId.getText().toString());
        fetchEvent(id);
    }

    private void fetchEvent(long id) {
        Call<Event> call = eventAPI.getEventById(id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Log.d("HttpStatus", String.valueOf(response.code()));
                if(response.isSuccessful() && response.body() != null){
                    makeToast("being redirected");
                    publishEvent(response.body());
                }
                else{
                    makeToast("Event not found, try again.");
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e("Network error", t.getMessage() != null ? t.getMessage() : "Error in netzwerk");
                makeToast("Error in network, try again later.");
                call.cancel();
            }
        });
    }

        private void makeToast(String msg) {
            Toast.makeText(FindEventActivity.this, msg, Toast.LENGTH_SHORT).show();
        }


    private void publishEvent(Event body) {
        Intent i = new Intent(FindEventActivity.this, PaymentActivity.class);
        i.putExtra("event", body);
        startActivity(i);
    }
}
