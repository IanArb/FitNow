package com.ianarbuckle.fitnow.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public class PopupFragment extends DialogFragment {

  private static final String TITLE_KEY = "title";
  private static final String SUB_KEY = "subTitle";

  int messageTitle;
  int messageSubTitle;

  public PopupFragment() {

  }

  public static PopupFragment newInstance(int title, int subTitle) {
    PopupFragment popupFragment = new PopupFragment();
    Bundle args = new Bundle();
    args.putInt(TITLE_KEY, title);
    args.putInt(SUB_KEY, subTitle);
    popupFragment.setArguments(args);
    return popupFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_popup, container, false);
    messageTitle = getArguments().getInt(TITLE_KEY);
    messageSubTitle = getArguments().getInt(SUB_KEY);

    View tvTitle = view.findViewById(R.id.title);
    ((TextView) tvTitle).setText(messageTitle);
    View tvSubTitle = view.findViewById(R.id.subTitle);
    ((TextView) tvSubTitle).setText(messageSubTitle);

    return getButtonListeners(view);
  }

  @NonNull
  private View getButtonListeners(View view) {
    Button yesButton = (Button) view.findViewById(R.id.yesButton);
    yesButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().finish();
      }
    });

    Button noButton = (Button) view.findViewById(R.id.noButton);
    noButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getDialog().dismiss();
      }
    });
    return view;
  }

}
