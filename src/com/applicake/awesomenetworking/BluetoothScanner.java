package com.applicake.awesomenetworking;


import com.applicake.awesomenetworking.BluetoothExchangeActivity.UserListAdapter;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class BluetoothScanner {
  
  private static final String LOG_CAT = "BluetoothScanner";
  public static String BLUETOOTH_PREFIX = "super_network";
  private ArrayList<String> mList;
  private Activity mActivity;
  private BluetoothAdapter mBluetoothAdapter;
  protected BaseAdapter mAdapter;
  protected ArrayList<String> mFoundDevicesList;
  
  // Create a BroadcastReceiver for ACTION_FOUND
  private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          // When discovery finds a device
          if (BluetoothDevice.ACTION_FOUND.equals(action)) {
              // Get the BluetoothDevice object from the Intent
              BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
              // Add the name and address to an array adapter to show in a ListView
              mList.add(device.getName());
              Log.d(LOG_CAT, "Found device: " + device.getName() + " " + device.getAddress());
          } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                  Log.d(LOG_CAT, "Finished discovery. Found devices " + mList.size());
                  for (String s : mList) {
                          //Log.d(LOG_CAT, s);
                          if (s.startsWith(BLUETOOTH_PREFIX)) {
                                  
                                  String id = s.substring(BLUETOOTH_PREFIX.length());
                                  Log.d(LOG_CAT, "Connect to: " + id);
                                  mFoundDevicesList.add(id);
                                  mAdapter.notifyDataSetChanged(); // not here
                          }
                  }
                  
                  
          }
      }
  };
  
  public BluetoothScanner(Activity context, UserListAdapter adapter, List<User> userList) {
    mActivity = context;
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    mAdapter = adapter;
    
//    mFoundDevicesList = userList;
  }
  
  public int getCount(){
    return 0;
  }
  
  public String getElementId(){
    return null; //device id
  }
  
  public static User getUserFromServer(String id){
    HttpClient client = getNewHttpClient();
    // execute request
    // parse result to User object
    // return user 
    User user = new User();
    return user;
  }
  
  public static HttpClient getNewHttpClient() {
    try {
      
      HttpParams params = new BasicHttpParams();
      HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

      ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, null);

      return new DefaultHttpClient(ccm, params);
    } catch (Exception e) {
      return new DefaultHttpClient();
    }
  }

  public List<User> getUserList() {
    return null;
  }
  
  protected void discover() {
    // Register the BroadcastReceiver
    
    mList = new ArrayList<String>();
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    mActivity.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    
    IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    mActivity.registerReceiver(mReceiver, filter2); // Don't forget to unregister during onDestroy
    
    
    Log.d(LOG_CAT, "Starting discovery...");
    mBluetoothAdapter.startDiscovery();
    
}
  

}
