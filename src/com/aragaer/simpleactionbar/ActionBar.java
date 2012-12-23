package com.aragaer.simpleactionbar;

import java.security.InvalidParameterException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBar extends ViewGroup {
	private final ViewGroup home;
	private final ImageView icon;
	private final TextView title;
	private final View up;
	final ActionsMenu menu;
	private final MenuItem home_item;
	private LinearLayout actions;

	public ActionBar(Context context) {
		this(context, null);
	}

	public ActionBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if (!(context instanceof AbActivity))
			throw new InvalidParameterException("Must be AbActivity");
		menu = new ActionsMenu(context);
		home_item = menu.add(-1, R.id.home, 0, R.string.home);

		PackageManager pm = context.getPackageManager();

		home = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.home,
				null);
		up = home.findViewById(R.id.up);
		icon = (ImageView) home.findViewById(R.id.home);
		home.setOnClickListener(item_click);
		home.setClickable(true);
		home.setFocusable(true);
		home.setTag(home_item);

		try {
			Drawable icon_d = pm.getActivityIcon(((Activity) context)
					.getComponentName());
			icon.setImageDrawable(icon_d);
		} catch (NameNotFoundException e) {
			Log.e("AB", "Activity component name not found!", e);
		}

		title = new TextView(context);
		title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
				.getDimension(R.dimen.action_bar_title_text_size));
		title.setSingleLine();
		title.setEllipsize(TruncateAt.END);
		title.setGravity(Gravity.CENTER_VERTICAL);
		title.setTextColor(Color.WHITE);
		title.setBackgroundResource(R.drawable.item_background_holo_dark);

		actions = new LinearLayout(context);
		title.setGravity(Gravity.CENTER);
		addView(actions);

		addView(home);
		addView(title);

		setBackgroundResource(R.drawable.ab_transparent_dark_holo);
	}

	public void setDisplayShowHomeEnabled(boolean showHome) {
	}

	public void setDisplayHomeAsUpEnabled(boolean isUp) {
		up.setVisibility(isUp ? VISIBLE : GONE);
	}

	public void setTitle(int resId) {
		title.setText(resId);
	}

	public void setIcon(int resId) {
		icon.setImageResource(resId);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int contentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
		final int verticalPadding = getPaddingTop() + getPaddingBottom();
		final int paddingLeft = getPaddingLeft();
		final int paddingRight = getPaddingRight();

		final int height = maxHeight - verticalPadding;
		int availableWidth = contentWidth - paddingLeft - paddingRight;

		final int exactHeightSpec = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.EXACTLY);
		int homeWidthSpec = MeasureSpec.makeMeasureSpec(availableWidth,
				MeasureSpec.AT_MOST);

		home.measure(homeWidthSpec, exactHeightSpec);
		final int homeWidth = home.getMeasuredWidth();
		availableWidth = Math.max(0, availableWidth - homeWidth);

		title.measure(MeasureSpec.makeMeasureSpec(availableWidth,
				MeasureSpec.AT_MOST), exactHeightSpec);
		availableWidth = Math.max(0, availableWidth - title.getMeasuredWidth());

		actions.measure(MeasureSpec.makeMeasureSpec(availableWidth,
				MeasureSpec.AT_MOST), exactHeightSpec);
		availableWidth = Math.max(0, availableWidth - actions.getMeasuredWidth());

		setMeasuredDimension(contentWidth, maxHeight);
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int x = getPaddingLeft(), y = getPaddingTop();
		home.layout(x, y, x + home.getMeasuredWidth(),
				y + home.getMeasuredHeight());
		x += home.getMeasuredWidth();
		title.layout(x, y, x + title.getMeasuredWidth(),
				y + title.getMeasuredHeight());
		actions.layout(r - actions.getMeasuredWidth(), y, r, y + actions.getMeasuredHeight());
	}

	private final OnClickListener item_click = new OnClickListener() {
		public void onClick(View v) {
			MenuItem item = (MenuItem) v.getTag();
			((Activity) getContext()).onOptionsItemSelected(item);
		}
	};

	private static final int MAX_ICON_SIZE = 32; // dp

	void setActions(SparseArray<MenuItem> items) {
		final Context ctx = getContext();
		final int icon_size = Math.round(MAX_ICON_SIZE * ctx.getResources().getDisplayMetrics().density);
		for (int i = 0; i < items.size(); i++) {
			final MenuItem item = items.valueAt(i);
			if (item.getGroupId() < 0)
				continue;
			Log.d("AB", "Action "+i+" ["+item.getTitle()+"]");
			TextView view = new TextView(ctx);
			view.setTag(item);
			view.setBackgroundResource(R.drawable.item_background_holo_dark);
			item.getIcon().setBounds(0, 0, icon_size, icon_size);
			view.setCompoundDrawables(item.getIcon(), null, null, null);
			view.setOnClickListener(item_click);
			view.setClickable(true);
			view.setFocusable(true);
			actions.addView(view, icon_size, icon_size);
		}
	}
}
