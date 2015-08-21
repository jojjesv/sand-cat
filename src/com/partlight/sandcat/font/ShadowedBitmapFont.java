package com.partlight.sandcat.font;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class ShadowedBitmapFont extends BitmapFont {

	private float	offsetX	= 1;
	private float	offsetY	= 1;

	public ShadowedBitmapFont() {
		super();
	}

	public ShadowedBitmapFont(BitmapFontData data, Array<TextureRegion> pageRegions, boolean integer) {
		super(data, pageRegions, integer);
	}

	public ShadowedBitmapFont(BitmapFontData data, TextureRegion region, boolean integer) {
		super(data, region, integer);
	}

	public ShadowedBitmapFont(boolean flip) {
		super(flip);
	}

	public ShadowedBitmapFont(FileHandle fontFile) {
		super(fontFile);
	}

	public ShadowedBitmapFont(FileHandle fontFile, boolean flip) {
		super(fontFile, flip);
	}

	public ShadowedBitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip) {
		super(fontFile, imageFile, flip);
	}

	public ShadowedBitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip, boolean integer) {
		super(fontFile, imageFile, flip, integer);
	}

	public ShadowedBitmapFont(FileHandle fontFile, TextureRegion region) {
		super(fontFile, region);
	}

	public ShadowedBitmapFont(FileHandle fontFile, TextureRegion region, boolean flip) {
		super(fontFile, region, flip);
	}

	@Override
	public GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
		final float r = this.getColor().r, g = this.getColor().r, b = this.getColor().b, a = this.getColor().a;
		this.setColor(0, 0, 0, a);
		final GlyphLayout glyphLayout = super.draw(batch, str, x + this.offsetX, y + this.offsetY);
		this.setColor(r, g, b, a);
		super.draw(batch, str, x, y);
		return glyphLayout;
	}

	@Override
	public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
		final float r = this.getColor().r, g = this.getColor().r, b = this.getColor().b, a = this.getColor().a;
		this.setColor(0, 0, 0, a);
		final GlyphLayout glyphLayout = super.draw(batch, str, x + this.offsetX, y + this.offsetY, targetWidth, halign, wrap);
		this.setColor(r, g, b, a);
		super.draw(batch, str, x, y, targetWidth, halign, wrap);
		return glyphLayout;
	}

	@Override
	public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign,
			boolean wrap) {
		final float r = this.getColor().r, g = this.getColor().r, b = this.getColor().b, a = this.getColor().a;
		this.setColor(0, 0, 0, a);
		final GlyphLayout glyphLayout = super.draw(batch, str, x + this.offsetX, y + this.offsetY, start, end, targetWidth, halign, wrap);
		this.setColor(r, g, b, a);
		super.draw(batch, str, x, y, start, end, targetWidth, halign, wrap);
		return glyphLayout;
	}

	@Override
	public void draw(Batch batch, GlyphLayout layout, float x, float y) {
		final float r = this.getColor().r, g = this.getColor().r, b = this.getColor().b, a = this.getColor().a;
		this.setColor(0, 0, 0, a);
		super.draw(batch, layout, x + this.offsetX, y + this.offsetY);
		this.setColor(r, g, b, a);
		super.draw(batch, layout, x, y);
	}

	public float getOffsetX() {
		return this.offsetX;
	}

	public float getOffsetY() {
		return this.offsetY;
	}

	public void setOffset(float offsetX, float offsetY) {
		this.setOffsetX(offsetX);
		this.setOffsetY(offsetY);
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
}
