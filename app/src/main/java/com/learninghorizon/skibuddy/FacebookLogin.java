package com.learninghorizon.skibuddy;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.learninghorizon.skibuddy.AsyncTasks.CreateProfile;
import com.learninghorizon.skibuddy.AsyncTasks.LoadProfile;
import com.learninghorizon.skibuddy.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookLogin extends AppCompatActivity {
    CallbackManager callbackManager;
    private static final String TAG = "FacebookLogin";
    private final Logger logger = Logger.getLogger("SkiBuddy");
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  getHash();*/
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_facebook_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "user_friends","user_photos");

        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        final String[] user_id = {null};
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                logger.log(Level.INFO, "Access token : " + loginResult.getAccessToken().getToken());

                GraphRequestBatch batch = new GraphRequestBatch(
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject jsonObject,
                                            GraphResponse response) {
                                        try {
                                            logger.log(Level.INFO, "User response "+jsonObject.getString("id"));

                                            user_id[0] =jsonObject.getString("id");
                                            new GraphRequest(
                                                    loginResult.getAccessToken(),
                                                    "/me" ,
                                                    null,
                                                    HttpMethod.GET,
                                                    new GraphRequest.Callback() {
                                                        public void onCompleted(GraphResponse response) {

                                                           User user = createProfile();
                                                            Log.e(TAG, "FACEBOOK ID : "+user.getFacebookId());
                                                           // try {
                                                                new CreateProfile(FacebookLogin.this).execute(user);
                                                           /* } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            } catch (ExecutionException e) {
                                                                e.printStackTrace();
                                                            }*/
                                                            startMainActivity();
                                                        }
                                                    }
                                            ).executeAsync();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                })
                        /*GraphRequest.newMyFriendsRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONArrayCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONArray jsonArray,
                                            GraphResponse response) {
                                        logger.log(Level.INFO, "friends : "+response.getRawResponse());
                                    }
                                })*/
                );
                batch.addCallback(new GraphRequestBatch.Callback() {
                    @Override
                    public void onBatchCompleted(GraphRequestBatch graphRequests) {
                        // Application code for when the batch finishes
                    }
                });
                batch.executeAsync();



            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });

        if(accessToken!=null){
            startMainActivity();
        }
    }

    private void startMainActivity() {
        //try {
             new LoadProfile(FacebookLogin.this).execute(createProfile());
       /* } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    private void getHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.learninghorizon.skibuddy", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {


        }
    }

    private User createProfile(){
        Profile profile = Profile.getCurrentProfile();
        String profilePicture = ""+profile.getProfilePictureUri(500,500);
        logger.log(Level.INFO, "User profile : "+profilePicture);
        String firstName = profile.getFirstName();
        String lastName = profile.getLastName();
        String facebookId = profile.getId();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setFacebookId(facebookId);
        return user;
    }
}
