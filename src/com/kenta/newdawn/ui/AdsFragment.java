/**
 * A good example
 * https://github.com/huewu/gdg_opensource_code_lab/blob/master/src/com/example/gdg_opensource_codelab_sample_1
 * /VideoResourceAdapter.java
 */

package com.kenta.newdawn.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AbsListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.kenta.newdawn.R;
import com.kenta.newdawn.adapter.ListAdArrayAdapter;
import com.kenta.newdawn.io.AdsRequest;
import com.kenta.newdawn.model.json.Ad;
import com.kenta.newdawn.model.json.ListAds;
import com.kenta.newdawn.service.AdsService;
import com.kenta.newdawn.util.LogUtils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;


public class AdsFragment extends SherlockListFragment {

    private static final String TAG = LogUtils.makeLogTag(AdsFragment.class);

    private ListAdArrayAdapter listAdArrayAdapter;
    private SpiceManager spiceManagerJson = new SpiceManager(AdsService.class);

    private MultiColumnListView mListView = null;

    final static String ARG_QUERY = "query";
    protected String mQuery = null;
    private int mOffset;
    private int mFilteredAds;

    private ListAds mListAds = null;

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
    public void onStart() {
        super.onStart();
        spiceManagerJson.start(getActivity());
        spiceManagerJson.execute(new AdsRequest(mQuery, mOffset), "q", DurationInMillis.ONE_SECOND * 10, new ListAdsListener());
    }

    @Override
    public void onStop() {
        spiceManagerJson.shouldStop();
        super.onStop();
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
        spiceManagerJson.execute(new AdsRequest(mQuery, mOffset), "q", DurationInMillis.ONE_SECOND * 10, new ListAdsListener());
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

        listAdArrayAdapter = new ListAdArrayAdapter(getActivity());

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

                if (listAdArrayAdapter == null)
                    return;

                final int count = listAdArrayAdapter.getCount();
                if (count == 0)
                    return;

                if (firstVisibleItem + visibleItemCount >= count && mFilteredAds > count) {
                    mOffset += 10;  // increase offset of the page
                    asyncListLoad(false);
                }
            }
        });

        mListView.setAdapter(listAdArrayAdapter);

        asyncListLoad(true);
    }


    // --------------------------------------------------------------------------------------------
    // INNER CLASS
    // --------------------------------------------------------------------------------------------
    private class ListAdsListener implements RequestListener<ListAds> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getListView().getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(ListAds result) {
            Toast.makeText(getListView().getContext(), "onPostExecute babe!!", Toast.LENGTH_SHORT).show();
            addMore();
            isLoading = false;
            mQuery = null;
        }
    }

    // --------------------------------------------------------------------------------------------
    // PRIVATE
    // --------------------------------------------------------------------------------------------

    private void addMore() {
        if (mListAds.getResults() == null)
            return;

        if (isOldFragment) {
            listAdArrayAdapter.clear();
            listAdArrayAdapter.notifyDataSetChanged();
        }

        for (Ad ad : mListAds.getResults()) {
            listAdArrayAdapter.add(ad);
            listAdArrayAdapter.notifyDataSetChanged();
            mFilteredAds = ad.getFiltered();
        }

        //mListAds.getResults().clear();  // clear our list
    }
}