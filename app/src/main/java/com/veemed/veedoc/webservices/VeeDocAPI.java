package com.veemed.veedoc.webservices;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.models.CallAcceptAPIResponse;
import com.veemed.veedoc.models.ChangePassword;
import com.veemed.veedoc.models.ConnectedCallPingModel;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.DeferResponseModel;
import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.models.NewMessageBody;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.models.event.OffDayModel;
import com.veemed.veedoc.models.Session;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.models.SpecialistInformation;
import com.veemed.veedoc.models.Speciality;
import com.veemed.veedoc.models.State;
import com.veemed.veedoc.models.VerificationCode;
import com.veemed.veedoc.models.event.ScheduledEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VeeDocAPI {

    @FormUrlEncoded()
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/identity/connect/token")
    Call<TokenResponse> authenticateUser(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grant_type);

    @Headers("Content-Type: application/json")
    @GET("/platform/api/user/info")
    Call<UserAPIResponse> getUserAPIResponse(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("/platform/api/specialist/info")
    Call<SpecialistInformation> getSpecialistInfo(@Header("Authorization") String authorization);

    @POST("/platform/api/user/registration/init")
    public Call<String> initRegistration(@Body UserAPIResponse user);

    @PUT("/platform/api/user/registration/verify")
    public Call<UserAPIResponse> putVerificationCode(@Body VerificationCode user, @Header("Authorization") String token);

    @POST("/platform/api/user/resend/verification/key")
    public Call<Void> resendVerificationCode(@Header("Authorization") String token);

    @GET("/platform/api/specialist/session/history/{year}/{month}/{day}/{param}")
    public Call<List<Session>> getSessions(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day,
            @Path("param") int param,
            @Header("Authorization") String token);

    @GET("/platform/api/endpoints/status/filtered/{status}/{filter}/{index}/{size}")
    public Call<List<Endpoint>> getEndPoints(
            @Path("status") String status,
            @Path("filter") String filter,
            @Path("index") int index,
            @Path("size") int size,
            @Header("Authorization") String token);
    @GET("/platform/api/endpoint/accessible/status")
    public Call<List<EndpointStatus>> getEndPointStatus(@Header("Authorization") String token);

    @GET("/platform/api/endpoint/session/info/{id}")
    public Call<SessionInfo> getSessionInfo(@Path("id") int id, @Header("Authorization") String token);

    @GET("/platform/api/state/1")
    public Call<List<State>> getStates(@Header("Authorization") String token);

    @GET("/platform/api/speciality/all")
    public Call<List<Speciality>> getSpecialities(@Header("Authorization") String token);

    @GET("/platform/api/specialist/offdays/{year}/{month}/1")
    public Call<List<OffDayModel>> getOffDays(
            @Path("year") int Year,
            @Path("month") int Month,
            @Header("Authorization") String token);

    @GET("/platform/api/specialist/schedule/{year}/{month}")
    public Call<List<ScheduledEvent>> getEvents(
            @Path("year") int Year,
            @Path("month") int Month,
            @Header("Authorization") String token);

    @GET("/platform/api/specialist/requests/pending")
    public Call<List<PendingSession>> getPendingRequests(@Header("Authorization") String token);

    @PUT("/platform/api/specialist/request/action")
    public Call<SessionInfo> callActionAccept(@Body CallActionsModel actionsModel, @Header("Authorization") String token);

    @PUT("/platform/api/specialist/request/action")
    public Call<Void> callActionReject(@Body CallActionsModel actionsModel, @Header("Authorization") String token);

    @PUT("/platform/api/specialist/request/action")
    public Call<Conversation> callActionDefer(@Body CallActionsModel actionsModel, @Header("Authorization") String token);

    @PUT("/platform/api/specialist/request/action")
    public Call<Conversation> pingConnectedCallStatus(@Body ConnectedCallPingModel callPingModel, @Header("Authorization") String token);


    @PUT("/platform/api/user/password/change")
    public Call<Void> changePassword(@Body ChangePassword changePassword, @Header("Authorization") String token);

    @PUT("/platform/api/user/registration/complete")
    public Call<String> completeRegistration(@Body UserAPIRequest userAPIResponse, @Header("Authorization") String token);


    // Messages
    @POST("/platform/api/requests/message/send")
    public Call<Void> sendNewMessage(@Body NewMessageBody messageBody, @Header("Authorization") String token);

    @GET("/platform/api/requests/deferred/last/message")
    public Call<List<Conversation>> getConversations(@Header("Authorization") String token);

    @GET("/platform/api/requests/{messageSessionId}/messages/{pageIndex}/{length}/{unread}")
    public Call<List<Message>> getMessages(
            @Path("messageSessionId") int messageSessionId,
            @Path("pageIndex") int pageIndex,
            @Path("length") int length,
            @Path("unread") boolean unread,
            @Header("Authorization") String token);

    @GET("/platform/api/requests/{messageSessionId}/messages/unread")
    public Call<List<Message>> pingMessages(@Path("messageSessionId") int messageSessionId, @Header("Authorization") String token);

}
