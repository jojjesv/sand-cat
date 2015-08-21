package com.partlight.sandcat.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Pool;
import com.partlight.sandcat.SandCatGame;
import com.partlight.sandcat.action.JumpAction;

public final class ActorUtils {
	private static final Pool<AlphaAction> ALPHA_POOL = new Pool<AlphaAction>() {
		@Override
		protected AlphaAction newObject() {
			return new AlphaAction();
		}
	};

	public static final int	ANIMATION_FADE_IN						= 0;
	public static final int	ANIMATION_FADE_IN_SCALE					= 1;
	public static final int	ANIMATION_FADE_OUT						= 2;
	public static final int	ANIMATION_FADE_OUT_SCALE				= 3;
	public static final int	ANIMATION_JUMP_IN_HIGH_INTENSIVITY		= 4;
	public static final int	ANIMATION_JUMP_IN_LOW_INTENSIVITY		= 5;
	public static final int	ANIMATION_JUMP_IN_MEDIUM_INTENSIVITY	= 6;

	private static final Pool<JumpAction> JUMP_POOL = new Pool<JumpAction>() {
		@Override
		protected JumpAction newObject() {
			return new JumpAction();
		}
	};

	private static final Pool<ScaleToAction> SCALE_POOL = new Pool<ScaleToAction>() {
		@Override
		protected ScaleToAction newObject() {
			return new ScaleToAction();
		}
	};

	public static void animateActor(Actor actor, float duration, int animation) {
		ActorUtils.animateActor(actor, duration, animation, Interpolation.circle);
	}

	public static void animateActor(Actor actor, float duration, int animation, Interpolation interpolation) {

		final float scaleX = actor.getScaleX();
		final float scaleY = actor.getScaleY();

		TemporalAction[] actions = null;

		switch (animation) {

		case ActorUtils.ANIMATION_FADE_IN:
			actor.getColor().a = 0;

			actions = new TemporalAction[1];

			actions[0] = ActorUtils.ALPHA_POOL.obtain();
			((AlphaAction) actions[0]).setAlpha(1);
			actions[0].setPool(ActorUtils.ALPHA_POOL);
			break;

		case ActorUtils.ANIMATION_FADE_OUT:
			actor.getColor().a = 1;

			actions = new TemporalAction[1];

			actions[0] = ActorUtils.ALPHA_POOL.obtain();
			((AlphaAction) actions[0]).setAlpha(0);
			actions[0].setPool(ActorUtils.ALPHA_POOL);
			break;

		case ActorUtils.ANIMATION_FADE_OUT_SCALE:
			actor.getColor().a = 1;

			actions = new TemporalAction[2];

			actions[0] = ActorUtils.ALPHA_POOL.obtain();
			((AlphaAction) actions[0]).setAlpha(0);
			actions[1] = ActorUtils.SCALE_POOL.obtain();
			((ScaleToAction) actions[1]).setScale(scaleX * 1.5f, scaleY * 1.5f);

			actions[0].setPool(ActorUtils.ALPHA_POOL);
			actions[1].setPool(ActorUtils.SCALE_POOL);
			break;

		case ActorUtils.ANIMATION_FADE_IN_SCALE:
			actor.getColor().a = 0;
			actor.setScale(scaleX * 1.5f, scaleY * 1.5f);

			actions = new TemporalAction[2];

			actions[0] = ActorUtils.ALPHA_POOL.obtain();
			((AlphaAction) actions[0]).setAlpha(1);
			actions[1] = ActorUtils.SCALE_POOL.obtain();
			((ScaleToAction) actions[1]).setScale(scaleX, scaleY);

			actions[0].setPool(ActorUtils.ALPHA_POOL);
			actions[1].setPool(ActorUtils.SCALE_POOL);
			break;

		case ActorUtils.ANIMATION_JUMP_IN_LOW_INTENSIVITY:
			actions = new TemporalAction[1];

			actions[0] = ActorUtils.JUMP_POOL.obtain();
			((JumpAction) actions[0]).setIntensivity(0.33f);
			actions[0].setPool(ActorUtils.JUMP_POOL);
			break;
		case ActorUtils.ANIMATION_JUMP_IN_MEDIUM_INTENSIVITY:
			actions = new TemporalAction[1];

			actions[0] = ActorUtils.JUMP_POOL.obtain();
			((JumpAction) actions[0]).setIntensivity(0.66f);
			actions[0].setPool(ActorUtils.JUMP_POOL);
			break;
		case ActorUtils.ANIMATION_JUMP_IN_HIGH_INTENSIVITY:
			actions = new TemporalAction[1];

			actions[0] = ActorUtils.JUMP_POOL.obtain();
			((JumpAction) actions[0]).setIntensivity(1);
			actions[0].setPool(ActorUtils.JUMP_POOL);
			break;
		}

		if (actions == null)
			return;

		for (final TemporalAction action : actions) {
			action.setDuration(duration);
			action.setInterpolation(interpolation);
			actor.addAction(action);
		}
	}

	public static void animateSineColor(Actor actor, Color c1, Color c2, float duration) {
		final float FACTOR = ActorUtils.getSineValue(duration);
		final float r = c1.r + (c2.r - c1.r) * FACTOR;
		final float g = c1.g + (c2.g - c1.g) * FACTOR;
		final float b = c1.b + (c2.b - c1.b) * FACTOR;

		actor.setColor(r, g, b, actor.getColor().a);
	}

	public static float getSineValue(float duration) {
		float value = (float) Math.sin(SandCatGame.uptime() / duration);

		value *= 0.5f;
		value += 0.5f;

		return value;
	}
}
