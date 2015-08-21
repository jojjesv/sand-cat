varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec4 u_color2;

void main()
{
	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
	gl_FragColor.a *= v_texCoords.y;
}