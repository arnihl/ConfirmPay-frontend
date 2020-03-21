package is.hi.hbv.conpay.Network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static final String EVENT_API_URL = "https://hugbo2-conpay.herokuapp.com/api/event/";
    private static final String CUSTOMER_API_URL = "https://hugbo2-conpay.herokuapp.com/api/customer/";


    public static Retrofit getEventClient() {
        return new Retrofit.Builder()
                .baseUrl(EVENT_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static Retrofit getCustomerClient(){
        return new Retrofit.Builder()
                .baseUrl(CUSTOMER_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

}
