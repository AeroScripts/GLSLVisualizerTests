uniform vec2 location;
uniform vec3 colour;

void main() {
	float distance = length(location - gl_FragCoord.xy);
	float attenuation = 1.0 / distance;
        vec4 result = vec4(attenuation*colour.x,attenuation*colour.y, attenuation*colour.z, 1.0); // OSX ATI fix

	gl_FragColor = result;
}