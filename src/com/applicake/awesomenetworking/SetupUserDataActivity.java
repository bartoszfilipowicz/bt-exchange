package com.applicake.awesomenetworking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetupUserDataActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    final EditText twitterTv = (EditText) findViewById(R.id.tv_twitter);
    Button saveButton = (Button) findViewById(R.id.bn_save);

    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    String userTwitter = prefs.getString(SetupApplication.TWITTER_ID, "");
    if (!"".equals(userTwitter)) {

    }
    saveButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        String twitterId = twitterTv.getText().toString();
        prefs.edit().putString(SetupApplication.TWITTER_ID, twitterId).commit();
        
      }
    });
  }

}
