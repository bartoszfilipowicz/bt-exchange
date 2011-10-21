package com.applicake.awesomenetworking;

import org.json.JSONException;
import org.json.JSONObject;

public class User { //User bean
  private int id;
  private String twitterId;
  
  public User(String twitter) {
    this.twitterId = twitter;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getTwitterId() {
    return twitterId;
  }
  public void setTwitterId(String twitterId) {
    this.twitterId = twitterId;
  }
  
  public static User fromJSONObject(String jsonInString) throws JSONException{
    
    JSONObject userObject = new JSONObject(jsonInString);
    User user = new User("xx");
    user.setId(userObject.getInt("id"));
    user.setTwitterId(userObject.getString("twitter"));
    return user;
  }
  public JSONObject getJSONObject() throws JSONException {
    JSONObject object = new JSONObject();
    object.put("id", id);
    object.put("twitter", twitterId);
    return object;
  }
}
