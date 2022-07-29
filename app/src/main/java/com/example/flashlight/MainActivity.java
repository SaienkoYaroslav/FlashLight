package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bFlash;
    private ConstraintLayout constraintLayout;
    private FlashClass flashClass;
    private boolean check = true;
    long mills = 100L;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onLongClick();
    }

    private void init() {
        bFlash = findViewById(R.id.button_flash);
        constraintLayout = findViewById(R.id.constr_lay);
        flashClass = new FlashClass(this);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }


    public void onClickFlash(View view) {

        if (flashClass.isFlashStatus()) {
            flashClass.flashOff();
            bFlash.setText("On");

            bFlash.setBackgroundResource(R.drawable.circle_green);

            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }

        } else {
            flashClass.flashOn();
            bFlash.setText("Off");

            bFlash.setBackgroundResource(R.drawable.circle_red);

            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
        }
    }

    void onLongClick() {
        bFlash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int backgroundColorWhite = getResources().getColor(R.color.white, null);
                int backgroundColorBlack = getResources().getColor(R.color.black_grey, null);

                if (check == true) {
                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = 1.0F;
                    getWindow().setAttributes(layout);
                    bFlash.setTextColor(backgroundColorBlack);
                    constraintLayout.setBackgroundColor(backgroundColorWhite);
                    check = false;
                }
                else {
                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = 0.2F;
                    getWindow().setAttributes(layout);
                    bFlash.setTextColor(backgroundColorWhite);
                    constraintLayout.setBackgroundColor(backgroundColorBlack);
                    check = true;
                }

                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (flashClass.isFlashStatus()) {
            flashClass.flashOff();
        }
    }
}