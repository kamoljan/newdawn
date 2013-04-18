/**
 * A good example
 * https://github.com/huewu/gdg_opensource_code_lab/blob/master/src/com/example/gdg_opensource_codelab_sample_1
 * /VideoResourceAdapter.java
 */

package com.kenta.newdawn.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AbsListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.kenta.newdawn.R;
import com.kenta.newdawn.util.LogUtils;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.codehaus.jackson.JsonFactory;


import java.io.IOException;
import java.util.List;

public class AdsFragment extends SherlockListFragment {

    private static final String TAG = LogUtils.makeLogTag(AdsFragment.class);

    final static String ARG_QUERY = "query";
    String mQuery = null;

    private MultiColumnListView mListView = null;
    private BoxAdapter mBoxAdapter = null;

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private AdList mAdList;
    private int mOffset = 0;  // http://...?&o=15
    private int mFilteredAds = 0;

    private static final int API_ADS_LIMIT = 10;
    private static final String API_URL = "http://54.251.249.255:54321/api/v1/list.json";
    private static final String API_KEY = "107d769b4e9c97c96433d85761c37834be6afdce";

    protected boolean isLoading = false;
    boolean isOldFragment = false;


    // --------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // --------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(ARG_QUERY);
        }

        // Inflate the layout for this fragment
        View mRoot = inflater.inflate(R.layout.act_sample, null);

        mListView = (MultiColumnListView) mRoot.findViewById(R.id.list);

        return mRoot;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current search query in case we need to recreate the fragment
        outState.putString(ARG_QUERY, mQuery);
    }

    private void asyncListLoad(Boolean _isFresh) {
        isOldFragment = _isFresh;
        isLoading = true;
        try {
            LogUtils.LOGI(TAG, "Start: LoadAsyncTask()");
            new LoadAsyncTask().execute();
        } catch(Exception e) {
            LogUtils.LOGE(TAG, "e = " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mOffset = 0;

        Bundle args = this.getArguments();
        if (args != null & !isOldFragment) {
            // Set query based on argument passed in
            mQuery = args.getString(ARG_QUERY);
        }

        mBoxAdapter = new BoxAdapter(getActivity());

        mListView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getListView().getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnScrollListener(new PLA_AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
                LogUtils.LOGD(TAG, "mOffset = " + mOffset);
            }

            @Override
            public void onScroll(PLA_AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isLoading)
                    return;

                if (mBoxAdapter == null)
                    return;

                final int count = mBoxAdapter.getCount();
                if (count == 0)
                    return;

                if (firstVisibleItem + visibleItemCount >= count && mFilteredAds > count) {
                    mOffset += 10;  // increase offset of the page
                    asyncListLoad(false);
                }
            }
        });

        mListView.setAdapter(mBoxAdapter);

        asyncListLoad(true);
    }


    // --------------------------------------------------------------------------------------------
    // INNER CLASS
    // --------------------------------------------------------------------------------------------
    private class BoxItem {
        public String url;

        public BoxItem(String imageUrl) {
            this.url = imageUrl;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    // --------------------------------------------------------------------------------------------
    // INNER CLASS
    // --------------------------------------------------------------------------------------------
    public class BoxAdapter extends ArrayAdapter<BoxItem> {

        private ImageTagFactory imageTagFactory = ImageTagFactory.newInstance();


        public BoxAdapter(Context context) {
            super(context, 0);

            imageTagFactory.setHeight(500);
            imageTagFactory.setWidth(500);
            imageTagFactory.setDefaultImageResId(R.drawable.no_image);
            imageTagFactory.setErrorImageId(R.drawable.ic_action_help);
            //imageTagFactory.setAnimation(android.R.anim.slide_in_left);
            //imageTagFactory.setAnimation(android.R.anim.fade_in);
            imageTagFactory.setSaveThumbnail(true);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sample, null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.item_image);
            ImageTag tag = imageTagFactory.build(getItem(position).getUrl().replace("thumbs", "images"), getContext());
            //ImageTag tag = mTagFactory.build(res.getUrl().replace("images", "thumbs"), getContext());
            iv.setTag(tag);
            DawnApplication.getImageManager().getLoader().load(iv);

            return convertView;
        }

        @Override
        public BoxItem getItem(int position) {
            return super.getItem(position);
        }
    }

    /**
     * {
     *    ads: [
     *        {
     *           image: "http://54.251.249.255:20001/thumbs/04/0477901857.jpg",
     *           subject: "Asdfasdfasdfasdfasdfasdf adsfasdfasdf",
     *           category: "3040",
     *           company_ad: "0",
     *           list_id: "8000002",
     *           price: "",
     *           date: "Hari ini 02:56",
     *           name: "naomi"
     *       },
     *       ..
     *    ]
     * }
     */
    /** Represents ad feed. */
    public static class AdList {
        @Key
        public int filtered;

        @Key
        public List<Ad> ads;
    }

    /** Represents a ad. */
    public static class Ad {
        @Key
        public String image;
    }

    /** API_URL for BAPY. */
    public static class BapyUrl extends GenericUrl {
        public BapyUrl(String encodedUrl) {
            super(encodedUrl);
        }

        @Key
        public String key;

        @Key
        public int o;  // offset

        @Key
        public int limit;

        @Key
        public int img = 1;

        @Key
        public String q;  // query

        @Key
        public String f = "p";  // private
    }

    private void run() throws Exception {
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest httpRequest) throws IOException {
                        httpRequest.setParser(new JsonObjectParser(JSON_FACTORY));
                    }
                });
        BapyUrl url = new BapyUrl(API_URL);
        url.key = API_KEY;
        url.o = mOffset;
        url.limit = API_ADS_LIMIT;
        if (mQuery != null) {
            url.q = mQuery;
        }

        HttpRequest request = requestFactory.buildGetRequest(url);
        mAdList = request.execute().parseAs(AdList.class);
        if (mAdList.ads.isEmpty()) {
            LogUtils.LOGI(TAG, "No ListAds found.");
        } else {
            mFilteredAds = mAdList.filtered;
            LogUtils.LOGI(TAG, "mFilteredAds = " + mFilteredAds);
        }
    }

    private class LoadAsyncTask extends AsyncTask<Void, Integer, Long> {
        // Do the long-running work in here
        @Override
        protected Long doInBackground(Void... unused) {
            try {
                try {
                    run();
                } catch (HttpResponseException e) {
                    LogUtils.LOGE(TAG, "LaodAsyncTask e = " + e.getMessage());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        // This is called each time you call publishProgress()
        protected void onProgressUpdate(Integer... progress) {
            // do nothing
        }

        // This is called when doInBackground() is finished
        protected void onPostExecute(Long result) {
            Toast.makeText(getListView().getContext(), "onPostExecute babe!!" , Toast.LENGTH_SHORT).show();
            addMore();
            isLoading = false;
            mQuery = null;
        }
    }

    public void addMore() {
        if (mAdList.ads == null)
            return;

        if (isOldFragment) {
            mBoxAdapter.clear();
            mBoxAdapter.notifyDataSetChanged();
        }

        for (Ad ad : mAdList.ads) {
            BoxItem boxItem = new BoxItem(ad.image);
            mBoxAdapter.add(boxItem);
            mBoxAdapter.notifyDataSetChanged();
        }

        mAdList.ads.clear();  // clear our list
    }
}