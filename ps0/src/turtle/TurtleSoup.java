/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    private static final int CIRCLE_DEGREES = 360;
    private static final int PI = 180;
    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        double turnDegrees = -90;
        for(int i=0;i<4;i++){
            turtle.forward(sideLength);
            turtle.turn(turnDegrees);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (double) ((sides - 2) * PI) /sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        //(n-2)*180/n = angle
        //1-2/n = angle/180
        //n = 2/(1-angle/180)
        return (int) Math.round(2 / (1 - angle / PI));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double RegularPolygonAngle = calculateRegularPolygonAngle(sides);
        double initialTurnDegree = -RegularPolygonAngle/2.0;
        turtle.turn(initialTurnDegree);
        for(int i=0;i<sides;i++){
            turtle.forward(sideLength);
            turtle.turn(PI - RegularPolygonAngle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        /*
        * to get a triangle , and then calculate arctan
        * Math.atan2's return have to * 180/Math.PI to turn to pi
        * DegreeFromCurrentToTarget是从(currentX,currentY)到(targetX,targetY)中间的角度，记为b
        * currentHeading表示的是与y周正方向，以顺时针为正，偏离的角度，记为x
        * 我们需要知道的就是两者之间的夹角，公式推导为b-(90-x)
        * 然后是从heading到目标角度，就是360 - b-(90-x)
        *  */

        double lenx = targetX - currentX;
        double leny = targetY - currentY;
        double DegreeFromCurrentToTarget = Math.atan2(leny,lenx)*180/Math.PI;
        System.out.println("DegreeFromCurrentToTarget: "+DegreeFromCurrentToTarget);
        double result = 360-(DegreeFromCurrentToTarget - (90 - currentHeading));

        if (result == 360) result = 0;
        if (result < 0 || result > 360) result = (360+result)%360;
        return result;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> turnDegrees = new ArrayList<Double>();
        double currentHeading = 0;
        for(int i=1;i<xCoords.size();i++){
            double headingAdjustment = calculateHeadingToPoint(currentHeading,xCoords.get(i-1),yCoords.get(i-1),xCoords.get(i),yCoords.get(i));
            currentHeading = (currentHeading+headingAdjustment)%360;
            System.out.println("currentHeading: "+currentHeading);
            turnDegrees.add(headingAdjustment);
        }
        return turnDegrees;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

//        drawSquare(turtle, 40);

        drawRegularPolygon(turtle,6,40);
        // draw the window
        turtle.draw();
    }

}
