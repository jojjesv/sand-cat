package com.partlight.sandcat.assets;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Asset implements Serializable {

	public String	path;
	public Class<?>	type;

	@Override
	public void read(Json json, JsonValue jsonData) {
		final String typeString = jsonData.getString("type");

		try {
			this.type = Class.forName(typeString);
		} catch (final ClassNotFoundException ex) {
		}

		this.path = jsonData.getString("path");
	}

	@Override
	public void write(Json json) {
		json.writeValue("type", this.type.getName());
		json.writeValue("path", this.path);
	}
}
