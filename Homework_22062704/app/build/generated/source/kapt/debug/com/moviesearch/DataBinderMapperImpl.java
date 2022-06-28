package com.moviesearch;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.moviesearch.databinding.ActivityMainBindingImpl;
import com.moviesearch.databinding.FavItemBindingImpl;
import com.moviesearch.databinding.FragmentDetailBindingImpl;
import com.moviesearch.databinding.FragmentFavouritesBindingImpl;
import com.moviesearch.databinding.FragmentFavouritesBindingLandImpl;
import com.moviesearch.databinding.FragmentListMovieBindingImpl;
import com.moviesearch.databinding.FragmentListMovieBindingLandImpl;
import com.moviesearch.databinding.FragmentStartBindingImpl;
import com.moviesearch.databinding.NewItemBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMAIN = 1;

  private static final int LAYOUT_FAVITEM = 2;

  private static final int LAYOUT_FRAGMENTDETAIL = 3;

  private static final int LAYOUT_FRAGMENTFAVOURITES = 4;

  private static final int LAYOUT_FRAGMENTLISTMOVIE = 5;

  private static final int LAYOUT_FRAGMENTSTART = 6;

  private static final int LAYOUT_NEWITEM = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.fav_item, LAYOUT_FAVITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.fragment_detail_, LAYOUT_FRAGMENTDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.fragment_favourites, LAYOUT_FRAGMENTFAVOURITES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.fragment_list_movie, LAYOUT_FRAGMENTLISTMOVIE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.fragment_start, LAYOUT_FRAGMENTSTART);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.moviesearch.R.layout.new_item, LAYOUT_NEWITEM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_FAVITEM: {
          if ("layout/fav_item_0".equals(tag)) {
            return new FavItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fav_item is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTDETAIL: {
          if ("layout/fragment_detail__0".equals(tag)) {
            return new FragmentDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_detail_ is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTFAVOURITES: {
          if ("layout-land/fragment_favourites_0".equals(tag)) {
            return new FragmentFavouritesBindingLandImpl(component, view);
          }
          if ("layout/fragment_favourites_0".equals(tag)) {
            return new FragmentFavouritesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_favourites is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTLISTMOVIE: {
          if ("layout/fragment_list_movie_0".equals(tag)) {
            return new FragmentListMovieBindingImpl(component, view);
          }
          if ("layout-land/fragment_list_movie_0".equals(tag)) {
            return new FragmentListMovieBindingLandImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_list_movie is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSTART: {
          if ("layout/fragment_start_0".equals(tag)) {
            return new FragmentStartBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_start is invalid. Received: " + tag);
        }
        case  LAYOUT_NEWITEM: {
          if ("layout/new_item_0".equals(tag)) {
            return new NewItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for new_item is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(9);

    static {
      sKeys.put("layout/activity_main_0", com.moviesearch.R.layout.activity_main);
      sKeys.put("layout/fav_item_0", com.moviesearch.R.layout.fav_item);
      sKeys.put("layout/fragment_detail__0", com.moviesearch.R.layout.fragment_detail_);
      sKeys.put("layout-land/fragment_favourites_0", com.moviesearch.R.layout.fragment_favourites);
      sKeys.put("layout/fragment_favourites_0", com.moviesearch.R.layout.fragment_favourites);
      sKeys.put("layout/fragment_list_movie_0", com.moviesearch.R.layout.fragment_list_movie);
      sKeys.put("layout-land/fragment_list_movie_0", com.moviesearch.R.layout.fragment_list_movie);
      sKeys.put("layout/fragment_start_0", com.moviesearch.R.layout.fragment_start);
      sKeys.put("layout/new_item_0", com.moviesearch.R.layout.new_item);
    }
  }
}
