package com.applicake.awesomenetworking;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.net.Uri;

import java.io.IOException;

public class Api {
  private static final Uri API_URL = Uri.parse("http://192.168.1.140:3001");
  private static final String PATH_USERS = "users";
  private HttpClient mClient;

  public Api(HttpClient client) {
    mClient = client;
  }

  public String getUserFromServer(String id) throws ClientProtocolException, IOException {
    Uri userUri = API_URL.buildUpon().appendPath(PATH_USERS).appendPath(id).build();
    HttpGet getRequest = new HttpGet(userUri.toString());
    HttpResponse getRespose = mClient.execute(getRequest);
    String response = EntityUtils.toString(getRespose.getEntity());
    return response;
  }

  public String getUserIdFromTwitter(String twitter) throws ClientProtocolException,
      IOException {
    Uri userUri = API_URL.buildUpon().appendPath(PATH_USERS).appendPath(twitter).build();
    return null;
  }

  public String createUser(User user) throws ClientProtocolException, IOException, JSONException {
    Uri userUri = API_URL.buildUpon().appendPath(PATH_USERS).build();
    HttpPost postRequest = new HttpPost(userUri.toString());

    HttpEntity postEntity = new StringEntity(user.getJSONObject().toString());
    postRequest.setEntity(postEntity);

    HttpResponse postRespose = mClient.execute(postRequest);
    String response = EntityUtils.toString(postRespose.getEntity());
    return response;

  }

}
