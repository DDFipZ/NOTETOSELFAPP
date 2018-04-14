package com.example.ddfipz.notetoselfapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

private NoteAdapter mNoteAdapter;
private boolean mSound;
private int mAnimOption;
private SharedPreferences mPrefs;

//To update the app from settings we override onResume

    @Override
    protected void onResume(){
        super.onResume();

        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("sound", true);
        mAnimOption = mPrefs.getInt("anim option", SettingsActivity.FAST);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hello World! 222

        mNoteAdapter = new NoteAdapter();

        ListView listNote = (ListView) findViewById(R.id.listView);
        listNote.setAdapter(mNoteAdapter);

        //TODO this is how to make a listview an onclick listener!!! Apply this in the shopping list app and combine it with the knowledge of the imageviews to be able to select items for deletion

        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id) {
                //create a temp note which is a reference
                //to the note that has just been clicked

                Note tempNote = mNoteAdapter.getItem(whichItem);

                // create a new dialog window

                DialogShowNote dialog = new DialogShowNote();
                dialog.sendNoteSelected(tempNote);
                //send in a reference to the note to be shown

                dialog.show(getFragmentManager(),"");
            }
        });

    }
    public void createNewNote(Note n) {

        mNoteAdapter.addNote(n);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this,SettingsActivity.class);
            startActivity(myIntent);
            return true;
        }

        if (id == R.id.action_add) {
            DialogNewNote dialog = new DialogNewNote();
            dialog.show(getFragmentManager(), "");
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public class NoteAdapter extends BaseAdapter {
        private JSONSerializer mSerializer;
        List<Note> noteList = new ArrayList<Note>();

        public NoteAdapter(){
            mSerializer = new JSONSerializer("NoteToSelf.json",MainActivity.this.getApplicationContext());

            try{
                noteList = mSerializer.load();
            }catch (Exception e){
                noteList = new ArrayList<Note>();
                Log.e("Error Loading Notes: ", "", e);
            }

        }
        //To save the notes we make a new method called saveNotes
        public void saveNotes() {
            try{
                mSerializer.save(noteList);
            }catch (Exception e){
                Log.e("Error Saving Notes: ","", e);
            }

        }

            @Override
            public int getCount() {
                return noteList.size();
            }

            public Note getItem(int whichItem) {
                return noteList.get(whichItem);
            }

            @Override
            public long getItemId(int whichItem) {
                return whichItem;
            }


            @Override
            public View getView(int whichItem, View view, ViewGroup viewGroup) {

                //Test to see if view has been properly implemented

                if( view == null) {

                    //If not then we will do so here
                    //by creating a LayoutInflater

                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    //Now we instantiate view using inflater.inflate using the listitem Layout

                    view = inflater.inflate(R.layout.listitem, viewGroup, false);

                    //false parameter is necessary because of the way we want to use listitem
                }

                //Grab a reference to all out textView and ImageView widgets

                TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
                TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
                ImageView ivImportant = (ImageView) view.findViewById(R.id.imageViewImportant);
                ImageView ivTodo = (ImageView) view.findViewById(R.id.imageViewTodo);
                ImageView ivIdea = (ImageView) view.findViewById(R.id.imageViewIdea);

                //Now, to hide any unwanted imageviews:

                Note tempNote = noteList.get(whichItem);

                if(!tempNote.isImportant()){
                    ivImportant.setVisibility(View.GONE);
                }

                if(!tempNote.isTodo()){
                    ivTodo.setVisibility(View.GONE);
                }

                if(!tempNote.isIdea()){
                    ivIdea.setVisibility(View.GONE);
                }

                // Add the text to the heading and description

                txtTitle.setText(tempNote.getTitle());
                txtDescription.setText(tempNote.getDescription());

                return view;
                }

                public void addNote (Note n){

                    noteList.add(n);
                    notifyDataSetChanged();
                }

            }

    @Override
    protected void onPause() {
        super.onPause();
        mNoteAdapter.saveNotes();
    }
}