package com.ianarbuckle.fitnow.walking.walkingtimer.results.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class GalleryFragment extends BaseFragment implements GalleryView {

  GridLayoutManager gridLayoutManager;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  GalleryAdapter adapter;

  GalleryPresenterImpl presenter;

  public static Fragment newInstance() {
    return new GalleryFragment();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.getUploads();
  }

  @Override
  protected void initPresenter() {
    presenter = new GalleryPresenterImpl(this, FitNowApplication.getAppInstance().getDatabaseHelper());
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_gallery, container, false);
    ButterKnife.bind(this, view);
    recyclerView.setHasFixedSize(true);
    gridLayoutManager = new GridLayoutManager(getContext(), 2);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(adapter);
    return view;
  }

  @Override
  public void setAdapter(GalleryAdapter adapter) {
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void showProgress() {
    showProgressDialog();
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }
}
