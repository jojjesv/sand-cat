package com.partlight.sandcat.tools;

import java.util.Comparator;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorYComparator implements Comparator<Actor>{
	@Override
	public int compare(Actor o1, Actor o2) {
		return o2.getY() - o1.getY() > 0 ? 1 : -1;
	}
}
