package com.example.ddfipz.notetoselfapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import javax.swing.text.html.ImageView;

public class DialogShowNote extends DialogFragment {

    private Note mNote;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_show_note,null);

        ImageView imageViewImportant = (ImageView) dialogView.findViewById(R.id.imageViewImportant);
        return null;
   }
    public void sendNoteSelected (Note noteSelected){
        mNote = noteSelected;
    }
}
