package com.kenta.newdawn.ui;

import android.annotation.TargetApi;
import android.app.SearchManager;
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
import com.kenta.newdawn.NewDawnApplication;
import com.kenta.newdawn.R;
import com.kenta.newdawn.util.LogUtils;
import com.kenta.newdawn.util.UIUtils;

/**
 * DetailAdActivity the first activity for Ad Details
 */
public class DetailAdActivity extends BaseActivity implements
        ActionBar.TabListener,
        ViewPager.OnPageChangeListener {

    private static final String TAG = LogUtils.makeLogTag(DetailAdActivity.class);

    private ListAdFragment mListAdFragment;
    private ViewPager mViewPager;

    // --------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // --------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        int detail_result = NewDawnApplication.sListId;

        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setPageMarginDrawable(R.drawable.grey_border_inset_lr);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText(R.string.title_discover).setTabListener(this));

        getSupportActionBar().setHomeButtonEnabled(false);
    }

    // --------------------------------------------------------------------------------------------
    // PRIVATE
    // --------------------------------------------------------------------------------------------

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

    // --------------------------------------------------------------------------------------------
    // INNER CLASS
    // --------------------------------------------------------------------------------------------
    private class DetailPagerAdapter extends FragmentPagerAdapter {
        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mListAdFragment = new ListAdFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

