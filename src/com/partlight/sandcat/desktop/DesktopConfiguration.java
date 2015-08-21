package com.partlight.sandcat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public final class DesktopConfiguration extends LwjglApplicationConfiguration {

	public DesktopConfiguration(String... mainArgs) {

		String s = null;

		for (int i = 0; i < mainArgs.length; i++) {
			s = mainArgs[i];
			if (s.contentEquals("-nosound")) {
				LwjglApplicationConfiguration.disableAudio = true;
				break;
			} else if (s.contentEquals("-fullscreen")) {
				this.fullscreen = true;
				break;
			}
		}
	}
}
