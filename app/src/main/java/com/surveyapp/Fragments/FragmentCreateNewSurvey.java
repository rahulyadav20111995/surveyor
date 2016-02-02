package com.surveyapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.surveyapp.R;
import com.surveyapp.Utils;

/**
 * Created by Rahul yadav on 31-01-2016.
 */
public class FragmentCreateNewSurvey extends Fragment {

    private Button createNewSurveyButton;


    public FragmentCreateNewSurvey() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_survey,container,false);

        createNewSurveyButton = (Button) rootView.findViewById(R.id.createNewSurveyButton);

        createNewSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();        //it will show a dialog allow user to choose options to crete new survey or choose a template
            }
        });

        return rootView;
    }

    private void showDialog(){
        new MaterialDialog.Builder(getActivity())
                .customView(getDialogView(),false)
                .show();
    }

    private View getDialogView(){

        View dialogView  = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_create_survey,new LinearLayout(getActivity()),false);

        Button createNewSurvey = (Button) dialogView.findViewById(R.id.dialogCreateNewSurveyButton);
        Button createNewFromTemplate = (Button) dialogView.findViewById(R.id.dialogCreateNewSurveyFromTemplateButton);
        Button cancelDialog = (Button) dialogView.findViewById(R.id.dialogCancelButton);

        createNewSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"new Survey",Toast.LENGTH_LONG).show();
            }
        });

        createNewFromTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"browse ",Toast.LENGTH_LONG).show();
            }
        });

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"cancel",Toast.LENGTH_LONG).show();
            }
        });
        return dialogView;
    }


}
