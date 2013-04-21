package com.kenta.newdawn.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.kenta.newdawn.NewDawnApplication;
import com.kenta.newdawn.R;
import com.kenta.newdawn.model.json.HolderAd;
import com.kenta.newdawn.model.json.ParcelableAd;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;

public class DetailAdFragment extends SherlockFragment {
    final static String ARG_PARCELABLE_AD = "ad";
    ParcelableAd mCurrentAd = null;
    
    private ImageTagFactory imageTagFactory = ImageTagFactory.newInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
        	mCurrentAd = savedInstanceState.getParcelable(ARG_PARCELABLE_AD);
        }

        // ImageLoader
        imageTagFactory.setHeight(100);
        imageTagFactory.setWidth(100);
        imageTagFactory.setDefaultImageResId(R.drawable.no_image);
        imageTagFactory.setErrorImageId(R.drawable.ic_launcher);
        //imageTagFactory.setAnimation(android.R.anim.slide_in_left);
        //imageTagFactory.setAnimation(android.R.anim.fade_in);
        imageTagFactory.setSaveThumbnail(true);
        
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_ad, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateAdView((ParcelableAd) args.getParcelable(ARG_PARCELABLE_AD));
        } else if (mCurrentAd != null) {
            // Set article based on saved instance state defined during onCreateView
            updateAdView(mCurrentAd);
        }
    }

    public void updateAdView(ParcelableAd _ad) {
    	TextView tv_list_id = (TextView) getActivity().findViewById(R.id.tv_list_id);
        tv_list_id.setText(_ad.getListId());
        
        TextView tv_subject = (TextView) getActivity().findViewById(R.id.tv_subject);
        tv_subject.setText(_ad.getSubject());
        
        TextView tv_body = (TextView) getActivity().findViewById(R.id.tv_body);
        tv_body.setText(_ad.getBody());
        
        TextView tv_price = (TextView) getActivity().findViewById(R.id.tv_price);
        tv_price.setText(_ad.getPrice());
        
        TextView tv_date = (TextView) getActivity().findViewById(R.id.tv_date);
        tv_date.setText(_ad.getDate());
        
        TextView tv_name = (TextView) getActivity().findViewById(R.id.tv_name);
        tv_name.setText(_ad.getName());
        
        TextView tv_phone = (TextView) getActivity().findViewById(R.id.tv_phone);
        tv_phone.setText(_ad.getPhone());
        
        ImageView iv_image = (ImageView) getActivity().findViewById(R.id.iv_image);
        ImageTag it_image = imageTagFactory.build(_ad.getImage().replace("thumbs", "images"), getActivity().getApplicationContext());
        iv_image.setTag(it_image);
        NewDawnApplication.getImageManager().getLoader().load(iv_image);
        
        mCurrentAd = _ad;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current ad selection in case we need to recreate the fragment
        outState.putParcelable(ARG_PARCELABLE_AD, mCurrentAd);
    }
}
