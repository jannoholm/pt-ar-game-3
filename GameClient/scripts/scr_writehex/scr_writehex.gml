var a = (argument[0] >> 4) & 15;
var b = argument[0] & 15;
var result="";
switch (a) {
	case 0:
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
	case 9:
		result = result+string(a);
		break;
	case 10:
		result = result+"A";
		break;
	case 11:
		result = result+"B";
		break;
	case 12:
		result = result+"C";
		break;
	case 13:
		result = result+"D";
		break;
	case 14:
		result = result+"E";
		break;
	case 15:
		result = result+"F";
		break;
}
switch (b) {
	case 0:
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
	case 9:
		result = result+string(b);
		break;
	case 10:
		result = result+"A";
		break;
	case 11:
		result = result+"B";
		break;
	case 12:
		result = result+"C";
		break;
	case 13:
		result = result+"D";
		break;
	case 14:
		result = result+"E";
		break;
	case 15:
		result = result+"F";
		break;
}
show_debug_message(result);