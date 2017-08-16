package com.tourplan.research.researchcc;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email,user_likes,user_photos,user_posts,manage_pages,publish_pages,publish_actions,publish_actions");
        loginButton.setHeight(100);
        loginButton.setTextColor(Color.WHITE);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginButton.setCompoundDrawablePadding(0);
        getLoginDetails(loginButton);
    }

    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    private void getLoginDetails(final LoginButton loginButton) {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*Profile profile = Profile.getCurrentProfile();
                if (profile!= null) {
                    Log.println(Log.INFO,"******* Profile:",profile.toString());
                }*/
                if (loginResult != null) {
                   getUserDetails(loginResult);
                } else {
                    Log.println(Log.INFO,"BOMBLA","**********");
                }
            }

            @Override
            public void onCancel() {
                Log.println(Log.INFO,"****CANCEL","****");

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
    }

    private void getUserDetails(LoginResult loginResult) {
        Log.println(Log.INFO,"getUserDetails","***********");
        Log.println(Log.INFO,"getUserDetails",loginResult.getAccessToken().getToken());
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject json_object, GraphResponse response) {
                Log.println(Log.INFO,"**********Login","complete");
                if (json_object == null) {
                    Log.println(Log.INFO,"Object:","NULL");
                } else {
                    Log.println(Log.INFO,"Object:","NOT NULL");
                    try {
                        // User likes
                        JSONArray posts = json_object.getJSONObject("likes").optJSONArray("data");
                        for (int i = 0; i < posts.length(); i++) {

                            JSONObject post = posts.optJSONObject(i);
                            int id = post.optInt("id");
                            String category = post.optString("category");
                            String name = post.optString("name");
                            int count = post.optInt("likes");
                            String location = post.optString("location");

                           // Log.e("id -", id + " | name -" + name + " | category-" + category + " | likes count -" + count + " | location-" + location);
                        }

                        //User Photos
                        JSONArray photo = json_object.getJSONObject("photos").optJSONArray("data");
                        int photoId=0;
                        String photoCreatedTime=null,from=null,icon=null,photoLink=null,photoName=null,picture=null,photoPlace=null;

                        for (int i = 0; i < photo.length(); i++) {
                            JSONObject photosObj = photo.getJSONObject(i);
                            Log.println(Log.INFO,"Photos: ",photosObj.toString());
                            if (photosObj.has("id")) {
                                photoId = photosObj.getInt("id");
                            }
                            if (photosObj.has("name")) {
                                photoName = photosObj.getString("name");
                            }
                            if (photosObj.has("place")) {
                                photoPlace = photosObj.getString("place");
                            }
                            if (photosObj.has("picture")) {
                                picture = photosObj.getString("picture");
                            }
                            if (photosObj.has("icon")) {
                                icon = photosObj.getString("icon");
                            }
                            if (photosObj.has("link")) {
                                photoLink = photosObj.getString("link");
                            }
                            if (photosObj.has("created_time")) {
                                photoCreatedTime = photosObj.getString("created_time");
                            }
                            if (photosObj.has("from")) {
                                from = photosObj.getString("from");
                            }

                             //Log.println(Log.INFO,"id - ",photoId+" | name - "+photoName+" | photoPlace - "+photoPlace+" | picture - "+picture+" | icon - "+icon+
                             // "| photoLink - "+photoLink+" | photoCreatedTime - "+photoCreatedTime+" | from - "+from);
                        }

                        //User Album
                        JSONArray albums = json_object.getJSONObject("albums").optJSONArray("data");
                        int albumId=0,count=0;
                        String name=null,createdTime=null,desc=null,event=null,link=null,location=null,place=null;

                        for (int i = 0; i < albums.length(); i++) {
                            JSONObject albumObj = albums.getJSONObject(i);
                            if (albumObj.has("id")) {
                                albumId = albumObj.optInt("id");
                            }
                            if (albumObj.has("name")) {
                                name = albumObj.optString("name");
                            }
                            if (albumObj.has("count")) {
                                count = albumObj.optInt("count");
                            }
                            if(albumObj.has("created_time")) {
                                createdTime = albumObj.getString("created_time");
                            }
                            if (albumObj.has("description"))
                            {
                                desc = albumObj.getString("description");
                            }
                            if (albumObj.has("event")) {
                                event = albumObj.getString("event");
                            }
                            if (albumObj.has("link")) {
                                link = albumObj.getString("link");
                            }
                            if (albumObj.has("location")) {
                                location = albumObj.getString("location");
                            }
                            if (albumObj.has("place")) {
                                place = albumObj.getString("place");
                            }

                            //Log.println(Log.INFO,"id -", albumId+" | name -"+name+ " | count-"+ count+ " | createdTime -" + createdTime+"| description - "+desc
                              //    +"| link-"+ link+ " | place-"+place+" | event - "+event+" | location - "+location);
                        }

                        //User Pictures
                        JSONArray pic = json_object.getJSONObject("picture").optJSONArray("data");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "likes{id,category,name,location,likes},albums{id,name,count,created_time,description,event,link,location,place}," +
                "photos{album,created_time,from,id,name,name_tags,place,target,tags{name,tagging_user},likes{name,username,link}}");
        request.setParameters(permission_param);
        request.executeAsync();
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
}
