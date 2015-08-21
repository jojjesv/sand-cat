package com.partlight.sandcat.action;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class JumpAction extends TemporalAction {

	private float	intensivity;
	private float	startAlpha;
	private float	startRot;
	private float	startScaleX;
	private float	startScaleY;
	private float	startX;
	private float	startY;

	public JumpAction() {
		this.intensivity = 0.5f;
	}

	@Override
	protected void begin() {
		super.begin();

		this.startAlpha = this.actor.getColor().a;
		this.startRot = this.actor.getRotation();
		this.startScaleX = this.actor.getScaleX();
		this.startScaleY = this.actor.getScaleY();
		this.startX = this.actor.getX();
		this.startY = this.actor.getY();
	}

	public float getIntensivity() {
		return this.intensivity;
	}

	public void setIntensivity(float intensivity) {
		this.intensivity = intensivity;
	}

	@Override
	protected void update(float percent) {
		final int phase = (int) Math.floor(4 * percent);

		switch (phase) {
		case 0:
			this.actor.setX(this.startX - 128 * this.intensivity);
			this.actor.setY(this.startY - 32 * this.intensivity);
			this.actor.setRotation(this.startRot - 25 * this.intensivity);
			this.actor.setScaleX(this.startScaleX * (1f + 0.6f * this.intensivity));
			this.actor.setScaleY(this.startScaleY * (1f + 0.6f * this.intensivity));
			this.actor.getColor().a = this.startAlpha * 0.25f;
			break;
		case 1:
			this.actor.setX(this.startX + 100 * this.intensivity);
			this.actor.setY(this.startY - 48 * this.intensivity);
			this.actor.setRotation(this.startRot + 16 * this.intensivity);
			this.actor.setScaleX(this.startScaleX * (1 + 0.5f * this.intensivity));
			this.actor.setScaleY(this.startScaleY * (1 + 0.5f * this.intensivity));
			this.actor.getColor().a = this.startAlpha * 0.25f;
			break;
		case 2:
			this.actor.setX(this.startX);
			this.actor.setY(this.startY + 24 * this.intensivity);
			this.actor.setRotation(this.startRot);
			this.actor.setScale(this.startScaleX * (1 + 0.7f * this.intensivity));
			this.actor.setScale(this.startScaleY * (1 + 0.7f * this.intensivity));
			this.actor.getColor().a = this.startAlpha * 0.66f;
			break;
		case 3:
			this.actor.getColor().a = this.startAlpha;
			this.actor.setRotation(this.startRot);
			this.actor.setScale(this.startScaleX, this.startScaleY);
			this.actor.setX(this.startX);
			this.actor.setY(this.startY);
			break;
		}
	}
}
