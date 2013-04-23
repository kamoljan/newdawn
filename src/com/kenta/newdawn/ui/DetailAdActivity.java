package com.kenta.newdawn.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.kenta.newdawn.model.json.ParcelableAd;
import com.kenta.newdawn.util.UIUtils;

/**
 * DetailAdActivity the first activity for Ad Details
 */
public class DetailAdActivity extends BaseActivity implements
        ActionBar.TabListener,
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private ParcelableAd mParcelableAd;

    // --------------------------------------------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // --------------------------------------------------------------------------------------------
    public static Intent newInstance(Activity activity, ParcelableAd _ad) {
        Intent intent = new Intent(activity, DetailAdActivity.class);
        intent.putExtra("parcelable_ad", _ad);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        mParcelableAd = null;
        if (getIntent().getExtras() != null) {
            mParcelableAd = getIntent().getExtras().getParcelable("parcelable_ad");
        }

        setTitle(mParcelableAd.getSubject());

        setContentView(R.layout.activity_home);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new DetailPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setPageMarginDrawable(R.drawable.grey_border_inset_lr);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText(mParcelableAd.getName()).setTabListener(this));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.menu_detail_ad, menu);

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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.call:
                call(mParcelableAd.getPhone(), this);
                return true;
            case R.id.sms:
                message(mParcelableAd.getPhone(), mParcelableAd.getSubject(), this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                    return getDetailAdFragment(mParcelableAd);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    // --------------------------------------------------------------------------------------------
    // PRIVATE METHODS
    // --------------------------------------------------------------------------------------------
    /**
     *  It opens DetailAdFragment
     */
    private DetailAdFragment getDetailAdFragment(ParcelableAd _ad) {
        DetailAdFragment detailAdFragment = new DetailAdFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailAdFragment.ARG_PARCELABLE_AD, _ad);
        detailAdFragment.setArguments(args);

        return detailAdFragment;
    }

    /**
     * Phone call.
     */
    private static void call(String phone, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * Whatsup, SMS, Skype...
     */
    private static void message(String phone, String message, Context context) {
        String uri = "smsto:" + phone;
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
        intent.putExtra("sms_body", message);
        context.startActivity(intent);
    }

}  // class end

