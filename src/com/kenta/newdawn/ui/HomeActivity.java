package com.kenta.newdawn.ui;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.kenta.newdawn.R;
import com.kenta.newdawn.util.LogUtils;
import com.kenta.newdawn.util.UIUtils;

/**
 * HomeActivity the first activity
 */
public class HomeActivity extends BaseActivity implements
        ActionBar.TabListener,
        ViewPager.OnPageChangeListener {

    private static final String TAG = LogUtils.makeLogTag(HomeActivity.class);

    private AdsFragment mAdsFragment;
    private ViewPager mViewPager;
    private Menu mOptionsMenu;
    private String mQuery = null;

    // --------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // --------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        setContentView(R.layout.activity_home);
        FragmentManager fm = getSupportFragmentManager();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        if (mViewPager != null) {
            // Phone setup
            mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
            mViewPager.setOnPageChangeListener(this);
            mViewPager.setPageMarginDrawable(R.drawable.grey_border_inset_lr);
            mViewPager.setPageMargin(getResources()
                    .getDimensionPixelSize(R.dimen.page_margin_width));

            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.title_discover)
                    .setTabListener(this));
            actionBar.addTab(actionBar.newTab()
                    .setText(R.string.title_sell)
                    .setTabListener(this));
        } else {
            mAdsFragment = (AdsFragment) fm.findFragmentById(R.id.fragment_discover);
        }
        getSupportActionBar().setHomeButtonEnabled(false);

        handleIntent(getIntent());
    }

    // --------------------------------------------------------------------------------------------
    // PRIVATE
    // --------------------------------------------------------------------------------------------

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);

            handleDiscover2Fragment();

            mViewPager.setCurrentItem(0);
            // Do work using string
        }
    }

    private AdsFragment handleDiscover2Fragment() {
        if (mAdsFragment != null) {
            mAdsFragment.isOldFragment = true;
            mAdsFragment.mQuery = mQuery;
            return mAdsFragment;
        } else {
            // Create fragment and give it an argument for the search query
            mAdsFragment  = new AdsFragment();
            Bundle args = new Bundle();
            args.putString(AdsFragment.ARG_QUERY, mQuery);
            mAdsFragment.setArguments(args);
            return mAdsFragment;
        }
    }

    // --------------------------------------------------------------------------------------------
    // OVERRIDES
    // --------------------------------------------------------------------------------------------

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int position) {
        getSupportActionBar().setSelectedNavigationItem(position);

        int titleId = -1;
        switch (position) {
            case 0:
                titleId = R.string.title_discover;
                break;
            case 1:
                titleId = R.string.title_sell;
                break;
        }
        String title = getString(titleId);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Since the pager fragments don't have known tags or IDs, the only way to persist the
        // reference is to use putFragment/getFragment. Remember, we're not persisting the exact
        // Fragment instance. This mechanism simply gives us a way to persist access to the
        // 'current' fragment instance for the given fragment (which changes across orientation
        // changes).
        //
        // The outcome of all this is that the "Refresh" menu button refreshes the stream across
        // orientation changes.
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        mOptionsMenu = menu;
        getSupportMenuInflater().inflate(R.menu.home, menu);

        setupSearchMenuItem(menu);

        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupSearchMenuItem(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        if (searchItem != null && UIUtils.hasHoneycomb()) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onSearchRequested();

        switch (item.getItemId()) {
            case R.id.menu_refresh:
                handleDiscover2Fragment();
                return true;

            case R.id.menu_search:
                if (!UIUtils.hasHoneycomb()) {
                    startSearch(null, false, Bundle.EMPTY, false);
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // --------------------------------------------------------------------------------------------
    // INNER CLASS
    // --------------------------------------------------------------------------------------------

    private class HomePagerAdapter extends FragmentPagerAdapter {
        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return handleDiscover2Fragment();

                case 1:
                    return (mAdsFragment = new AdsFragment());
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

