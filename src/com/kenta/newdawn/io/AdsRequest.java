package com.kenta.newdawn.io;

import android.net.Uri;

import com.kenta.newdawn.Config;
import com.kenta.newdawn.model.json.ListAds;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

public class AdsRequest extends SpringAndroidSpiceRequest<ListAds> {

    private String keyword;
    private int offset;
    private int limit = 0;

    public AdsRequest(String keyword, int offset) {
        super(ListAds.class);
        this.keyword = keyword;
        this.offset = offset;
    }

    @Override
    public ListAds loadDataFromNetwork() throws Exception {

        // With Uri.Builder class we can build our url is a safe manner
        Uri.Builder uriBuilder = Uri.parse(Config.GET_APY_LIST_URL).buildUpon();
        if (keyword != null) {
            uriBuilder.appendQueryParameter("q", keyword);
        }
        uriBuilder.appendQueryParameter("o", String.valueOf(offset));
        String url = uriBuilder.build().toString();

        return getRestTemplate().getForObject(url, ListAds.class);
    }

    /**
     * This method generates a unique cache key for this request. In this case our cache key depends just on the
     * keyword.
     *
     * @return
     */
    public String createCacheKey() {
        return "q." + keyword;
    }
}