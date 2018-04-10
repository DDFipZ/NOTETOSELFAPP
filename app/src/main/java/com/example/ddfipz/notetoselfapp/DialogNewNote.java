package com.example.ddfipz.notetoselfapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class DialogNewNote extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_note,null);

        final EditText editTitle = (EditText) dialogView.findViewById(R.id.txtTitle);
        final EditText editDescription = (EditText) dialogView.findViewById(R.id.txtDescription);
        final CheckBox checkBoxIdea = (CheckBox) dialogView.findViewById(R.id.checkBoxIdea);
        final CheckBox checkBoxTodo = (CheckBox) dialogView.findViewById(R.id.checkBoxTodo);
        final CheckBox checkBoxImportant = (CheckBox) dialogView.findViewById(R.id.checkBoxImportant);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);
        final Button buttonOK = (Button) dialogView.findViewById(R.id.buttonOK);

        builder.setView(dialogView).setMessage("Add a new note");
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Note newNote = new Note();
                newNote.setTitle(editTitle.getText().toString());
                newNote.setDescription(editDescription.getText().toString());
                newNote.setIdea(checkBoxIdea.isChecked());
                newNote.setImportant(checkBoxImportant.isChecked());
                newNote.setTodo(checkBoxTodo.isChecked());

                MainActivity callingActivity = (MainActivity) getActivity();
                callingActivity.createNewNote(newNote);
                dismiss();
            }

        });
        return builder.create();
    }
}
