package com.partlight.sandcat.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.partlight.sandcat.FragmentShaders;
import com.partlight.sandcat.VertexShaders;
import com.partlight.sandcat.assets.SandCatAssets;

public class LinearGradient extends Image implements Disposable {

	private ShaderProgram	spShader;
	private FrameBuffer		fbFrameBuffer;

	public LinearGradient() {
		super();
		this.spShader = this.createShader();
		this.fbFrameBuffer = this.createFrameBuffer();
		this.setColor(Color.WHITE);
		this.frameBuffer();
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	protected FrameBuffer createFrameBuffer() {
		return new FrameBuffer(Format.RGBA8888, 1, Gdx.graphics.getHeight(), false);
	}

	protected ShaderProgram createShader() {
		return new ShaderProgram(VertexShaders.vertDefault, FragmentShaders.fragGradient);
	}

	@Override
	public void dispose() {
		this.fbFrameBuffer.dispose();
	}

	protected void frameBuffer() {

		final SpriteBatch batch = new SpriteBatch();

		this.fbFrameBuffer.bind();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setShader(this.spShader);
		batch.draw(SandCatAssets.tPixel, 0, 0, 1, this.fbFrameBuffer.getHeight());
		batch.end();
		FrameBuffer.unbind();

		this.setDrawable(new TextureRegionDrawable(new TextureRegion(this.fbFrameBuffer.getColorBufferTexture())));

		batch.dispose();
	}
}
