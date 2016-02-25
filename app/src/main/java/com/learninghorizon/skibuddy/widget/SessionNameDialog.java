package com.learninghorizon.skibuddy.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.interfaces.SessionNameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramnivasindani on 12/2/15.
 */

public class SessionNameDialog extends DialogFragment {

    List<SessionNameObserver> observers = new ArrayList<SessionNameObserver>();
    String sessionName;
    public SessionNameDialog(){

    }

    EditText edittextSessionName;
    LayoutInflater inflater;
    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.session_name_dialog_layout, null);
        edittextSessionName = (EditText) view.findViewById(R.id.edittext_session_name);

        alertBuilder.setView(view);
        alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyObservers();
                //TODO: update in db and restapi
            }
        });
        return alertBuilder.create();
    }


    public void registerObserver(SessionNameObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(){
        for(SessionNameObserver observer: observers) {
            observer.updateData(edittextSessionName.getText().toString());

        }
    }
}
