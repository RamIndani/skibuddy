package com.learninghorizon.skibuddy.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.Profile;
import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.interfaces.TagLineObserver;
import com.learninghorizon.skibuddy.utility.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramnivasindani on 11/17/15.
 */
public class TagLineDialog extends DialogFragment{

    List<TagLineObserver> observers = new ArrayList<TagLineObserver>();
    String tagLine;
    public TagLineDialog(){

    }

    EditText tagLineEditText;
    ImageView tagProfileImage;
    TextView tagUserName;
    TextView tagLength;
    LayoutInflater inflater;
    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tagline_dialog_layout, null);
        tagLineEditText = (EditText) view.findViewById(R.id.edittext_tag_line);
        tagLength = (TextView) view.findViewById(R.id.tag_length);

        Bundle bundle = getArguments();
        if(null != bundle) {
            tagLine = bundle.getString("tagLine");
            if(null != tagLine && !tagLine.isEmpty()){
                tagLineEditText.setText(tagLine);
                if(null != tagLength) {
                    tagLength.setText(tagLineEditText.getText().length() + "/130");
                }
            }
        }


        tagLineEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tagLength.setText(tagLineEditText.getText().length()+"/130");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Profile profile = Profile.getCurrentProfile();
        tagProfileImage = (ImageView) view.findViewById(R.id.edit_tag_pic);
        tagUserName = (TextView) view.findViewById(R.id.edit_tag_name);
        Picasso.with(getActivity()).load(profile.getProfilePictureUri(240, 240))
                .transform(new RoundedTransformation(50, 4))
                .error(R.mipmap.ic_launcher)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerCrop()
                .into(tagProfileImage);
        tagUserName.setText(profile.getFirstName().concat(" ").concat(profile.getLastName()));
        alertBuilder.setView(view);
        alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyObservers();
                //TODO: update in db and restapi
            }
        });
        alertBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return alertBuilder.create();
    }


    public void registerObserver(TagLineObserver observer){
        observers.add(observer);
    }

    private void notifyObservers(){
        for(TagLineObserver observer: observers) {
            observer.updateData(tagLineEditText.getText().toString());

        }
    }
}
