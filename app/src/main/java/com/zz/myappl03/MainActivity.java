package com.zz.myappl03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bRight, bLeft;
    TextView tRight, tLeft;
    SeekBar seekbar;
    TextView[] views;
    String TAG = "com.ahmadizamzama.lab03.sharedprefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bRight = findViewById(R.id.bottomright_button);
        bLeft = findViewById(R.id.bottomleft_button);
        tLeft = findViewById(R.id.topleft_textview);
        tRight = findViewById(R.id.topright_textview);
        seekbar = findViewById(R.id.seekbar);
        layout = findViewById(R.id.activity_main_layout);
        views = new TextView[]{bRight, bLeft, tRight, tLeft};
        bRight.setOnClickListener(this);
        bLeft.setOnClickListener(this);
        tRight.setOnClickListener(this);
        tLeft.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lastprogess;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                for (TextView x: views) {
                    x.setTextSize(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                lastprogess = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Snackbar snackbar= Snackbar.make(layout, "Font Size Changed To " +  seekBar.getProgress() + "sp", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seekBar.setProgress(lastprogess);
                        for (TextView x: views) {
                            x.setTextSize(lastprogess);
                        }
                        Snackbar.make(layout, "Font Size Reverted Back To " + lastprogess + "sp", Snackbar.LENGTH_LONG);
                    }
                });
                snackbar.setActionTextColor(Color.BLUE);
                View snackBarView = snackbar.getView();
                TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
        setInitialvalues();
    }

    private void setInitialvalues() {
        for (TextView x: views) {
            x.setText(sharedPreferences.getString(x.getTag().toString(),"0"));
            seekbar.setProgress(30);
        }
    }

    @Override
    public void onClick(View view) {
        TextView x = (TextView)view;
        x.setText("" +(Integer.parseInt(x.getText().toString()) + 1));
        editor.putString(x.getTag().toString(), x.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInitialvalues();
    }
}