package com.aragaer.simpleactionbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public interface MenuItem extends android.view.MenuItem {
	public MenuItem setAlphabeticShortcut(char alphaChar);
	public MenuItem setCheckable(boolean checkable);
	public MenuItem setChecked(boolean checked);
	public MenuItem setEnabled(boolean enabled);
	public MenuItem setIcon(Drawable icon);
	public MenuItem setIcon(int iconRes);
	public MenuItem setIntent(Intent intent);
}
