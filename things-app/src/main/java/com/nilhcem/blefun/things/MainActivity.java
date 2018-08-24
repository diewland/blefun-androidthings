package com.nilhcem.blefun.things;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.nilhcem.blefun.common.Ints;

public class MainActivity extends Activity {

    private static final String TAG = Activity.class.getSimpleName();

    private final RBHat rbHat = new RBHat();
    private final GattServer mGattServer = new GattServer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rbHat.onCreate();

        mGattServer.onCreate(this, new GattServer.GattServerListener() {
            @Override
            public byte[] onCounterRead() {
                return Ints.toByteArray(99);
            }

            @Override
            public void onInteractorWritten(String value) {
                Log.d(TAG, "client message ==> " + value);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGattServer.onDestroy();
        rbHat.onDestroy();
    }
}
