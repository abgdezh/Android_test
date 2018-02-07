package com.example.mb.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkVersion;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class MainActivity extends AppCompatActivity {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES
    };


    private Button createButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        return button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKSdk.login(this, sMyScope);

        /* Отправление сообщение вк
        VKParameters vkParameters = VKParameters.from();
        vkParameters.put(VKApiConst.USER_ID, 7490419);
        vkParameters.put(VKApiConst.MESSAGE, "HI!");
        VKRequest request = new VKRequest("messages.send", vkParameters);

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                System.out.println("WORKING");
            }
            @Override
            public void onError(VKError error) {
                System.out.println("ERROR");
                System.out.println(error);
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                System.out.println("STRANGE");
            }
        });*/

        Button vk_button = findViewById(R.id.vk_button);

        vk_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //переходим с первой на вторую активность
                Intent intent = new Intent(MainActivity.this, vk_dialogs.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        final LinearLayout linearLayout = findViewById(R.id.list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                editText.setText("");
                linearLayout.addView(createButton(text));
                ScrollView scrollView = findViewById(R.id.scroll);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
