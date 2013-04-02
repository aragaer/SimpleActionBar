package com.aragaer.simpleactionbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint("Override")
public class Activity extends android.app.Activity {
	private ActionBar ab;
	private LinearLayout ll;
	public ActionBar getActionBar() {
		return ab;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ab = new ActionBar(this);

		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				getResources().getDimensionPixelSize(R.dimen.action_bar_default_height));
		ll.addView(ab, lp);
		if (onCreateActionBarMenu(ab.menu))
			ab.setActions(ab.menu.items);
	}

	public void setContentView(int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null));
	}

	public void setContentView(View view) {
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
		setContentView(view, lp);
	}

	public void setContentView(View view, ViewGroup.LayoutParams params) {
		ll.addView(view, params);
		super.setContentView(ll);
	}

	public boolean onCreateActionBarMenu(Menu menu) {
		return false;
	}
}
