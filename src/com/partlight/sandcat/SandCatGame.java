package com.partlight.sandcat;

import java.nio.file.FileSystems;
import java.nio.file.Files;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.partlight.sandcat.assets.Assets;
import com.partlight.sandcat.assets.SandCatAssets;

public class SandCatGame extends Game {

	private static Assets	amAssets;
	private static float	uptime;

	
	public static final Assets getAssets() {
		if (SandCatGame.amAssets == null)
			SandCatGame.amAssets = new Assets();
		return SandCatGame.amAssets;
	}

	public static boolean isMobile() {
		final ApplicationType type = Gdx.app.getType();
		return type.equals(ApplicationType.Android) || type.equals(ApplicationType.iOS);
	}

	public static final void loadAssetsJson() {
		final String path = "bin/assets.json";
		if (Files.exists(FileSystems.getDefault().getPath(path)))
			SandCatGame.getAssets().loadAssetJson("bin/assets.json");
	}

	/**
	 * Gets this game's uptime, in seconds.
	 * 
	 * @return This game's uptime, in seconds.
	 */
	public static float uptime() {
		return SandCatGame.uptime;
	}

	protected OrthographicCamera	cCamera;
	protected Viewport				vViewport;
	protected float					bgG;
	protected float					bgR;
	protected float					bgB;
	protected Stage					sHud;

	public SandCatGame() {
	}

	@Override
	public void create() {
		SandCatGame.loadAssetsJson();
		SandCatAssets.create();
		this.loadShaders();

		this.cCamera = new OrthographicCamera();

		this.vViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.cCamera);
	}

	public void disableHud() {
		if (this.sHud == null)
			return;

		this.sHud.clear();
		this.sHud.dispose();
		this.sHud = null;
	}

	@Override
	public void dispose() {
		super.dispose();
		SandCatAssets.dispose();

		this.disableHud();

		if (SandCatGame.amAssets != null) {
			final Array<String> loadedAssets = SandCatGame.amAssets.getAssetManager().getAssetNames();
			for (final String s : loadedAssets)
				SandCatGame.amAssets.getAssetManager().unload(s);
		}
	}

	public void enableHud() {
		this.sHud = new Stage();
	}

	public OrthographicCamera getCamera() {
		return this.cCamera;
	}

	public Stage getHud() {
		return this.sHud;
	}

	public Viewport getViewport() {
		return this.vViewport;
	}

	public void loadShaders() {
		VertexShaders.vertDefault = Gdx.files.internal("bin/shaders/vertex/vert_default.glsl").readString();

		FragmentShaders.fragDefault = Gdx.files.internal("bin/shaders/fragment/frag_default.glsl").readString();
		FragmentShaders.fragDissolve = Gdx.files.internal("bin/shaders/fragment/frag_dissolve.glsl").readString();
		FragmentShaders.fragGaussianBlur = Gdx.files.internal("bin/shaders/fragment/frag_gaussianblur.glsl").readString();
		FragmentShaders.fragGradient = Gdx.files.internal("bin/shaders/fragment/frag_gradient.glsl").readString();
	}

	@Override
	public void render() {
		if (SandCatGame.amAssets != null)
			SandCatGame.amAssets.update();

		Gdx.gl.glClearColor(this.bgR, this.bgG, this.bgB, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		SandCatGame.uptime += Gdx.graphics.getDeltaTime();

		super.render();

		if (this.sHud != null)
			this.sHud.draw();
	}

	public void setBackground(Color color) {
		this.setBackground(color.r, color.g, color.b);
	}

	public void setBackground(float r, float g, float b) {
		this.bgR = r;
		this.bgG = g;
		this.bgB = b;
	}

	/**
	 * Sets the current screen without calling {@link Screen#show()} or
	 * {@link Screen#hide()}.
	 * 
	 * @param screen
	 */
	public void setScreenRaw(Screen screen) {
		this.screen = screen;
	}
}
