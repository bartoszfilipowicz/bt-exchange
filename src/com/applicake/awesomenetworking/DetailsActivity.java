package com.applicake.awesomenetworking;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.details_view);
    
    Bundle extras = getIntent().getExtras();
    String email = extras.getString(SetupApplication.TWITTER_ID);
    
    TextView emailEt = (TextView) findViewById(R.id.twitter);
    emailEt.setText(email);
    
    ImageView userImage = (ImageView) findViewById(R.id.photo);
    
    GravatarDowloader downloader = GravatarDowloader.getInstance();
    downloader.download(email, userImage);
    
    
  }

}
