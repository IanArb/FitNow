package com.ianarbuckle.fitnow.running.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.GalleryDialogFragment;
import com.ianarbuckle.fitnow.utils.RecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class RunGalleryFragment extends BaseFragment implements RunGalleryView {

  GridLayoutManager gridLayoutManager;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.rlEmptyMessage)
  RelativeLayout rlEmptyMessage;

  RunGalleryAdapter adapter;

  RunGalleryPresenterImpl presenter;

  public static Fragment newInstance() {
    return new RunGalleryFragment();
  }

  @Override
  protected void initPresenter() {
    presenter = new RunGalleryPresenterImpl(this, FitNowApplication.getAppInstance().getDatabaseHelper());
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.getUploads();
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
    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Bundle bundle = presenter.getBundle();
        bundle.putInt(Constants.POSITION_KEY, position);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        GalleryDialogFragment runGalleryDialogFragment = GalleryDialogFragment.newInstance();
        runGalleryDialogFragment.setArguments(bundle);
        runGalleryDialogFragment.show(fragmentTransaction, Constants.TAG_GALLERY_FULLSCREEN_FRAGMENT);
      }

      @Override
      public void onLongItemClick(View view, int position) {

      }
    }));
    return view;
  }

  @Override
  public void showProgress() {
    showProgressDialog();
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }

  @Override
  public void setAdapter(RunGalleryAdapter adapter) {
    recyclerView.setVisibility(View.VISIBLE);
    rlEmptyMessage.setVisibility(View.GONE);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void showErrorMessage() {

  }

  @Override
  public boolean onBackPressed() {
    getActivity().finish();
    return super.onBackPressed();
  }
}
