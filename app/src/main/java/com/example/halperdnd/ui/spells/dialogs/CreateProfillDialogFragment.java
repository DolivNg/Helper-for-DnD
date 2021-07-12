package com.example.halperdnd.ui.spells.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.halperdnd.R;
import com.example.halperdnd.ui.spells.SpellsTableFragment;

public class CreateProfillDialogFragment extends DialogFragment  {
    private SpellsTableFragment fragment;

    private EditText editText = null;
    public CreateProfillDialogFragment( SpellsTableFragment fragment) {
        this.fragment=fragment;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /**builder*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        return builder
                .setTitle("Назовите профиль")
                .setView(inflater.inflate(R.layout.loyaut_dialog_spell, null))
                //.setMessage("Для закрытия окна нажмите ОК")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText =  getDialog().findViewById(R.id.eTProfillSpell);
                        fragment.createProfile(editText.getText().toString());
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
