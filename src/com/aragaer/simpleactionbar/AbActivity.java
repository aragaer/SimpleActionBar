package com.aragaer.simpleactionbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class AbActivity extends Activity {
	private ActionBar ab;
	private LinearLayout ll;
	public ActionBar getActionBar() {
		return ab;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("AB", "ActionBar activity onCreate");
		ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ab = new ActionBar(this);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				getResources().getDimensionPixelSize(R.dimen.action_bar_default_height));
		ll.addView(ab, lp);
		if (onCreateActionBarMenu(ab.menu))
			ab.setActions(ab.menu.items);
	}

	public void setContentView(int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null));
	}

	public void setContentView(View view) {
		ll.addView(view);
		super.setContentView(ll);
	}

	public void setContentView(View view, ViewGroup.LayoutParams params) {
		ll.addView(view, params);
		super.setContentView(ll);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public boolean onCreateActionBarMenu(Menu menu) {
		return false;
	}
}
