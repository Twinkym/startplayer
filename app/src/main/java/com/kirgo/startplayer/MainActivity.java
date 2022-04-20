package com.kirgo.startplayer;

import androidx.appcompat.app.AppCompatActivity;
import com.kirgo.startplayer.MultitouchView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MultitouchView myTouchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTouchView = new MultitouchView(this);
        setContentView(myTouchView);

    }


}