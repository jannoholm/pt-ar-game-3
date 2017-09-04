//@description Calculates point on curve in time
//@param startPoint Ending point of the curve
//@param controlPoint Control point for the curve
//@param endPoint Starting point of the curve
//@param time Point of time on the curve

var startPoint = argument0;
var controlPoint = argument1;
var endPoint = argument2;
var timePoint = argument3;

return(sqr(1-argument3) * argument2 + 2*(1-argument3)*argument3*argument1 + sqr(argument3)*argument0);

