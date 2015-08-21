varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_map;
uniform float u_ratio;
uniform bool u_invert;

void main()
{
	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
	
	vec4 map_c = texture2D(u_map, v_texCoords);
	float map_p = map_c.r * map_c.g * map_c.b;
	
	float dif = 0.0;
	if (u_invert)
	{
		dif = map_p - u_ratio;
	}
	else
	{
		dif = u_ratio - map_p;
	}
	
	gl_FragColor.a *= clamp((dif) / 0.1, 0.0, 1.0);
}