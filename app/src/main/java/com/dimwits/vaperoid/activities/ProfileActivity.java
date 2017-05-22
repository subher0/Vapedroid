package com.dimwits.vaperoid.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimwits.vaperoid.R;
import com.dimwits.vaperoid.fragments.UnauthenticatedFragment;
import com.dimwits.vaperoid.requests.UserRequest;
import com.dimwits.vaperoid.requests.entities.UserEntity;
import com.dimwits.vaperoid.utils.listeners.ResponseListener;
import com.dimwits.vaperoid.utils.network.NetworkConstants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by farid on 5/23/17.
 */

public class ProfileActivity extends Activity implements ResponseListener {
    private ImageView userPictureView;
    private TextView usernameField;
    private TextView emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userPictureView = (ImageView) findViewById(R.id.profile_picture);
        usernameField = (TextView) findViewById(R.id.profile_username);

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                this.getString(R.string.shared_pref_file), Context.MODE_PRIVATE);
        String sessionId = sharedPreferences.getString(UnauthenticatedFragment.SESSION_ID_KEY, null);
        UserRequest userRequest = new UserRequest();
        userRequest.getUserBySessionId(this, sessionId);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResponseFinished(String response, boolean isSuccessful) {
        Gson gson = new Gson();
        UserEntity user = gson.fromJson(response, UserEntity.class);
        usernameField.setText(user.getLogin());
        Picasso.with(this).load(NetworkConstants.IP_ADDRESS + "/api/file/" + user.getAvatar())
                .into(userPictureView);
    }
}
