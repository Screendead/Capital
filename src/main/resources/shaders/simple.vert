#version 430

uniform mat4 view;
uniform mat4 transform;

layout (location = 0) in vec2 position;
layout (location = 1) in vec2 textures;

centroid out vec2 tex_coords;

void main() {
	tex_coords = textures;
	gl_Position = view * transform * vec4(position, 1.0, 1.0);
}
