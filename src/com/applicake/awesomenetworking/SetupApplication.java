package com.applicake.awesomenetworking;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class SetupApplication {

  //setup my bluetooth id

  private static final int REQUEST_ENABLE_BT = 1;  
  public static final String PREFS_ID_KEY = "id";
  public static final String TWITTER_ID = "twitter_id";


  public static void setUpBluetooth(Activity context, String id) {
    // TODO Auto-generated method stub

//    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context); //setup elsewere
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    if (mBluetoothAdapter == null) {
      // Device does not support Bluetooth
      return;
    }

    if (!mBluetoothAdapter.isEnabled()) {
      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      context.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    mBluetoothAdapter.setName(BluetoothScanner.BLUETOOTH_PREFIX + " " + id);
    
  }

  public static void setDiscoverable(Activity context) {

    //enable discovery of local device
    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
    context.startActivity(discoverableIntent);
    context.finish();
  }

}
