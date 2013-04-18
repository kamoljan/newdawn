package com.kenta.newdawn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.kenta.newdawn.NewDawnApplication;
import com.kenta.newdawn.R;
import com.kenta.newdawn.model.json.Ad;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;

public class ListAdArrayAdapter extends ArrayAdapter<Ad> {

    private ImageTagFactory imageTagFactory = ImageTagFactory.newInstance();


    public ListAdArrayAdapter(Context context) {
        super(context, 0);

        imageTagFactory.setHeight(500);
        imageTagFactory.setWidth(500);
        imageTagFactory.setDefaultImageResId(R.drawable.no_image);
        imageTagFactory.setErrorImageId(R.drawable.ic_launcher);
        //imageTagFactory.setAnimation(android.R.anim.slide_in_left);
        //imageTagFactory.setAnimation(android.R.anim.fade_in);
        imageTagFactory.setSaveThumbnail(true);
    }

    // TODO: where is ViewHolder?
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sample, null);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.item_image);
        ImageTag tag = imageTagFactory.build(getItem(position).image.replace("thumbs", "images"), getContext());
        //ImageTag tag = mTagFactory.build(res.getUrl().replace("images", "thumbs"), getContext());
        iv.setTag(tag);
        NewDawnApplication.getImageManager().getLoader().load(iv);

        return convertView;
    }

    @Override
    public Ad getItem(int position) {
        return super.getItem(position);
    }
}