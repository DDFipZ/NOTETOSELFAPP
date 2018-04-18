package com.example.ddfipz.notetoselfapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.content.Context.MODE_PRIVATE;

public class SettingsActivity extends AppCompatActivity {


    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private Boolean mSound;
    static final int FAST = 0;
    static final int SLOW = 1;
    static final int NONE = 2;

    private int mAnimOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mEditor = mPrefs.edit();

        // This part of the code is
        // only focusing on changing
        // the sound on off setting.
        // The checkbox in the settings
        // menu is used to do this.
        mSound = mPrefs.getBoolean("sound", true);

        CheckBox cbSound = (CheckBox) findViewById(R.id.cbOnOff);

        if(mSound){
            cbSound.setChecked(true);
        }
        else{
            cbSound.setChecked(false);
        }

        cbSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.i("sound= ", "" + mSound);
                Log.i("isChecked= ", "" + isChecked);

                //If sounds is true, make is false, if false, make true

                mSound = !mSound;
                mEditor.putBoolean("sound", mSound);
            }
        });

        //This code focuses on just the
        //radio group and buttons.

        //Radio buttons

        mAnimOption = mPrefs.getInt("anim option", FAST);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //Deselect all the buttons

        radioGroup.clearCheck();

        //Switch to see which is selected

        switch (mAnimOption){

            case FAST:
                radioGroup.check(R.id.rbFast);
                break;
            case SLOW:
                radioGroup.check(R.id.rbSlow);
                break;
            case NONE:
                radioGroup.check(R.id.rbNone);
                break;
        }
//TODO possible error here, i added @suppressLint to solve an issue with checkedId
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if(rb != null && checkedId > -1){

                    switch (rb.getId()){

                        case R.id.rbFast:
                            mAnimOption = FAST;
                            break;
                        case R.id.rbSlow:
                            mAnimOption = SLOW;
                            break;
                        case R.id.rbNone:
                            mAnimOption = NONE;
                            break;
                    }

                    //Now to edit it:
                    mEditor.putInt("anim option", mAnimOption);
                }
            }
        });



        Button btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
    // To save all the changes
    // that have been made we
    // override the onPause cycle
    // to make sure that the shared
    // preferences will have to be
    // updated min and max once

    @Override
    protected void onPause(){
        super.onPause();
        mEditor.commit();
    }
}
