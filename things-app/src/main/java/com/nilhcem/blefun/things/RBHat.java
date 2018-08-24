package com.nilhcem.blefun.things;

import android.graphics.Color;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;
import com.google.android.things.contrib.driver.bmx280.Bmx280;
import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RBHat {

    private static final String TAG = RBHat.class.getSimpleName();

    // Rainbow HAT items
    private Bmx280 sensor;
    private AlphanumericDisplay segment;
    private Apa102 ledstrip;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;

    public void onCreate() {
        try {
            sensor = RainbowHat.openSensor();
            segment = RainbowHat.openDisplay();
            ledstrip = RainbowHat.openLedStrip();
            buttonA = RainbowHat.openButtonA();
            buttonB = RainbowHat.openButtonB();
            buttonC = RainbowHat.openButtonC();

            // press A: show temperature
            buttonA.setOnButtonEventListener(new Button.OnButtonEventListener() {
                @Override
                public void onButtonEvent(Button button, boolean pressed) {
                    try {
                        sensor.setTemperatureOversampling(Bmx280.OVERSAMPLING_1X);
                        segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
                        segment.display(sensor.readTemperature());
                        segment.setEnabled(true);
                    } catch (IOException e) {
                        Log.e(TAG, "Error initializing RB hat", e);
                    }
                }
            });

            // press C: reset digit screen
            buttonC.setOnButtonEventListener(new Button.OnButtonEventListener() {
                @Override
                public void onButtonEvent(Button button, boolean pressed) {
                    try {
                        segment.setEnabled(false);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });


        } catch (IOException e) {
            Log.e(TAG, "Error initializing RB hat", e);
        }
    }

    public void onDestroy() {
        try {
            if (ledstrip != null) {
                ledstrip.close();
            }
            if (sensor != null) {
                sensor.close();
            }
            if (segment != null) {
                segment.close();
            }
            if (buttonA != null) {
                buttonA.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing lucky cat resources", e);
        } finally {
            ledstrip = null;
            sensor = null;
            segment = null;
            buttonA = null;
        }
    }

}
