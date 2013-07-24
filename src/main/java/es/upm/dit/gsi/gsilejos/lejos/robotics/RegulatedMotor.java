package es.upm.dit.gsi.gsilejos.lejos.robotics;

import es.upm.dit.gsi.gsilejos.lejos.robotics.navigation.DifferentialPilot;
//import es.upm.dit.gsi.gsilejos.lejos.robotics.navigation.MoveListener;


/**
 * 
 * Simulates the Lejos RegulatedMotor for the simulator
 * 
 * Be careful, some methods are unimplemented
 * 
 * @author amardomingo
 *
 */
public class RegulatedMotor {

    private double speed;       // Speed
    private byte direction;     // Direction: 0F 1L 2R
    private boolean moving;     // T or F
    private int anguloRotado;   // Angle rotated by the motor.

    DifferentialPilot piloto;


    ///////////////////////////
    // METODOS IMPLEMENTADOS //
    ///////////////////////////

    /**
     * Constructor.
     * Default speed is 2 (0.2 in simbad).
     * Default direction is 2 (right, as stated in the algorithm).
     * Default move value is 0.
     */
    public RegulatedMotor() {
       this.speed = 2;
       this.direction = 2;
       this.moving = false;
    }
    

    /**
     * Setter.
     * Sets the pilot the simulator belongs to, and its direction
     * 
     * SIMULATOR ONLY
     *
     * @param piloto
     * @param direction
     */
    public void setMotor(DifferentialPilot piloto, byte direction) {
        this.piloto = piloto;
        this.direction = direction;
    }

    /**
     * Starts the motor at the speed given for the variable 'speed'
     * It will turn to one direction or another depending of the value of
     * the 'direction' variable.
     *
     * @throws IllegalStateException If the pilot is already moving
     */
    public void forward() throws IllegalStateException {
        //if (!this.piloto.getRotacionMotor()) {
            this.piloto.setRotateSpeed(speed);
            switch (this.direction) {
                case 0:
                    this.piloto.rotate(speed);
                    break;
                case 1:
                    this.piloto.rotate(speed);
                    break;
                case 2:
                    this.piloto.rotate((-1)*speed);
                    break;
            }
            this.moving = true;
            //this.piloto.setRotacionMotor(true);
        //} else {
        //   throw new IllegalStateException("No se puede tener dos motores rotando a la vez.");
        //}
    }

    /**
     * Starts the motor at the speed given for the variable 'speed'
     * It will turn to one direction or another depending of the value of
     * the 'direction' variable.
     * Is opposite to 'forward()'
     *
     * @throws IllegalStateException If the pilot is already moving
     */
    public void backward() {
        //if (!this.piloto.getRotacionMotor()) {
            this.piloto.setRotateSpeed(speed);
            switch (direction) {
                case 0:
                    this.piloto.rotate((-1)*speed);
                    break;
                case 1:
                    this.piloto.rotate((-1)*speed);
                    break;
                case 2:
                    this.piloto.rotate(speed);
                    break;
            }
            this.moving = true;
            //this.piloto.setRotacionMotor(true); // Esto debería hacerlo localmente
        //} else {
        //    throw new IllegalStateException("No se puede tener dos motores rotando a la vez.");
        //}
    }

    /**
     * Stops the motor, and set 'moving' to false
     */
    public void stop() {
        //byte motor = 1;
        this.piloto.stop();
        this.moving = false;
        //this.piloto.setRotacionMotor(false);
    }

    /**
     * Returns the 'moving' variable
     *
     * @return true if the motor is on the move
     */
    public boolean isMoving() {
        return this.moving;
    }

    /**
     * Rotates the pilot to the position given as argument
     *
     * @param limitAngle - Angle to be rotated to.
     */
    public void rotateTo(int limitAngle) {
        int anguloActual = this.anguloRotado%360;
        this.rotate(limitAngle-anguloActual);
    }

    /**
     * Runs the 'rotateTo' method in a separate Thread
     * Experimental
     */
    public void rotateTo(final int limitAngle,boolean immediateReturn) {
        
        if (immediateReturn) {
            Thread thread = new Thread(new Runnable() {
                public void run(){
                    rotateTo(limitAngle);
                }
            });
            thread.start();
        } else {
            this.rotateTo(limitAngle);
        }
    }

    /**
     * Rotate the pilot the angle given as parameters
     *
     * @param angle - Angle to rotate.
     */
    public void rotate(int angle) {
        switch(this.direction) {
            case 0:
                this.piloto.rotate(angle);
                break;
            case 1:
                this.piloto.rotate(angle);
                break;
            case 2:
                this.piloto.rotate((-1)*angle);
                break;
        }
        this.anguloRotado += angle;
    }

    /**
     * Runs the 'rotate(angle)' method in a separated Thread
     * Experimental
     */
    public void rotate(final int angle, boolean immediateReturn) {
        if (immediateReturn) {
            Thread thread = new Thread(new Runnable() {
                public void run(){
                    rotate(angle);
                }
            });
            thread.start();
        } else {
            rotate(angle);
        }
    }

    /**
     * Setter
     * Sets the speed of the motor
     *
     * @param speed int representing the speed of the pilot
     */
    public void setSpeed (int speed) {
        this.speed = speed;
    }

    /**
     * Getter
     * Return the motor speed, regardless of it being stop or in moventent
     *
     * @return speed int with the motor speed
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Getter
     * Return the same value as getSpeed()
     * 
     * @return speed - int with the motor speed
     * @see getSpeed()
     */
    public double getRotationSpeed() {
        return this.getSpeed();
    }
   
    //////////////////////////////
    // METODOS NO IMPLEMENTADOS //
    //////////////////////////////

    public void addMoveListener(RegulatedMotorListener m) {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public RegulatedMotorListener removeListener() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void flt() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    void updateState( int mode) {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public int getLimitAngle() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public float getMaxSpeed() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public boolean isStalled() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void setStallThreshold(int error, int time){
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public int getTachoCount() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void resetTachoCount() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void setAcceleration() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void waitComplete() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

}







