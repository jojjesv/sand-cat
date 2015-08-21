package com.partlight.sandcat.android;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public final class AndroidConfiguration extends AndroidApplicationConfiguration {

	public AndroidConfiguration() {
		this.hideStatusBar = true;
		this.useImmersiveMode = true;
	}
}
