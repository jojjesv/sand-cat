package com.partlight.sandcat.graphics;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.partlight.sandcat.FragmentShaders;
import com.partlight.sandcat.SandCatGame;
import com.partlight.sandcat.VertexShaders;

public class LinearGradient extends Image implements Disposable {

	private ShaderProgram	spShader;
	private FrameBuffer		fbFrameBuffer;

	public LinearGradient() {
		super();
		this.spShader = this.createShader();
		this.fbFrameBuffer = this.createFrameBuffer();
		this.setColor(Color.WHITE);
		this.frameBuffer();
		this.setSize(graphics.getWidth(), graphics.getHeight());
	}

	protected ShaderProgram createShader() {
		return new ShaderProgram(VertexShaders.vertDefault, FragmentShaders.fragGradient);
	}

	protected void frameBuffer() {

		final SpriteBatch batch = new SpriteBatch();

		this.fbFrameBuffer.bind();
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setShader(this.spShader);
		batch.draw(((SandCatGame) Gdx.app.getApplicationListener()).getAssetLibrary().trPixel, 0, 0, 1, this.fbFrameBuffer.getHeight());
		batch.end();
		FrameBuffer.unbind();

		this.setDrawable(new TextureRegionDrawable(new TextureRegion(this.fbFrameBuffer.getColorBufferTexture())));

		batch.dispose();
	}

	protected FrameBuffer createFrameBuffer() {
		return new FrameBuffer(Format.RGBA8888, 1, graphics.getHeight(), false);
	}

	@Override
	public void dispose() {
		this.fbFrameBuffer.dispose();
	}
}
