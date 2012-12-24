package com.aragaer.simpleactionbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class ActionsMenu implements Menu {
	private Context ctx;
	SparseArray<MenuItem> items = new SparseArray<MenuItem>();

	private class ActionItem implements MenuItem {
		private Drawable icon;
		private int id, gid;
		private CharSequence title;
		private Intent intent;

		public char getAlphabeticShortcut() {
			return 0;
		}

		public int getGroupId() {
			return gid;
		}

		public Drawable getIcon() {
			return icon;
		}

		public Intent getIntent() {
			return intent;
		}

		public int getItemId() {
			return id;
		}

		public ContextMenuInfo getMenuInfo() {
			return null;
		}

		public char getNumericShortcut() {
			return 0;
		}

		public int getOrder() {
			return 0;
		}

		public SubMenu getSubMenu() {
			return null;
		}

		public CharSequence getTitle() {
			return title;
		}

		public CharSequence getTitleCondensed() {
			return title;
		}

		public boolean hasSubMenu() {
			return false;
		}

		public boolean isCheckable() {
			return false;
		}

		public boolean isChecked() {
			return false;
		}

		public boolean isEnabled() {
			return true;
		}

		public boolean isVisible() {
			return true;
		}

		public MenuItem setAlphabeticShortcut(char alphaChar) {
			return this;
		}

		public MenuItem setCheckable(boolean checkable) {
			return this;
		}

		public MenuItem setChecked(boolean checked) {
			return this;
		}

		public MenuItem setEnabled(boolean enabled) {
			return this;
		}

		public MenuItem setIcon(Drawable icon) {
			this.icon = icon;
			return this;
		}
		public MenuItem setIcon(int iconRes) {
			this.icon = ctx.getResources().getDrawable(iconRes);
			return this;
		}

		public MenuItem setIntent(Intent intent) {
			this.intent = intent;
			return this;
		}

		public MenuItem setNumericShortcut(char numericChar) {
			return null;
		}

		public MenuItem setOnMenuItemClickListener(
				OnMenuItemClickListener menuItemClickListener) {
			return null;
		}

		public MenuItem setShortcut(char numericChar, char alphaChar) {
			return null;
		}

		public MenuItem setTitle(CharSequence title) {
			this.title = title;
			return null;
		}

		public MenuItem setTitle(int title) {
			this.title = ctx.getText(title);
			return null;
		}

		public MenuItem setTitleCondensed(CharSequence title) {
			return null;
		}

		public MenuItem setVisible(boolean visible) {
			return null;
		}

		private ActionItem(int id, int gid, CharSequence title) {
			this.id = id;
			this.gid = gid;
			setTitle(title);
		}

		private ActionItem(int id, int gid, int title) {
			this.id = id;
			this.gid = gid;
			setTitle(title);
		}

		boolean invoke(Context ctx) {
			if (intent != null) {
				ctx.startActivity(intent);
				return true;
			}

			return false;
		}
	}

	public MenuItem add(CharSequence title) {
		return add(0, 0, 0, title);
	}

	public MenuItem add(int titleRes) {
		return add(0, titleRes, 0, ctx.getText(titleRes));
	}

	public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
		ActionItem item = new ActionItem(itemId, groupId, title);
		items.put(itemId, item);
		return item;
	}

	public MenuItem add(int groupId, int itemId, int order, int titleRes) {
		return add(groupId, itemId, order, ctx.getText(titleRes));
	}

	public int addIntentOptions(int groupId, int itemId, int order,
			ComponentName caller, Intent[] specifics, Intent intent, int flags,
			MenuItem[] outSpecificItems) {
		return 0;
	}

	public SubMenu addSubMenu(CharSequence title) {
		return null;
	}

	public SubMenu addSubMenu(int titleRes) {
		return null;
	}

	public SubMenu addSubMenu(int groupId, int itemId, int order,
			CharSequence title) {
		return null;
	}

	public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
		return null;
	}

	public void clear() {
		items.clear();
	}

	public void close() { }

	public MenuItem findItem(int id) {
		return items.get(id);
	}

	public MenuItem getItem(int index) {
		return items.valueAt(index);
	}

	public boolean hasVisibleItems() {
		return items.size() > 0;
	}

	public boolean isShortcutKey(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean performIdentifierAction(int id, int flags) {
		ActionItem item = (ActionItem) findItem(id);
		return item != null && item.invoke(ctx);
	}

	public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
		return false;
	}

	public void removeGroup(int groupId) { }

	public void removeItem(int id) { }

	public void setGroupCheckable(int group, boolean checkable,
			boolean exclusive) { }

	public void setGroupEnabled(int group, boolean enabled) { }

	public void setGroupVisible(int group, boolean visible) { }

	public void setQwertyMode(boolean isQwerty) { }

	public int size() {
		return items.size();
	}

	public ActionsMenu(Context context) {
		ctx = context;
	}
}
