package com.example.ddfipz.notetoselfapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Note mTempNote = new Note();
    public void createNewNote(Note n){
        mTempNote = n;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //Hello World! 222
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShowNote dialog = new DialogShowNote();
                dialog.sendNoteSelected(mTempNote);
                dialog.show(getFragmentManager(),"123");
            }
        });

    }


}
