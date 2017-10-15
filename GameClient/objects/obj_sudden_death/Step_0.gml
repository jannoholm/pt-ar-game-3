lifetime=lifetime+1;
if (lifetime_limit < lifetime) {
	instance_destroy();
}