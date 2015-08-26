package com.partlight.sandcat.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.partlight.sandcat.FragmentShaders;
import com.partlight.sandcat.VertexShaders;

public class DissolveImage extends Image {

	private FloatAction faProgression;

	private boolean			isAnimating;
	private int				shaderMapLocation;
	private int				shaderInvertLocation;
	private int				shaderRatioLocation;
	private ShaderProgram	spDissolveShader;
	private Texture			tMap;

	public DissolveImage(TextureRegion texture, Texture map) {
		super(texture);
		this.tMap = map;
		this.faProgression = new FloatAction(0, 1) {
			@Override
			public boolean act(float delta) {
				DissolveImage.this.updateShader(this.getValue());
				return super.act(delta);
			}

			@Override
			protected void end() {
				DissolveImage.this.onAnimationFinish();
				super.end();
			}
		};
		this.faProgression.setInterpolation(Interpolation.linear);

		this.spDissolveShader = new ShaderProgram(VertexShaders.vertDefault, FragmentShaders.fragDissolve);

		this.shaderRatioLocation = this.spDissolveShader.getUniformLocation("u_ratio");
		this.shaderMapLocation = this.spDissolveShader.getUniformLocation("u_invert");
		this.shaderMapLocation = this.spDissolveShader.getUniformLocation("u_map");
	}

	public void animate() {
		this.animate(0.25f);
	}

	public void animate(float duration) {
		this.faProgression.setDuration(duration);
		this.faProgression.reset();

		this.isAnimating = true;
		this.addAction(this.faProgression);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (this.isAnimating) {
			batch.setShader(this.spDissolveShader);
			super.draw(batch, parentAlpha);
			batch.flush();
			batch.setShader(null);
		} else
			super.draw(batch, parentAlpha);
	}

	protected void onAnimationFinish() {
		DissolveImage.this.isAnimating = false;
	}

	public void setInvertShader(boolean invertShader) {
		this.spDissolveShader.begin();
		this.spDissolveShader.setUniformi(this.shaderInvertLocation, ((invertShader) ? 1 : 0));
		this.spDissolveShader.end();
	}

	protected void updateShader(float percent) {
		this.spDissolveShader.begin();
		this.tMap.bind(1);
		this.spDissolveShader.setUniformi(this.shaderMapLocation, 1);
		this.spDissolveShader.setUniformf(this.shaderRatioLocation, percent);
		this.spDissolveShader.end();

		Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
	}
}
