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

    ViewHolder viewHolder = null;

    public ListAdArrayAdapter(Context context) {
        super(context, 0);

        imageTagFactory.setHeight(500);
        imageTagFactory.setWidth(500);
        imageTagFactory.setDefaultImageResId(R.drawable.no_image);
        imageTagFactory.setErrorImageId(R.drawable.ic_launcher);
        imageTagFactory.setAnimation(android.R.anim.slide_in_left);
        //imageTagFactory.setAnimation(android.R.anim.fade_in);
        imageTagFactory.setSaveThumbnail(true);
    }

    /**
     * Improve {@link android.widget.ListView} performance while scrolling<br/>
     * <a href="ttp://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder">See doc</a>
     */

    static class ViewHolder {
        ImageView iv;
        ImageTag it;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sample, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.it = imageTagFactory.build(getItem(position).image.replace("thumbs", "images"), getContext());
        viewHolder.iv.setTag(viewHolder.it);
        NewDawnApplication.getImageManager().getLoader().load(viewHolder.iv);

        return convertView;
    }


    @Override
    public Ad getItem(int position) {
        return super.getItem(position);
    }
}