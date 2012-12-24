package com.aragaer.simpleactionbar;

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

		menu = new ActionsMenu(context);
		home_item = menu.add(-1, R.id.home, 0, R.string.home);

		PackageManager pm = context.getPackageManager();

		home = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.home, null);
		up = home.findViewById(R.id.up);
		icon = (ImageView) home.findViewById(R.id.home);
		home.setOnClickListener(item_click);
		home.setClickable(true);
		home.setFocusable(true);
		home.setTag(home_item);

		try {
			Drawable icon_d = pm.getActivityIcon(((Activity) context).getComponentName());
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

	public void setDisplayShowHomeEnabled(boolean showHome) { }

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

		final int exactHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

		home.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), exactHeightSpec);
		availableWidth = Math.max(0, availableWidth - home.getMeasuredWidth());

		title.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), exactHeightSpec);
		availableWidth = Math.max(0, availableWidth - title.getMeasuredWidth());

		actions.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST), exactHeightSpec);
		availableWidth = Math.max(0, availableWidth - actions.getMeasuredWidth());

		setMeasuredDimension(contentWidth, maxHeight);
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int x = getPaddingLeft(), y = getPaddingTop();
		home.layout(x, y, x + home.getMeasuredWidth(), y + home.getMeasuredHeight());
		x += home.getMeasuredWidth();
		title.layout(x, y, x + title.getMeasuredWidth(), y + title.getMeasuredHeight());
		actions.layout(r - actions.getMeasuredWidth(), y, r, y + actions.getMeasuredHeight());
	}

	private final OnClickListener item_click = new OnClickListener() {
		public void onClick(View v) {
			MenuItem item = (MenuItem) v.getTag();
			if (((Activity) getContext()).onOptionsItemSelected(item))
				return;
			menu.performIdentifierAction(item.getItemId(), 0);
		}
	};

	void setActions(SparseArray<MenuItem> items) {
		final Context ctx = getContext();
		for (int i = 0; i < items.size(); i++) {
			final MenuItem item = items.valueAt(i);
			if (item.getGroupId() < 0)
				continue;
			ActionView view = new ActionView(ctx);
			view.setAction(item);
			view.setOnClickListener(item_click);
			actions.addView(view);
		}
	}

	private final class ActionView extends TextView {
		private static final int MAX_ICON_SIZE = 32; // dp
		private Drawable icon;
		final int icon_size;

		public ActionView(Context context) {
			super(context);
			setBackgroundResource(R.drawable.item_background_holo_dark);
			setClickable(true);
			setFocusable(true);
			icon_size = (int) (MAX_ICON_SIZE
					* context.getResources().getDisplayMetrics().density + 0.5f);
		}

		void setAction(MenuItem action) {
			setTag(action);
			icon = action.getIcon();
			icon.setBounds(0, 0, icon_size, icon_size);
			setCompoundDrawables(icon, null, null, null);
		}

		protected void onMeasure(int wms, int hms) {
			if (MeasureSpec.getMode(hms) == MeasureSpec.AT_MOST) {
				// Fill all available height.
				hms = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(hms),
						MeasureSpec.EXACTLY);
			}
			super.onMeasure(hms, hms); // we want it square
			final int w = getMeasuredWidth();
			final int dw = icon.getBounds().width();
			setPadding((w - dw) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
		}
	}
}
