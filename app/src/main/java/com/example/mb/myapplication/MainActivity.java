package com.example.mb.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    private Button createButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        return button;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
