uniform vec4 u_boundary[1];
uniform float radius;
varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()
{
	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
	
	for (int i = 0; i < u_boundary.length(); i++)
	{
		if (v_texCoords.x >= u_boundary[i].x - radius)
		{
			if (v_texCoords.x > u_boundary[i].z)
				gl_FragColor.a *= clamp((v_texCoords.x - u_boundary[i].z) / radius, 0.0, 1.0);
			else
				gl_FragColor.a *= clamp((u_boundary[i].x - v_texCoords.x) / radius, 0.0, 1.0);
		}
		
		if (v_texCoords.y >= u_boundary[i].y - radius)
		{
			if (v_texCoords.y > u_boundary[i].w)
				gl_FragColor.a *= clamp((v_texCoords.x - u_boundary[i].w) / radius, 0.0, 1.0);
			else
				gl_FragColor.a *= clamp((u_boundary[i].y - v_texCoords.y) / radius, 0.0, 1.0);
		}
	}
}