#version 430

uniform mat4 view;
uniform mat4 transform;

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normals;
layout (location = 2) in vec2 textures;

centroid out vec2 tex_coords;

void main() {
	tex_coords = textures;
	gl_Position = view * transform * vec4(position + normals, 1.0);
}
