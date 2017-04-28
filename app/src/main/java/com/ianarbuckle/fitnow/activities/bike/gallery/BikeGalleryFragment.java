package com.ianarbuckle.fitnow.activities.bike.gallery;

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
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;
import com.ianarbuckle.fitnow.utils.GalleryDialogFragment;
import com.ianarbuckle.fitnow.utils.RecyclerItemClickListener;
import com.ianarbuckle.fitnow.activities.walking.gallery.WalkGalleryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeGalleryFragment extends BaseFragment implements BikeGalleryView {

  GridLayoutManager gridLayoutManager;

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.rlEmptyMessage)
  RelativeLayout rlEmptyMessage;

  WalkGalleryAdapter adapter;

  BikeGalleryPresenterImpl presenter;

  public static Fragment newInstance() {
    return new BikeGalleryFragment();
  }

  @Override
  protected void initPresenter() {
    DatabaseHelper databaseHelper = FitNowApplication.getAppInstance().getDatabaseHelper();
    presenter = new BikeGalleryPresenterImpl(this, databaseHelper);
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
  public void setAdapter(BikeGalleryAdapter adapter) {
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
}
