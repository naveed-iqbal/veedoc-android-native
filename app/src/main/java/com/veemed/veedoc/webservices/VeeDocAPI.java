package com.veemed.veedoc.webservices;

import com.veemed.CallActionsModel;
import com.veemed.veedoc.models.ChangePassword;
import com.veemed.veedoc.models.ConnectedCallPingModel;
import com.veemed.veedoc.models.Conversation;
import com.veemed.veedoc.models.Endpoint;
import com.veemed.veedoc.models.EndpointStatus;
import com.veemed.veedoc.models.EndpointsRequestModel;
import com.veemed.veedoc.models.Facility;
import com.veemed.veedoc.models.MarkMessagesReadRequestModel;
import com.veemed.veedoc.models.Message;
import com.veemed.veedoc.models.NewMessageBody;
import com.veemed.veedoc.models.PartnerSite;
import com.veemed.veedoc.models.PendingSession;
import com.veemed.veedoc.models.Session;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.models.SpecialistInformation;
import com.veemed.veedoc.models.Speciality;
import com.veemed.veedoc.models.State;
import com.veemed.veedoc.models.SupportGroupModel;
import com.veemed.veedoc.models.UserAPIRequest;
import com.veemed.veedoc.models.VerificationCode;
import com.veemed.veedoc.models.event.OffDayModel;
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
    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("/identity/connect/token")
    Call<TokenResponse> authenticateUser(@Field("client_id") String paramString1, @Field("client_secret") String paramString2, @Field("username") String paramString3, @Field("password") String paramString4, @Field("grant_type") String paramString5);

    @PUT("/platform/api/specialist/request/action")
    Call<SessionInfo> callActionAccept(@Body CallActionsModel paramCallActionsModel, @Header("Authorization") String paramString);

    @PUT("/platform/api/specialist/request/action")
    Call<Conversation> callActionDefer(@Body CallActionsModel paramCallActionsModel, @Header("Authorization") String paramString);

    @GET("/platform/api/specialist/request/{sessionId}/session")
    Call<SessionInfo> callActionReconnect(@Path("sessionId") int paramInt, @Header("Authorization") String paramString);

    @PUT("/platform/api/specialist/request/action")
    Call<Void> callActionReject(@Body CallActionsModel paramCallActionsModel, @Header("Authorization") String paramString);

    @PUT("/platform/api/user/password/change")
    Call<Void> changePassword(@Body ChangePassword paramChangePassword, @Header("Authorization") String paramString);

    @PUT("/platform/api/user/registration/complete")
    Call<String> completeRegistration(@Body UserAPIRequest paramUserAPIRequest, @Header("Authorization") String paramString);

    @GET("/platform/api/accessible/users/{index}/{pageSize}")
    Call<List<UserAPIRequest>> getChatUsers(@Path("pageSize") int paramInt1, @Path("index") int paramInt2, @Header("Authorization") String paramString);

    @GET("/platform/api/requests/deferred/last/message")
    Call<List<Conversation>> getConversations(@Header("Authorization") String paramString);

    @GET("/platform/api/endpoint/accessible/status")
    Call<List<EndpointStatus>> getEndPointStatus(@Header("Authorization") String paramString);

    @GET("/platform/api/endpoints/status/filtered/{status}/{filter}/{index}/{size}")
    Call<List<Endpoint>> getEndPoints(@Path("status") String paramString1, @Path("filter") String paramString2, @Path("index") int paramInt1, @Path("size") int paramInt2, @Header("Authorization") String paramString3);

    @POST("/platform/api/endpoint/accessible")
    Call<List<Endpoint>> getEndPointsNew(@Body EndpointsRequestModel request, @Header("Authorization") String paramString);

    @GET("/platform/api/specialist/schedule/{year}/{month}")
    Call<List<ScheduledEvent>> getEvents(@Path("year") int paramInt1, @Path("month") int paramInt2, @Header("Authorization") String paramString);

    @GET("/platform/api/requests/{sessionId}/messages/session/info")
    Call<Conversation> getMessageSessionInfoBySessionId(@Path("sessionId") int paramInt, @Header("Authorization") String paramString);

    @GET("/platform/api/requests/{messageSessionId}/messages/{pageIndex}/{length}/{unread}")
    Call<List<Message>> getMessages(@Path("messageSessionId") int paramInt1, @Path("pageIndex") int paramInt2, @Path("length") int paramInt3, @Path("unread") boolean paramBoolean, @Header("Authorization") String paramString);

    @GET("/platform/api/specialist/offdays/{year}/{month}/1")
    Call<List<OffDayModel>> getOffDays(@Path("year") int paramInt1, @Path("month") int paramInt2, @Header("Authorization") String paramString);

    @GET("/platform/api/specialist/requests/pending")
    Call<List<PendingSession>> getPendingRequests(@Header("Authorization") String paramString);

    @GET("/platform/api/endpoint/session/info/{id}")
    Call<SessionInfo> getSessionInfo(@Path("id") int paramInt, @Header("Authorization") String paramString);

    @GET("/platform/api/specialist/session/history/{year}/{month}/{day}/{param}")
    Call<List<Session>> getSessions(@Path("year") int paramInt1, @Path("month") int paramInt2, @Path("day") int paramInt3, @Path("param") int paramInt4, @Header("Authorization") String paramString);

    @GET("/platform/api/specialist/info")
    @Headers({"Content-Type: application/json"})
    Call<SpecialistInformation> getSpecialistInfo(@Header("Authorization") String paramString);

    @GET("/platform/api/speciality/all")
    Call<List<Speciality>> getSpecialities(@Header("Authorization") String paramString);

    @GET("/platform/api/state/1")
    Call<List<State>> getStates(@Header("Authorization") String paramString);

    @GET("/platform/api/user/support/groups")
    Call<List<SupportGroupModel>> getSupportGroups(@Header("Authorization") String paramString);

    @GET("/platform/api/user/info")
    @Headers({"Content-Type: application/json"})
    Call<UserAPIResponse> getUserAPIResponse(@Header("Authorization") String paramString);

    @POST("/platform/api/user/registration/init")
    Call<String> initRegistration(@Body UserAPIResponse paramUserAPIResponse);

    @PUT("/platform/api/requests/message/mark/read")
    Call<Void> markMessageRead(@Body MarkMessagesReadRequestModel paramMarkMessagesReadRequestModel, @Header("Authorization") String paramString);

    @PUT("/platform/api/specialist/request/ping/add")
    Call<Conversation> pingConnectedCallStatus(@Body ConnectedCallPingModel paramConnectedCallPingModel, @Header("Authorization") String paramString);

    @GET("/platform/api/requests/{messageSessionId}/messages/unread")
    Call<List<Message>> pingMessages(@Path("messageSessionId") int paramInt, @Header("Authorization") String paramString);

    @PUT("/platform/api/user/registration/verify")
    Call<UserAPIResponse> putVerificationCode(@Body VerificationCode paramVerificationCode, @Header("Authorization") String paramString);

    @POST("/platform/api/user/resend/verification/key")
    Call<Void> resendVerificationCode(@Header("Authorization") String paramString);

    @POST("/platform/api/requests/message/send")
    Call<Void> sendNewMessage(@Body NewMessageBody paramNewMessageBody, @Header("Authorization") String paramString);

    @GET("/platform/api/partnersites/get")
    Call<List<PartnerSite>> getPartnerSites(@Header("Authorization") String paramString);

    @GET("/platform/api/partnersite/facility/{id}")
    Call<List<Facility>> getFacilities(@Path("id") int paramInt, @Header("Authorization") String paramString);
}
