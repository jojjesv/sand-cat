package com.partlight.sandcat.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BaseAssetLibrary implements AssetLoadingListener {
	public TextureRegion trPixel;

	public void createPixel() {
		if (this.trPixel != null)
			throw new IllegalStateException("Pixel texture region already created!");

		final Pixmap pixel = new Pixmap(1, 1, Format.RGB565);
		pixel.setColor(Color.WHITE);
		pixel.fill();
		this.trPixel = new TextureRegion(new Texture(pixel));

		pixel.dispose();
	}

	@Override
	public void onAssetLoaded(String fileName, String arrayTag) {
	}
}
