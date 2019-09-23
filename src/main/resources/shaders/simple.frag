#version 430

uniform sampler2D tex;

centroid in vec2 tex_coords;

out vec4 fragColor;

void main() {
	vec4 texture_out = texture(tex, tex_coords);

	fragColor = vec4(texture_out.rgb, texture_out.a);
}
