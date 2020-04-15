package is.hi.hbv.conpay;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbv.conpay.Model.Customer;
import is.hi.hbv.conpay.Model.Event;
import is.hi.hbv.conpay.Model.EventAdapter;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.EventAPI;
import is.hi.hbv.conpay.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventsActivity extends AppCompatActivity {

    private Customer customer;
    RecyclerView recyclerView;
    private EventAPI eventAPI;
    EventAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.my_events_layout);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            customer = (Customer) extras.get("Customer");
        } else {
            Toast.makeText(MyEventsActivity.this, "You have to be logged in to create events", Toast.LENGTH_LONG).show();
        }
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchEvents();
    }

    //populatea myevent eftir að maður hefur loggað sig inn:
    public void fetchEvents(){

        eventAPI = new APIClient().getEventClient().create(EventAPI.class);
        Call<List<Event>> call = eventAPI.getAllByOwner(customer.getId());

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("HttpStatus", String.valueOf(response.body()));
                    writeRecycler(response.body());

                } else {
                    Log.i("onEmptyResponse", "Returned empty response");
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                System.err.println("Failure");
                Log.e("Network error", t.getMessage());
                call.cancel();
            }

        });
    }
    private void writeRecycler(List<Event> response){

        eventAdapter = new EventAdapter(this, response);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

    }
}
