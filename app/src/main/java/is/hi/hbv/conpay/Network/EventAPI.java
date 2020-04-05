package is.hi.hbv.conpay.Network;

import java.util.List;


import is.hi.hbv.conpay.Model.Event;
import is.hi.hbv.conpay.Model.PaymentMethod;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventAPI {

    @GET("getallpublic")
    Call<List<Event>> getAllPublic();

    @GET("get/{id}")
    Call<Event> getEventById(@Path("id") long id);

    @POST("save")
    Call<Event> saveEvent(@Body Event event);

    @GET("getall")
    Call<List<Event>> getAll();

    @GET("getbyowner/{id}")
    Call<List<Event>> getAllByOwner(@Path("id") long id);

    @DELETE("delete/{id}")
    Call<Void> delete(@Path("id") long id);

    @POST("pay/{id}")
    Call<PaymentMethod> payEvent(@Path("id") long id, @Body PaymentMethod paymentMethod);


}
