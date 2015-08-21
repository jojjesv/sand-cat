package com.partlight.sandcat.assets;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

public class Assets implements Disposable, AssetErrorListener {

	private final OrderedMap<String, String>	aAssetsLoading;
	private final Array<AssetLoadingListener>	aLoadingListeners;
	private final AssetManager					amAssetManager;
	private Iterator<Entry<String, String>>		iAssetsLoadingIterator;
	private ObjectMap<String, Asset>			omAssets;

	public Assets() {
		this.amAssetManager = new AssetManager();
		this.aLoadingListeners = new Array<AssetLoadingListener>();
		this.aAssetsLoading = new OrderedMap<String, String>();
	}

	public void addAssetLoadingListener(AssetLoadingListener listener) {
		this.aLoadingListeners.add(listener);
	}

	public void clearAssetLoadingListeners() {
		this.aLoadingListeners.clear();
	}

	@Override
	public void dispose() {
		this.amAssetManager.dispose();
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
	}

	public AssetManager getAssetManager() {
		return this.amAssetManager;
	}

	public ObjectMap<String, Asset> getAssetMap() {
		return this.omAssets;
	}

	public String getAssetPath(String assetTag) {
		return this.omAssets.get(assetTag).path;
	}

	public void load(String tag) {
		final Asset ASSET = this.omAssets.get(tag);
		if (ASSET == null)
			return;

		this.aAssetsLoading.put(ASSET.path, tag);
		this.amAssetManager.load(ASSET.path, ASSET.type);
	}

	public void loadAssetJson(String jsonPath) {
		this.omAssets = new ObjectMap<String, Asset>();

		final Json json = new Json();
		final JsonReader reader = new JsonReader();
		final JsonIterator arrayIterator = reader.parse(Gdx.files.internal(jsonPath)).iterator();

		JsonValue arrayValue;
		while (arrayIterator.hasNext()) {
			arrayValue = arrayIterator.next();
			this.omAssets.put(arrayValue.name, json.fromJson(Asset.class, arrayValue.get(0).toString()));
		}
	}

	public boolean removeAssetLoadingListener(AssetLoadingListener listener) {
		return this.aLoadingListeners.removeValue(listener, true);
	}

	public void unload(String tag) {
		final Asset ASSET = this.omAssets.get(tag);
		if (ASSET == null)
			return;

		if (this.amAssetManager.isLoaded(ASSET.path)) {
			this.amAssetManager.unload(ASSET.path);
			this.aAssetsLoading.remove(ASSET.path);
		}
	}

	public boolean update() {
		final boolean update = this.amAssetManager.update();

		if (this.aAssetsLoading.size > 0) {
			this.iAssetsLoadingIterator = this.aAssetsLoading.iterator();

			Entry<String, String> next;
			while (this.iAssetsLoadingIterator.hasNext()) {
				next = this.iAssetsLoadingIterator.next();

				if (this.amAssetManager.isLoaded(next.key)) {
					for (final AssetLoadingListener listener : this.aLoadingListeners)
						listener.onAssetLoaded(next.key, next.value);
					this.aAssetsLoading.remove(next.key);
					this.iAssetsLoadingIterator.remove();
					break;
				}
			}
		}

		return update;
	}
}
