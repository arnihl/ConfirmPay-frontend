package is.hi.hbv.conpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;


import is.hi.hbv.conpay.Model.Customer;
import is.hi.hbv.conpay.Model.Event;
import is.hi.hbv.conpay.Model.EventAdapter;
import is.hi.hbv.conpay.Network.APIClient;
import is.hi.hbv.conpay.Network.EventAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    List<Event> eventList;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    public Customer loggedInCustomer;
    private Menu menu;
    private MenuItem menuMyAccount;
    private MenuItem menuMyEvents;
    private MenuItem menuCreateEvent;
    private MenuItem menuLogin;
    private MenuItem menuSignup;

    private final int LOGIN_REQUEST_CODE = 1;
    private final int SIGNUP_REQUEST_CODE = 2;

    private EventAPI eventAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventAPI = new APIClient().getEventClient().create(EventAPI.class);
        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.nv);
        menu = nv.getMenu();
        menuMyAccount = menu.findItem(R.id.menuMyAccount);
        menuMyEvents = menu.findItem(R.id.menuMyEvents);
        menuCreateEvent = menu.findItem(R.id.menuCreateEvent);
        menuLogin = menu.findItem(R.id.menuLogin);
        menuSignup =  menu.findItem(R.id.menuSignUp);

        setConditionalVisibles();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.menuMyAccount:
                        myAccount(loggedInCustomer);
                        break;
                    case R.id.menuMyEvents:
                        myEvents(loggedInCustomer);
                        break;
                    case R.id.menuCreateEvent:
                        createEvent(loggedInCustomer);
                        break;
                    case R.id.menuLogin:
                        logIn();
                        break;
                    case R.id.menuSignUp:
                        signUp();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        fetchEvents();

    }

    private void setConditionalVisibles() {
        if(loggedInCustomer == null){
            menuMyAccount.setVisible(false);
            menuMyEvents.setVisible(false);
            menuCreateEvent.setVisible(false);
            menuLogin.setVisible(true);
            menuSignup.setVisible(true);
        } else {
            menuMyAccount.setVisible(true);
            menuMyEvents.setVisible(true);
            menuCreateEvent.setVisible(true);
            menuLogin.setVisible(false);
            menuSignup.setVisible(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Activity Result");
        switch (requestCode){
            case LOGIN_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    loggedInCustomer = (Customer) data.getExtras().getSerializable("Customer");
                    System.out.println("CUSTOMAH: " + loggedInCustomer.getName());
                }
                break;
            default:
                break;
        }
        setConditionalVisibles();
    }


    private void createEvent(Customer loggedInCustomer) {
        if(loggedInCustomer == null){
            Toast.makeText(MainActivity.this, "You have to be logged in to do this", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(MainActivity.this, CreateEventActivity.class);
        i.putExtra("Customer", loggedInCustomer);
        startActivity(i);
    }

    private void myEvents(Customer loggedInCustomer) {
        if(loggedInCustomer == null){
            Toast.makeText(MainActivity.this, "You have to be logged in to do this", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(MainActivity.this, MyEventsActivity.class);
        i.putExtra("Customer", loggedInCustomer);
        startActivity(i);
    }

    private void myAccount(Customer loggedInCustomer) {
        if(loggedInCustomer == null){
            Toast.makeText(MainActivity.this, "You have to be logged in to do this", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(MainActivity.this, MyAccountActivity.class);
        i.putExtra("Customer", loggedInCustomer);
        startActivity(i);
    }

    private void signUp() {
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    private void logIn() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(i, LOGIN_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    // populate events
    public void fetchEvents(){

        eventAPI = new APIClient().getEventClient().create(EventAPI.class);
        Call<List<Event>> call = eventAPI.getAllPublic();

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