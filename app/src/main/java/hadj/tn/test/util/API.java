package hadj.tn.test.util;

import java.util.List;

import hadj.tn.test.model.Alert;
import hadj.tn.test.model.AppUserRole;
import hadj.tn.test.model.Evenement;
import hadj.tn.test.model.FCMToken;
import hadj.tn.test.model.Invitation;
import hadj.tn.test.model.NotificationResquest;
import hadj.tn.test.model.Post;
import hadj.tn.test.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    /******************** Without bearer token **********************/

    @POST("api/auth/signup")
    Call<ResponseBody> createUser(
            @Body User user
    );

    @POST("api/auth/login")
    Call<ResponseBody> checkUser(
            @Body User user
    );

    @POST("api/alert/save")
    Call<Alert> saveAlert(
            @Body Alert alert
    );

    /********************** With bearer token ****************/

    @GET("api/user/get/{username}")
    Call<User> findUser(@Header("Authorization") String token, @Path("username") String username);

    @POST("/api/user/update")
    Call<ResponseBody> updateUser(@Header("Authorization") String token, @Body User user);



    @POST("/api/pub/addPublication")
    Call<ResponseBody> createPost(
            @Header("Authorization") String token, @Body Post post
    );

    @POST("api/pub/updatePub")
    Call<ResponseBody> updatePost(@Header("Authorization") String token, @Body Post post);

    @GET("api/pub/getPublications")
    Call<List<Post>> findAllPubs(@Header("Authorization") String token);

    @DELETE("api/pub/deletePub/{id}")
    Call<Void> deletePubById(@Header("Authorization") String token,@Path("id") int id);

    @GET("api/user/byRole/{role}")
    Call<List<User>> findByRole(@Header("Authorization") String token, @Path("role") AppUserRole role);

    @POST("/api/fcm/addToken")
    Call<ResponseBody> addRegistrationToken(
            @Header("Authorization") String token,@Body FCMToken fcmToken
    );

    @GET("api/fcm/getToken/{username}")
    Call<List<FCMToken>> findTokensByUsername(
            @Header("Authorization") String token,@Path("username") String username
    );

    @POST("api/user/toDonor")
    Call<ResponseBody> setToDonor( @Header("Authorization") String token, @Body User user);

    @POST("/api/fcm/send-notification")
    Call<ResponseBody> sendNotification(@Header("Authorization") String token, @Body NotificationResquest request);


    @GET("/api/alert/get/{id}")
    Call<Alert> getAlert(@Header("Authorization") String token,@Path("id") int id);
    @DELETE("/api/alert/delete/{id}")
    Call<Void> deleteAlert(@Header("Authorization") String token , @Path("id") int id);
    @GET("api/alert/getAll")
    Call<List<Alert>> getAlerts(@Header("Authorization") String token);
    @POST("api/alert/update")
    Call <Alert> acceptAlert(@Header("Authorization") String token,@Body Alert alert);

    @DELETE("api/invitation/delete")
    Call <Void> deleteInvit(@Header("Authorization") String token,@Body Evenement event);
    @GET("api/invitation/getAll")
    Call<List<Invitation>> getInvits(@Header("Authorization") String token);

    @GET("api/event/{id}")
    Call<Evenement> getEvent(@Header("Authorization") String token, @Path("id") int id);
}

