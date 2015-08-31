package com.partlight.sandcat.tools.listener;

public interface ValueChangeListener<T, K> {
	public void onValueChange(T sender, K value);
}
