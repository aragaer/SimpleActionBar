package com.aragaer.simpleactionbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public interface MenuItem extends android.view.MenuItem {
	public static final int SHOW_AS_ACTION_NEVER = 0;
	public static final int SHOW_AS_ACTION_IF_ROOM = 1;
	public static final int SHOW_AS_ACTION_ALWAYS = 2;
	public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
	public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;

	public void setShowAsAction(int actionEnum);

	public MenuItem setAlphabeticShortcut(char alphaChar);
	public MenuItem setCheckable(boolean checkable);
	public MenuItem setChecked(boolean checked);
	public MenuItem setEnabled(boolean enabled);
	public MenuItem setIcon(Drawable icon);
	public MenuItem setIcon(int iconRes);
	public MenuItem setIntent(Intent intent);
}
