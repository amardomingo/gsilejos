package es.upm.dit.gsi.gsilejos.lejos.nxt;

import es.upm.dit.gsi.gsilejos.lejos.robotics.RegulatedMotor;

/**
 * 
 * Interface for the motors
 * 
 * @author amardomingo
 *
 */
public interface Motor {
	public static final RegulatedMotor A = new RegulatedMotor (); // LEFT
    public static final RegulatedMotor B = new RegulatedMotor (); // HEAD
    public static final RegulatedMotor C = new RegulatedMotor (); // RIGHT
}
