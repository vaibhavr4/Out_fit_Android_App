package com.vaibhav.out_fit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.vaibhav.out_fit.R;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener{
  private static View view;

  private static EditText emailId;
  private static TextView submit, back;
  private FirebaseAuth autenticationRef;

  public ForgotPassword_Fragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.forgotpassword_layout, container,
            false);
    autenticationRef = FirebaseAuth.getInstance();
    initViews();
    setListeners();
    return view;
  }

  // Initialize the views
  private void initViews() {
    emailId = (EditText) view.findViewById(R.id.registered_emailid);
    submit = (TextView) view.findViewById(R.id.forgot_button);
    back = (TextView) view.findViewById(R.id.backToLoginBtn);

    // Setting text selector over textviews
    XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
    try {
      ColorStateList csl = ColorStateList.createFromXml(getResources(),
              xrp);

      back.setTextColor(csl);
      submit.setTextColor(csl);

    } catch (Exception e) {
    }

  }

  // Set Listeners over buttons
  private void setListeners() {
    back.setOnClickListener(this);
    submit.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.backToLoginBtn:

        // Replace Login Fragment on Back Presses
        new MainActivity().replaceLoginFragment();
        break;

      case R.id.forgot_button:

        // Call Submit button task
        submitButtonTask();
        break;

    }

  }

  private void submitButtonTask() {
    String getEmailId = emailId.getText().toString();

    // Pattern for email id validation
    Pattern p = Pattern.compile(Utils.regEx);

    // Match the pattern
    Matcher m = p.matcher(getEmailId);

    // First check if email id is not null else show error toast
    if (getEmailId.equals("") || getEmailId.length() == 0)

      new CustomToast().Show_Toast(getActivity(), view,
              "Please enter your Email Id.");

      // Check if email id is valid or not
    else if (!m.find())
      new CustomToast().Show_Toast(getActivity(), view,
              "Your Email Id is Invalid.");

      // Else submit email id and fetch passwod or do your stuff
    else
    {
      autenticationRef.sendPasswordResetEmail(getEmailId)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  Toast.makeText(getActivity(), "Please check your mail for further instructions",
                          Toast.LENGTH_LONG).show();
                }
              })
              .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  Toast.makeText(getActivity(), "Please try again later",
                          Toast.LENGTH_LONG).show();
                }
              });

    }

  }
}