package com.veemed.veedoc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.veemed.veedoc.R;
import com.veemed.veedoc.models.CallActionsModel;
import com.veemed.veedoc.models.SessionInfo;
import com.veemed.veedoc.repositories.VeeDocRepository;
import com.veemed.veedoc.utils.Toast;
import com.veemed.veedoc.webservices.RetrofitCallbackListener;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Response;

public class KartCallActivity extends AppCompatActivity implements Session.SessionListener,
        PublisherKit.PublisherListener, View.OnTouchListener, View.OnClickListener, Session.SignalListener {

    private static final String TAG = KartCallActivity.class.getSimpleName();
    private static String API_KEY = "";
    private static String SESSION_ID = "";
    private static String TOKEN = "";
    private static final String LOG_TAG = TAG;
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;

    private ImageView ivCallCancel;
    private ImageView ivMic;
    private ImageView ivCamera;
    private ImageView ivResetCamera;

    private Publisher mPublisher;
    private Subscriber mSubscriber;

    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private float mLastScaleFactor;

    private boolean isAudioPublished = true;
    private boolean isVideoPublished = true;

    private SessionInfo sessionInfo;

    RetrofitCallbackListener<SessionInfo> sessionInfoCallbackListener = new RetrofitCallbackListener<SessionInfo>() {
        @Override
        public void onResponse(Call<SessionInfo> call, Response<SessionInfo> response, int requestID) {
            sessionInfo = response.body();
            initCall(sessionInfo);
        }

        @Override
        public void onFailure(Call<SessionInfo> call, Throwable t, int requestID) {

        }
    };

    private void initCall(SessionInfo sessionInfo) {
        API_KEY = sessionInfo.getHost();
        SESSION_ID = sessionInfo.getResourceId();
        TOKEN = sessionInfo.getToken();
        requestPermissions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kart_call);

        ivCallCancel = findViewById(R.id.ivCallEnd);
        ivCamera = findViewById(R.id.ivVideo);
        ivMic = findViewById(R.id.ivMic);
        ivResetCamera = findViewById(R.id.ivResetCamera);

        ivCallCancel.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        ivMic.setOnClickListener(this);
        ivResetCamera.setOnClickListener(this);

        mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
        mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);

        mSubscriberViewContainer.setOnTouchListener(this);
        VeeDocRepository userRepo = VeeDocRepository.getInstance();

        sessionInfo = (SessionInfo) getIntent().getSerializableExtra("session");
        if(sessionInfo == null) { // this means call requested from endpoints screen
            int id = getIntent().getIntExtra("sessionId", -1);
            if (id != -1) {
                userRepo.getSessionInfo(id, sessionInfoCallbackListener, 0);
            } else {
                Toast.makeText(this, "No session found", Toast.LENGTH_LONG).show();
                finish();
            }
        } else { // call accepted from currentSessions
            initCall(sessionInfo);
        }

        mScaleDetector = new ScaleGestureDetector(this, new KartCallActivity.ScaleListener());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);

            // initialize and connect to the session

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onSignalReceived(Session session, String type, String data, Connection connection) {
        String myConnectionId = session.getConnection().getConnectionId();
        if (connection != null && connection.getConnectionId().equals(myConnectionId)) {
            mSession.disconnect();
        }
    }

    @Override
    public void onConnected(Session session) {
        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView){
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        sendEndCallSignal();
        finish();
        Log.d(TAG, "onDisconnected: Session disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }

    private void sendResetCameraSignal() {
        mSession.sendSignal("ios", "__cmd__;reset;", mSession.getConnection());
    }

    private void sendMoveCameraSignal(float x, float y) {
        String panPoint = String.format("point %f %f", x, y);
        String screenWidth = String.format("screen_width %f", mSubscriberViewContainer.getWidth());
        String screenHeight = String.format("screen_height %f", mSubscriberViewContainer.getHeight());
        String command = String.format("__cmd__;ptz_start;%s;%s;%s;ptz_end;", panPoint, screenWidth, screenHeight);

        mSession.sendSignal("ios", command, mSession.getConnection());
    }

    private void sendZoomCameraSignal(float zoomFactorFloat) {
        String zoomFactor = String.format("zoom_scale %f", zoomFactorFloat);
        String screenWidth = String.format("screen_width %f", mSubscriberViewContainer.getWidth());
        String screenHeight = String.format("screen_height %f", mSubscriberViewContainer.getHeight());
        String command = String.format("__cmd__;ptz_start;%s;%s;%s;ptz_end;", zoomFactor, screenWidth, screenHeight);

        mSession.sendSignal("ios", command, mSession.getConnection());
    }

    private void sendEndCallSignal() {
        mSession.sendSignal("ios", "__cmd__;call_disconnect;", mSession.getConnection());
    }

    private void publishAudio(boolean bool) {
        mPublisher.setPublishAudio(bool);
        if(bool) {
            ivMic.setImageResource(R.drawable.ic_mic);
        } else {
            ivMic.setImageResource(R.drawable.ic_mic_off);
        }
        isAudioPublished = bool;
    }

    private void publishVideo(boolean bool) {
        mPublisher.setPublishVideo(bool);
        if(bool) {
            ivCamera.setImageResource(R.drawable.ic_video_cam);
        } else {
            ivCamera.setImageResource(R.drawable.ic_video_cam_off);
        }
        isVideoPublished = bool;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        mScaleDetector.onTouchEvent(motionEvent);
        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE || motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if(motionEvent.getPointerCount() == 1) {
                sendMoveCameraSignal(motionEvent.getX(), motionEvent.getY());
            }
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ivResetCamera:
                sendResetCameraSignal();
                break;

            case R.id.ivCallEnd:
                rejectCall();
                break;

            case R.id.ivVideo:
                publishVideo(!isVideoPublished);
                break;

            case R.id.ivMic:
                publishAudio(!isAudioPublished);
                break;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
           mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            if(mScaleFactor < mLastScaleFactor) {
                // TODO send zoom in signal
            } else if(mScaleFactor > mLastScaleFactor) {
                // TODO send zoom out signal
            }
            mLastScaleFactor = mScaleFactor;
            sendZoomCameraSignal(detector.getScaleFactor());
            // invalidate();
            return true;
        }
    }

    private void rejectCall() {
        CallActionsModel callActionsModel = new CallActionsModel();
        callActionsModel.setPerformedBy(Integer.valueOf(1));
        callActionsModel.setPerformedAction("EndSession");
        callActionsModel.setSpecialistRequestId(sessionInfo.getSpecialistRequestId());
        VeeDocRepository.getInstance().rejectCall(callActionsModel, new RetrofitCallbackListener<Void>() {
            public void onFailure(Call<Void> param1Call, Throwable param1Throwable, int param1Int) {
                Toast.makeText(KartCallActivity.this, "Failed to close the session", com.veemed.veedoc.utils.Toast.LENGTH_LONG).show();
            }

            public void onResponse(Call<Void> param1Call, Response<Void> param1Response, int param1Int) {
                if (param1Response.isSuccessful()) {
                    Toast.makeText(KartCallActivity.this, "Session closed", com.veemed.veedoc.utils.Toast.LENGTH_LONG).show();
                    mSession.disconnect();
                } else {
                    Toast.makeText(KartCallActivity.this, "Could not close the session", com.veemed.veedoc.utils.Toast.LENGTH_LONG).show();
                }
            }
        }, 0);
    }
}
