package is.hi.hbv.conpay.Network;


import java.util.List;

import is.hi.hbv.conpay.Model.Customer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerAPI {

    @POST("save")
    Call<Customer> saveCustomer(@Body Customer customer);

    @GET("getall")
    Call<List<Customer>> getAll();

    @GET("findbyid")
    Call<Customer> findById(@Query("id") String id);

    @GET("findbyname")
    Call<List<Customer>> findByName(@Query("name") String name);

    @POST("login")
    Call<Customer> login(@Body Customer customer);

    @GET("isloggedin")
    Call<Customer> isLoggedIn();

    @DELETE("{id}")
    Call<Void> delete(@Path("id") long id);

}
