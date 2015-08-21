varying vec4 v_color;
varying vec2 v_texCoords;
uniform vec2 resolution;
uniform int samples;
uniform sampler2D u_texture;

void main()
{
	vec4 colors = vec4(0.0);
	
	int samples_quater = samples / 4;
	
	for (int x = -samples_quater; x < samples_quater; x++)
		colors += texture2D(u_texture, v_texCoords + vec2((1.0 / resolution.x) * x, 0.0));
	for (int y = -samples_quater; y < samples_quater; y++)
		colors += texture2D(u_texture, v_texCoords + vec2(0.0, (1.0 / resolution.y) * y));
	
	colors /= vec4(samples);
	
	gl_FragColor = colors;
}