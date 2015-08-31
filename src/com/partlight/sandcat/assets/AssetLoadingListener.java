package com.partlight.sandcat.assets;

public interface AssetLoadingListener {
	public void onAssetLoaded(String assetPath, String assetTag, Assets asset);
}
