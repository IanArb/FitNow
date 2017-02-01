package com.ianarbuckle.fitnow.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 01/12/2016.
 * Reference - https://developer.android.com/reference/android/app/DialogFragment.html
 */

public class ErrorDialogFragment extends DialogFragment {

  private static final String MESSAGE_KEY = "message";

  int messageTitle;

  public ErrorDialogFragment() {

  }

  public static ErrorDialogFragment newInstance(int title) {
    ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
    Bundle args = new Bundle();
    args.putInt(MESSAGE_KEY, title);
    errorDialogFragment.setArguments(args);
    return errorDialogFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_error_dialog, container, false);
    messageTitle = getArguments().getInt(MESSAGE_KEY);
    View textView = view.findViewById(R.id.errorMessage);
    ((TextView) textView).setText(messageTitle);

    Button button = (Button) view.findViewById(R.id.errorButton);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getDialog().dismiss();
      }
    });
    return view;
  }

}
