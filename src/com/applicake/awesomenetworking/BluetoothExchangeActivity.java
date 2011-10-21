package com.applicake.awesomenetworking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BluetoothExchangeActivity extends Activity {
  private BluetoothScanner mScanner;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    String twitterId = prefs.getString(SetupApplication.TWITTER_ID, "");
    if ("".equals(twitterId)) {
      Intent intent = new Intent(this, SetupUserDataActivity.class);
      startActivity(intent);
    }
    
    SetupApplication.setUpBluetooth(this, twitterId);

    final Activity activity = this;

    Button scan = (Button) findViewById(R.id.bn_scan);

    scan.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        mScanner.discover();
      }
    });

    Button makeMeDiscoverable = (Button) findViewById(R.id.make_discoverable);
    makeMeDiscoverable.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        SetupApplication.setDiscoverable(activity);
      }
    });
    
    Button setup = (Button) findViewById(R.id.setup);
    setup.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), SetupUserDataActivity.class));
      }
    });
    
    

    ArrayList<User> userList = new ArrayList<User>();

    ListView list = (ListView) findViewById(R.id.list);
    UserListAdapter adapter = new UserListAdapter(userList, this); //set user list
    mScanner = new BluetoothScanner(this, adapter, userList);
    list.setAdapter(adapter);
    
    list.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        User user = (User) adapter.getItemAtPosition(position);
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(SetupApplication.TWITTER_ID, user.getTwitterId());
        startActivity(intent);
        
      }
    });

  }

  public static class UserListAdapter extends BaseAdapter {
    private List<User> mUserList;
    private LayoutInflater mInflater;

    public UserListAdapter(List<User> users, Context context) {
      super();
      mInflater = LayoutInflater.from(context);
      mUserList = users;

    }

    @Override
    public int getCount() {
      if (mUserList == null)
        return 0;
      return mUserList.size();
    }

    @Override
    public Object getItem(int pos) {
      if (mUserList == null)
        return null;
      return mUserList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
      if (mUserList == null)
        return 0;
      return mUserList.get(pos).getId();
    }

    //view building
    @Override
    public View getView(int pos, View convertedView, ViewGroup parent) {
      View view = convertedView;
      if (convertedView == null)
        view = mInflater.inflate(R.layout.user_item, null);

      TextView twitterTv = (TextView) view.findViewById(R.id.tv_twitter);
      twitterTv.setText(mUserList.get(pos).getTwitterId());

      return view;
    }

  }

}