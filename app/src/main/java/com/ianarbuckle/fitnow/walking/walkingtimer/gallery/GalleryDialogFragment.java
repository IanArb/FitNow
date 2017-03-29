package com.ianarbuckle.fitnow.walking.walkingtimer.gallery;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 25/03/2017.
 *
 */

public class GalleryDialogFragment extends DialogFragment {

  @BindView(R.id.viewPager)
  ViewPager viewPager;

  ViewPagerAdapter viewPagerAdapter;
  private List<GalleryModel> galleryModelArrayList;
  int selectedPosition = 0;

  public GalleryDialogFragment() {
  }

  public static GalleryDialogFragment newInstance() {
    return new GalleryDialogFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_pager_gallery, container, false);
    ButterKnife.bind(this, view);
    galleryModelArrayList = (List<GalleryModel>) getArguments().getSerializable(Constants.IMAGES_KEY);
    selectedPosition = getArguments().getInt(Constants.POSITION_KEY);

    viewPagerAdapter = new ViewPagerAdapter();
    viewPager.setAdapter(viewPagerAdapter);
    viewPager.addOnPageChangeListener(getListener());
    viewPager.setCurrentItem(selectedPosition);
    return view;
  }

  @NonNull
  private ViewPager.OnPageChangeListener getListener() {
    return new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    };
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
  }

  private class ViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;

    public ViewPagerAdapter() {
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
      layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = layoutInflater.inflate(R.layout.layout_fullscreen_gallery, container, false);

      GalleryModel galleryModel = galleryModelArrayList.get(position);

      ImageView imageView = (ImageView) view.findViewById(R.id.imgDisplay);

      ImageButton imageButton = (ImageButton) view.findViewById(R.id.ibClose);

      TextView textView = (TextView) view.findViewById(R.id.tvPosition);

      imageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          getDialog().dismiss();
        }
      });

      String imageUrl = galleryModel.getImageUrl();

      UiUtils.loadExpandedImage(imageUrl, imageView);

      textView.setText(position + 1 + " out of " + galleryModelArrayList.size());

      container.addView(view);

      return view;
    }

    @Override
    public int getCount() {
      return galleryModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }
  }
}
