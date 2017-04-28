package com.ianarbuckle.fitnow.activities.walking.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.ianarbuckle.fitnow.utils.*;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class WalkGalleryFragment extends BaseFragment implements WalkGalleryView {

  GridLayoutManager gridLayoutManager;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.rlEmptyMessage)
  RelativeLayout rlEmptyMessage;

  WalkGalleryAdapter adapter;

  WalkGalleryPresenterImpl presenter;

  public static Fragment newInstance() {
    return new WalkGalleryFragment();
  }

  @Override
  protected void initPresenter() {
    presenter = new WalkGalleryPresenterImpl(this, FitNowApplication.getAppInstance().getDatabaseHelper());
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
        GalleryDialogFragment galleryDialogFragment = GalleryDialogFragment.newInstance();
        galleryDialogFragment.setArguments(bundle);
        galleryDialogFragment.show(fragmentTransaction, Constants.TAG_GALLERY_FULLSCREEN_FRAGMENT);
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
  public void setAdapter(WalkGalleryAdapter adapter) {
    recyclerView.setVisibility(View.VISIBLE);
    rlEmptyMessage.setVisibility(View.GONE);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment errorDialog = ErrorDialogFragment.newInstance(R.string.common_network_error);
    errorDialog.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.TAG_STOP_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public boolean onBackPressed() {
    getActivity().finish();
    return super.onBackPressed();
  }
}
