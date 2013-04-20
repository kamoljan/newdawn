package com.kenta.newdawn.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.kenta.newdawn.R;

public class DetailAdFragment extends SherlockFragment {
    final static String ARG_LIST_ID = "list_id";
    String mCurrentListId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
        	mCurrentListId = savedInstanceState.getString(ARG_LIST_ID);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ad_view, container, false);
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
            updateAdView(args.getString(ARG_LIST_ID));
        } else if (mCurrentListId != null) {
            // Set article based on saved instance state defined during onCreateView
            updateAdView(mCurrentListId);
        }
    }

    public void updateAdView(String _list_id) {
        TextView article = (TextView) getActivity().findViewById(R.id.article);
        article.setText(_list_id);
        mCurrentListId = _list_id;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current ad selection in case we need to recreate the fragment
        outState.putString(ARG_LIST_ID, mCurrentListId);
    }
}
