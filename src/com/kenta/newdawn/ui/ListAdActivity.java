package com.kenta.newdawn.ui;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.SearchView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.kenta.newdawn.R;
import com.kenta.newdawn.model.json.ParcelableAd;
import com.kenta.newdawn.util.LogUtils;
import com.kenta.newdawn.util.UIUtils;

public class ListAdActivity extends BaseActivity implements
		ListAdFragment.OnListAdSelectedListener {

	private static final String TAG = LogUtils.makeLogTag(ListAdActivity.class);

	// --------------------------------------------------------------------------------------------
	// ACTIVITY LIFECYCLE
	// --------------------------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);

		if (isFinishing()) {
			return;
		}

		// However, if we're being restored from a previous state,
		// then we don't need to do anything and should return or else
		// we could end up with overlapping fragments.
		if (savedInstanceState != null) {
			return;
		}

		ListAdFragment listAdFragment = new ListAdFragment();

		// In case this activity was started with special instructions from an
		// Intent,
		// pass the Intent's extras to the fragment as arguments
		listAdFragment.setArguments(getIntent().getExtras());

		// Add the fragment to the 'fragment_container' FrameLayout
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listAdFragment).commit();

        getActionBar().setHomeButtonEnabled(false);
	}

	// --------------------------------------------------------------------------------------------
	// PRIVATE
	// --------------------------------------------------------------------------------------------

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
				searchView.setSearchableInfo(searchManager
						.getSearchableInfo(getComponentName()));
			}
		}
	}

	// --------------------------------------------------------------------------------------------
	// PUBLIC
	// --------------------------------------------------------------------------------------------
    /*
	public void onAdSelected(ParcelableAd _ad) {
		// The user selected the ad from ListAdFragment

		// Create fragment and give it an argument for the selected article
		DetailAdFragment detailAdFragment = new DetailAdFragment();
		Bundle args = new Bundle();
		args.putParcelable(DetailAdFragment.ARG_PARCELABLE_AD, _ad);
		detailAdFragment.setArguments(args);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack so the user can navigate
		// back
		transaction.replace(R.id.fragment_container, detailAdFragment);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}
	*/
    public void onAdSelected(ParcelableAd _ad) {
        Intent intent = DetailAdActivity.newInstance(this, _ad);
        startActivity(intent);
    }

} // class end
