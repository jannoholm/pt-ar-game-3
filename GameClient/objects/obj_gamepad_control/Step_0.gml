
var pad = global.gamepadDeviceId;
var debugString = "Gamepad input: ";

for ( var i = 0; i < gamepad_axis_count(pad); i++ ) {
	debugString += "a" + string(i) + ":" + string(gamepad_axis_value(pad, i)) + " ";
}

debugString += "\t";



for ( var i = 0; i < gamepad_button_count(pad); i++ ) {
	debugString += "b" + string(i) + ":" + string(gamepad_button_check(pad, i)) + " ";
}

// show_debug_message(debugString);