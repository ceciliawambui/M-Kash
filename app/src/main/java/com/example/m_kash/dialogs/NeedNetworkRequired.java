package com.example.m_kash.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class NeedNetworkRequired extends DialogFragment {
    private  static  NeedNetworkRequired needNetworkRequired= new NeedNetworkRequired();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Internet ");
        builder.setMessage("Please check if you are connected to a network ");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder.create();
    }
    public static  NeedNetworkRequired getInstance(){
        if (needNetworkRequired==null) {
            return new NeedNetworkRequired();
        }else{
            return  needNetworkRequired;
        }
    }

}
