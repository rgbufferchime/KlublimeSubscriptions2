/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bufferchime.klublimesubscriptions;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.bufferchime.klublimesubscriptions.billing.BillingManager;
import com.bufferchime.klublimesubscriptions.billing.BillingProvider;
import com.bufferchime.klublimesubscriptions.skulist.AcquireFragment;

/**
 * Example game using Play Billing library.
 *
 * Please follow steps inside the codelab to understand the best practices for this new library.
 */
public class GamePlayActivity extends AppCompatActivity implements BillingProvider {
    // Debug tag, for logging
    private static final String TAG = "GamePlayActivity";

    // Tag for a dialog that allows us to find it when screen was rotated
    private static final String DIALOG_TAG = "dialog";

    private BillingManager mBillingManager;
    private AcquireFragment mAcquireFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Set Portrait


        // Try to restore dialog fragment if we were showing it prior to screen rotation

        // Create and initialize BillingManager which talks to BillingLibrary
        mBillingManager = new BillingManager(this);

        // Specify purchase and drive buttons listeners
        // Note: This couldn't be done inside *.xml for Android TV since TV layout is inflated
        // via AppCompat
        findViewById(R.id.button_purchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPurchaseButtonClicked(view);
            }
        });


        findViewById(R.id.updateaddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GamePlayActivity.this, UserDetails.class));
            }
        });



    }

    @Override
    public BillingManager getBillingManager() {
        return mBillingManager;
    }

    /**
     * User clicked the "Buy Gas" button - show a purchase dialog with all available SKUs
     */
    public void onPurchaseButtonClicked(final View arg0) {
        Log.d(TAG, "Purchase button clicked.");

        if (mAcquireFragment == null) {
            mAcquireFragment = new AcquireFragment();
        }

        if (!isAcquireFragmentShown()) {
            mAcquireFragment.show(getSupportFragmentManager(), DIALOG_TAG);
        }
    }


    /**
     * Remove loading spinner and refresh the UI
     */


    /**
     * Show an alert dialog to the user
     * @param messageId String id to display inside the alert dialog
     */
    @UiThread
    void alert(@StringRes int messageId) {
        alert(messageId, null);
    }

    /**
     * Show an alert dialog to the user
     * @param messageId String id to display inside the alert dialog
     * @param optionalParam Optional attribute for the string
     */
    @UiThread
    void alert(@StringRes int messageId, @Nullable Object optionalParam) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new RuntimeException("Dialog could be shown only from the main thread");
        }

        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setNeutralButton("OK", null);

        if (optionalParam == null) {
            bld.setMessage(messageId);
        } else {
            bld.setMessage(getResources().getString(messageId, optionalParam));
        }

        bld.create().show();
    }

    /**
     * Enables or disables the "please wait" screen.
     */


    /**
     * Update UI to reflect model
     */


    public boolean isAcquireFragmentShown() {
        return mAcquireFragment != null && mAcquireFragment.isVisible();
    }
}
