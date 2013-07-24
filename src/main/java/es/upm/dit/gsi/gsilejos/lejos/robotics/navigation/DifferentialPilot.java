package es.upm.dit.gsi.gsilejos.lejos.robotics.navigation;

// imports para Lejos
import java.util.ArrayList;
import java.util.Random;
import javax.vecmath.Vector3d;

import es.upm.dit.gsi.gsilejos.lejos.nxt.Motor;
import es.upm.dit.gsi.gsilejos.lejos.nxt.UltrasonicSensor;
import es.upm.dit.gsi.gsilejos.lejos.robotics.RegulatedMotor;
import es.upm.dit.gsi.gsilejos.simbad.gui.Simbad;

import simbad.sim.Wall;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Robot;


/**
 * 
 * Class to simulate the Lejos DifferentialPilot
 * 
 * Be careful, some methods are unimplemented
 * 
 * @author amardomingo
 *
 */
public class DifferentialPilot {

    private Simbad frame;
    private EnvironmentDescription enviroment;
    private Robot robot;
    private UltrasonicSensor sensorL;
    private UltrasonicSensor sensorR;
    private UltrasonicSensor sensorF;
    private RegulatedMotor leftMotor;
    private RegulatedMotor rightMotor;

    private double speed = 0.3;
    private int toWaitTime = 100;
    private boolean realism = false;
    private double width = 1.1;
    private double thick = 0.1;
    private boolean rotacionMotor = false;
    private int maze;
    private float offsetX = -2;
    private float offsetZ = -2;
    //Para definir el laberinto
    private ArrayList<Boolean> mazeListH1 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListH2 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListH3 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListH4 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListH5 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListV1 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListV2 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListV3 = new ArrayList<Boolean>();
    private ArrayList<Boolean> mazeListV4 = new ArrayList<Boolean>();

    ///////////////////////////
    // METODOS IMPLEMENTADOS //
    ///////////////////////////
    /**
     * Constructor.
     *
     * @param wheelDiameter Unused by the simulator
     * @param trackWidth Unused by the simulator
     * @param leftMotor
     * @param rightMotor
     */
    public DifferentialPilot(final float wheelDiameter, final float trackWidth,
            final RegulatedMotor leftMotor, final RegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * Method to configure and start simbad.
     *
     * @param x,y 'int' Starting position for the robot
     * @param waitTime 'int' time to wait between sensor measurements
     * @param sensor the 'UltrasonicSensor' used by the pilot
     * @param realism if true, add random deviations to the movements
     * @param maze The maze number (from 0 to 5)
     */
    public void setSimbad(int x, int z, int waitTime, UltrasonicSensor sensorL, UltrasonicSensor sensorR, UltrasonicSensor sensorF,
            boolean realism, int maze) {
        this.toWaitTime = waitTime;
        this.maze = maze;
        this.realism = realism;
        if (realism) width = 0.8;
        Vector3d posicion = new Vector3d(x + offsetX, 0, z + 0.5 + offsetZ);
        this.setPiloto(posicion, "Skids");
        this.setEnviroment();
        this.setMotores();
        this.sensorF = sensorF;
        this.sensorF.setSensor(robot);
        this.sensorL = sensorL;
        this.sensorL.setSensor(robot);
        this.sensorR = sensorR;
        this.sensorR.setSensor(robot);
        enviroment.showAxis(false);
        this.frame = new Simbad(enviroment, false);
        frame.startSimbad("topview", true);

        this.calibrarSimbad(waitTime);
    }

    /**
     * Setter.
     * Creates the robot used by the simulator
     *
     * @param posicion 'Vec
     */
    private void setPiloto(Vector3d posicion, String nombre) {
        this.robot = new Robot(posicion, nombre);
    }

    /**
     * Setter.
     * Configure the robots, directions and the pilot they belong to
     */
    private void setMotores() {
        this.leftMotor.setMotor(this, (byte) 1);
        this.rightMotor.setMotor(this, (byte) 2);
    }

    /**
     * Generate the maze from the walls array
     */
    private void generateMaze() {
        for (int i = 0; i < 5; i++) {
            if ((boolean) mazeListH1.get(i)) {
                Wall wall = new Wall(new Vector3d(i + offsetX, 0, 0 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 5; i++) {
            if ((boolean) mazeListH2.get(i)) {
                Wall wall = new Wall(new Vector3d(i + offsetX, 0, 1 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 5; i++) {
            if ((boolean) mazeListH3.get(i)) {
                Wall wall = new Wall(new Vector3d(i + offsetX, 0, 2 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 5; i++) {
            if ((boolean) mazeListH4.get(i)) {
                Wall wall = new Wall(new Vector3d(i + offsetX, 0, 3 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 5; i++) {
            if ((boolean) mazeListH5.get(i)) {
                Wall wall = new Wall(new Vector3d(i + offsetX, 0, 4 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 6; i++) {
            if ((boolean) mazeListV1.get(i)) {
                Wall wall = new Wall(new Vector3d(i - 0.5 + offsetX, 0, 0.5 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                wall.rotate90(1);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 6; i++) {
            if ((boolean) mazeListV2.get(i)) {
                Wall wall = new Wall(new Vector3d(i - 0.5 + offsetX, 0, 1.5 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                wall.rotate90(1);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 6; i++) {
            if ((boolean) mazeListV3.get(i)) {
                Wall wall = new Wall(new Vector3d(i - 0.5 + offsetX, 0, 2.5 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                wall.rotate90(1);
                enviroment.add(wall);
            }
        }
        for (int i = 0; i < 6; i++) {
            if ((boolean) mazeListV4.get(i)) {
                Wall wall = new Wall(new Vector3d(i - 0.5 + offsetX, 0, 3.5 + offsetZ),
                        (float) width, (float)thick, (float) 0.8, enviroment);
                wall.rotate90(1);
                enviroment.add(wall);
            }
        }
    }

    /**
     * Simbad Self-calibration. Sets the wait time according to its 
     * own testing
     */
    public void calibrarSimbad() {
        int toWaitTime = this.toWaitTime;
        calibrarSimbad(toWaitTime);
    }

    /**
     * Calibrate simbad to the given waitTime
     *
     * @param toWaitTime the time between sensors meassurements
     */
    public void calibrarSimbad(int toWaitTime) {
        this.toWaitTime = toWaitTime;
        this.sensorF.setWaitTime(toWaitTime);
        this.sensorL.setWaitTime(toWaitTime);
        this.sensorR.setWaitTime(toWaitTime);
        wait(2000);
    }

    /**
     * Setter.
     * Sets the pilot speed, depending on the 'byte' argument.
     * If cero(0), sets the translation speed, and, if one(1),
     * sets the rotation speed
     *
     * @param speed - new speed
     * @param tipo 0 for translation, 1 for rotation.
     */
    private void setSpeed(double speed, byte tipo) {
        this.speed = speed / 10;
        if (tipo == 0) {
            this.robot.setTranslationalVelocity(this.speed);
        } else {
            this.robot.setRotationalVelocity(this.speed);
        }
    }

    /**
     * Setter.
     * Sets the pilot travel speed
     *
     * @param speed - new Speed
     */
    public void setTravelSpeed(double speed) {
        this.setSpeed(speed, (byte) 0);
    }
    
    /**
     * Setter.
     * Sets the pilot rotation speed
     *
     * @param speed - new Speed
     */
    public void setRotateSpeed(double speed) {
        this.setSpeed(speed, (byte) 1);
    }

    /**
     * Getter.
     * Returns the pilot travel speed
     * 
     * @return float with the speed
     */
    public float getTravelSpeed() {
        return (float) this.speed;
    }

    /**
     * Sets the pilot translational speed
     */
    public void forward() {
        this.robot.setTranslationalVelocity(speed);
    }

    /**
     * Sets the pilot transaltional speed, reverse.
     * Inverse operation than 'forward'
     */
    public void backward() {
        this.robot.setTranslationalVelocity((-1) * speed);
    }

    /**
     * Rotate the motor the given angle
     *
     * @param angle - The angle to rotate, in degrees
     */
    public void rotate(double angle) {
        //Variable aleatoria para añadir realismo al giro
        int va = 0;
        int va2 = 0;
        if (this.realism && angle > 30) {
            Random r = new Random();
            va = r.nextInt(2);
            va2 = r.nextInt(2);
            if (va2 == 0) {
                va = -va;
            }
        }
        this.robot.rotateY((Math.PI * (angle + va)) / 180);
    }

    /**
     * Runs 'rotate' in a new Thread if immediateReturn is true
     *
     * @param angle
     * @param immediateReturn if true, launch a new thread
     */
    public void rotate(final double angle, boolean immediateReturn) {
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
     * Rotates 90º to the left
     * 
     */
    public void rotateLeft() {
    	this.rotate(90);
    }
    
    /**
     * Rotates 90º to the right
     * 
     */
    public void rotateRight() {
    	this.rotate(-90);
    }

    /**
     * Stops the robot, setting both Translational and Rotational Speeds to 0
     */
    public void stop() {
        this.robot.setTranslationalVelocity(0);
        this.robot.setRotationalVelocity(0);
    }
    
    /**
     * Stops the robot, setting both Translational and Rotational Speeds to 0
     * 
     * For the simulator, this is equals to call stop()
     */
    public void quickStop() {
    	stop();
    }

    /**
     * Returns true if any of the speeds is true
     *
     * @return true if the pilot is on the move
     */
    public boolean isMoving() {
        return (this.robot.getTranslationalVelocity() != 0 
                || this.robot.getRotationalVelocity() != 0);
        /*boolean isMoving;
        if (this.robot.getTranslationalVelocity() != 0 || this.robot.getRotationalVelocity() != 0) {
            isMoving = true;
        } else {
            isMoving = false;
        }
        return isMoving;*/
    }


    /**
     * Moves the pilot the given distance
     *
     * @param distance
     */
    public void travel(double distance) {
        double inicio = this.robot.getOdometer();
        boolean noProblem = true; //Para evitar bloqueos contra paredes
        float travelTemp = 0;
        int count = 0;

        //Random variable for realism 
        int va = 0;
        int va2 = 0;
        if (this.realism) {
            Random r = new Random();
            va = r.nextInt(5);
            va2 = r.nextInt(2);
            if (va2 == 0) {
                va = -va;
            }
        }

        while (noProblem && ((float) (this.robot.getOdometer() - inicio) * (1000 / 4) < Math.abs(distance + va))) {
            if (distance > 0) {
                this.robot.setTranslationalVelocity(this.speed);
            }
            if (distance < 0) {
                this.robot.setTranslationalVelocity(-this.speed);
            }

            //To prevent the robot remaining lock on an obstacle
            if (travelTemp == (float) (this.robot.getOdometer() - inicio) * (1000 / 4)) {
                count++;
            } else {
                count = 0;
            }
            if (count == 15) {
                noProblem = false;
                count = 0;
            }
            travelTemp = (float) (this.robot.getOdometer() - inicio) * (1000 / 4);
            wait(this.toWaitTime / 2); // para que le de tiempo a actualizar el odometro
        }
        this.robot.setTranslationalVelocity(0);
    }

    /**
     * Runs the 'travel(distance)' on a new Thread if immediateReturn is true
     *
     * @param distance
     * @param immediateReturn if true, launch a new Thread.
     */
    public void travel(final double distance, boolean immediateReturn) {
        if (immediateReturn) {
            Thread thread = new Thread(new Runnable() {
                public void run(){
                    travel(distance);
                }
            });
            thread.start();
        } else {
            travel(distance);
        }
    }

    /**
     * Method to wait
     *
     * @param n the number of miliseconds to wait
     */
    private static void wait(int n) {
        try {
            Thread.sleep(n);
        } catch(Exception e) {
            System.out.println("Exception while thread was sleeping");
            System.out.println(e.getStackTrace());
        }
        //long t0, t1;
        //t0 = System.currentTimeMillis();
        //do {
        //    t1 = System.currentTimeMillis();
        //} while (t1 - t0 < n);
    }

    /////////////////////////////
    // METODOS SIN IMPLEMENTAR //
    /////////////////////////////
    public DifferentialPilot(final float wheelDiameter, final float trackWidth,
            final Motor leftMotor, final Motor rightMotor,
            final boolean reverse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public DifferentialPilot(final float leftWheelDiameter,
            final float rightWheelDiameter, final float trackWidth,
            final Motor leftMotor, final Motor rightMotor,
            final boolean reverse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void addMoveListener(MoveListener m) {
    	throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public boolean isStalled() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    protected void movementStart(boolean alert) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }	

    public void setSpeed(final int leftSpeed, final int rightSpeed)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void setAcceleration(int acceleration) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void setMinRadius(double radius) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public float getMaxTravelSpeed() throws UnsupportedOperationException {
        // Simbad no está limitado por la velocidad
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public double getRotateSpeed() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public float getMaxRotateSpeed() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public double getRotateMaxSpeed() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public double getMinRadiuos() throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public Move getMovement() throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public float getAngleIncrement() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void reset() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void steer(double turnRate)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void steerBackward(double turnRate)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void steer(double turnRate, double angle)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void steer(double turnRate, double angle, boolean immediateReturn)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void rotationStarted(RegulatedMotor motor, int tachoCount, boolean stall, long ts)
    		throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void	rotationStopped(RegulatedMotor motor, int tachoCount, boolean stall, long ts)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    //public void arc(final double radius) throws UnsupportedOperationException {
    //    throw new UnsupportedOperationException("This method is not implemented yet.");
    //}

    public void arc(final double radius, final double angle)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void arc(final double radius, final double angle,
            final boolean immediateReturn) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
    
    public void arcBackward(final double radius)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void travelArc(double radius, double distance) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    public void travelArc(double radius, double distance, boolean immediateReturn)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    /**
     * Creates the mazes
     */
    private void setEnviroment() {
        this.enviroment = new EnvironmentDescription();
        this.enviroment.setWorldSize(8);
        this.enviroment.add(robot);
        if (maze == 0) {
            mazeListH1.add(0, true);
            mazeListH1.add(1, true);
            mazeListH1.add(2, true);
            mazeListH1.add(3, true);
            mazeListH1.add(4, true);

            mazeListH2.add(0, true);
            mazeListH2.add(1, true);
            mazeListH2.add(2, false);
            mazeListH2.add(3, true);
            mazeListH2.add(4, false);

            mazeListH3.add(0, true);
            mazeListH3.add(1, true);
            mazeListH3.add(2, true);
            mazeListH3.add(3, false);
            mazeListH3.add(4, true);

            mazeListH4.add(0, true);
            mazeListH4.add(1, false);
            mazeListH4.add(2, true);
            mazeListH4.add(3, true);
            mazeListH4.add(4, false);

            mazeListH5.add(0, true);
            mazeListH5.add(1, true);
            mazeListH5.add(2, false);
            mazeListH5.add(3, true);
            mazeListH5.add(4, true);

            mazeListV1.add(0, true);
            mazeListV1.add(1, false);
            mazeListV1.add(2, false);
            mazeListV1.add(3, false);
            mazeListV1.add(4, false);
            mazeListV1.add(5, true);

            mazeListV2.add(0, true);
            mazeListV2.add(1, false);
            mazeListV2.add(2, false);
            mazeListV2.add(3, false);
            mazeListV2.add(4, true);
            mazeListV2.add(5, true);

            mazeListV3.add(0, true);
            mazeListV3.add(1, false);
            mazeListV3.add(2, false);
            mazeListV3.add(3, false);
            mazeListV3.add(4, false);
            mazeListV3.add(5, true);

            mazeListV4.add(0, true);
            mazeListV4.add(1, false);
            mazeListV4.add(2, false);
            mazeListV4.add(3, false);
            mazeListV4.add(4, true);
            mazeListV4.add(5, true);

        } else if (maze == 1) {
            mazeListH1.add(0, true);
            mazeListH1.add(1, true);
            mazeListH1.add(2, true);
            mazeListH1.add(3, true);
            mazeListH1.add(4, true);

            mazeListH2.add(0, true);
            mazeListH2.add(1, true);
            mazeListH2.add(2, false);
            mazeListH2.add(3, true);
            mazeListH2.add(4, false);

            mazeListH3.add(0, true);
            mazeListH3.add(1, true);
            mazeListH3.add(2, false);
            mazeListH3.add(3, false);
            mazeListH3.add(4, true);

            mazeListH4.add(0, false);
            mazeListH4.add(1, false);
            mazeListH4.add(2, false);
            mazeListH4.add(3, true);
            mazeListH4.add(4, false);

            mazeListH5.add(0, true);
            mazeListH5.add(1, true);
            mazeListH5.add(2, true);
            mazeListH5.add(3, true);
            mazeListH5.add(4, false);

            mazeListV1.add(0, true);
            mazeListV1.add(1, false);
            mazeListV1.add(2, false);
            mazeListV1.add(3, false);
            mazeListV1.add(4, false);
            mazeListV1.add(5, true);

            mazeListV2.add(0, true);
            mazeListV2.add(1, false);
            mazeListV2.add(2, false);
            mazeListV2.add(3, true);
            mazeListV2.add(4, true);
            mazeListV2.add(5, true);

            mazeListV3.add(0, true);
            mazeListV3.add(1, false);
            mazeListV3.add(2, true);
            mazeListV3.add(3, false);
            mazeListV3.add(4, false);
            mazeListV3.add(5, true);

            mazeListV4.add(0, true);
            mazeListV4.add(1, true);
            mazeListV4.add(2, false);
            mazeListV4.add(3, false);
            mazeListV4.add(4, true);
            mazeListV4.add(5, true);

        } else if (maze == 2) {
            mazeListH1.add(0, true);
            mazeListH1.add(1, true);
            mazeListH1.add(2, true);
            mazeListH1.add(3, true);
            mazeListH1.add(4, true);

            mazeListH2.add(0, true);
            mazeListH2.add(1, false);
            mazeListH2.add(2, true);
            mazeListH2.add(3, true);
            mazeListH2.add(4, false);

            mazeListH3.add(0, false);
            mazeListH3.add(1, true);
            mazeListH3.add(2, true);
            mazeListH3.add(3, false);
            mazeListH3.add(4, true);

            mazeListH4.add(0, true);
            mazeListH4.add(1, true);
            mazeListH4.add(2, false);
            mazeListH4.add(3, true);
            mazeListH4.add(4, false);

            mazeListH5.add(0, true);
            mazeListH5.add(1, true);
            mazeListH5.add(2, true);
            mazeListH5.add(3, true);
            mazeListH5.add(4, true);

            mazeListV1.add(0, true);
            mazeListV1.add(1, false);
            mazeListV1.add(2, false);
            mazeListV1.add(3, false);
            mazeListV1.add(4, false);
            mazeListV1.add(5, true);

            mazeListV2.add(0, true);
            mazeListV2.add(1, false);
            mazeListV2.add(2, false);
            mazeListV2.add(3, false);
            mazeListV2.add(4, false);
            mazeListV2.add(5, true);

            mazeListV3.add(0, true);
            mazeListV3.add(1, false);
            mazeListV3.add(2, false);
            mazeListV3.add(3, false);
            mazeListV3.add(4, false);
            mazeListV3.add(5, true);

            mazeListV4.add(0, false);
            mazeListV4.add(1, false);
            mazeListV4.add(2, false);
            mazeListV4.add(3, false);
            mazeListV4.add(4, false);
            mazeListV4.add(5, true);

        } else if (maze == 3) {
            mazeListH1.add(0, true);
            mazeListH1.add(1, true);
            mazeListH1.add(2, true);
            mazeListH1.add(3, true);
            mazeListH1.add(4, false);

            mazeListH2.add(0, true);
            mazeListH2.add(1, false);
            mazeListH2.add(2, false);
            mazeListH2.add(3, true);
            mazeListH2.add(4, false);

            mazeListH3.add(0, false);
            mazeListH3.add(1, true);
            mazeListH3.add(2, false);
            mazeListH3.add(3, false);
            mazeListH3.add(4, true);

            mazeListH4.add(0, true);
            mazeListH4.add(1, true);
            mazeListH4.add(2, false);
            mazeListH4.add(3, true);
            mazeListH4.add(4, false);

            mazeListH5.add(0, true);
            mazeListH5.add(1, true);
            mazeListH5.add(2, true);
            mazeListH5.add(3, true);
            mazeListH5.add(4, true);

            mazeListV1.add(0, true);
            mazeListV1.add(1, false);
            mazeListV1.add(2, true);
            mazeListV1.add(3, false);
            mazeListV1.add(4, true);
            mazeListV1.add(5, true);

            mazeListV2.add(0, true);
            mazeListV2.add(1, false);
            mazeListV2.add(2, false);
            mazeListV2.add(3, true);
            mazeListV2.add(4, false);
            mazeListV2.add(5, true);

            mazeListV3.add(0, true);
            mazeListV3.add(1, false);
            mazeListV3.add(2, true);
            mazeListV3.add(3, false);
            mazeListV3.add(4, false);
            mazeListV3.add(5, true);

            mazeListV4.add(0, true);
            mazeListV4.add(1, false);
            mazeListV4.add(2, false);
            mazeListV4.add(3, false);
            mazeListV4.add(4, false);
            mazeListV4.add(5, true);

        } else if (maze == 4) {
            mazeListH1.add(0, true);
            mazeListH1.add(1, true);
            mazeListH1.add(2, true);
            mazeListH1.add(3, true);
            mazeListH1.add(4, true);

            mazeListH2.add(0, true);
            mazeListH2.add(1, false);
            mazeListH2.add(2, false);
            mazeListH2.add(3, false);
            mazeListH2.add(4, true);

            mazeListH3.add(0, true);
            mazeListH3.add(1, false);
            mazeListH3.add(2, false);
            mazeListH3.add(3, false);
            mazeListH3.add(4, true);

            mazeListH4.add(0, true);
            mazeListH4.add(1, false);
            mazeListH4.add(2, false);
            mazeListH4.add(3, false);
            mazeListH4.add(4, true);

            mazeListH5.add(0, true);
            mazeListH5.add(1, true);
            mazeListH5.add(2, true);
            mazeListH5.add(3, true);
            mazeListH5.add(4, true);

            mazeListV1.add(0, true);
            mazeListV1.add(1, false);
            mazeListV1.add(2, true);
            mazeListV1.add(3, true);
            mazeListV1.add(4, false);
            mazeListV1.add(5, true);

            mazeListV2.add(0, true);
            mazeListV2.add(1, false);
            mazeListV2.add(2, true);
            mazeListV2.add(3, false);
            mazeListV2.add(4, false);
            mazeListV2.add(5, true);

            mazeListV3.add(0, true);
            mazeListV3.add(1, false);
            mazeListV3.add(2, false);
            mazeListV3.add(3, true);
            mazeListV3.add(4, false);
            mazeListV3.add(5, true);

            mazeListV4.add(0, true);
            mazeListV4.add(1, false);
            mazeListV4.add(2, true);
            mazeListV4.add(3, true);
            mazeListV4.add(4, false);
            mazeListV4.add(5, false);

        } else {
            mazeListH1.add(0, true);
            mazeListH1.add(1, true);
            mazeListH1.add(2, true);
            mazeListH1.add(3, true);
            mazeListH1.add(4, true);

            mazeListH2.add(0, false);
            mazeListH2.add(1, false);
            mazeListH2.add(2, false);
            mazeListH2.add(3, false);
            mazeListH2.add(4, false);

            mazeListH3.add(0, false);
            mazeListH3.add(1, false);
            mazeListH3.add(2, false);
            mazeListH3.add(3, false);
            mazeListH3.add(4, false);

            mazeListH4.add(0, false);
            mazeListH4.add(1, false);
            mazeListH4.add(2, false);
            mazeListH4.add(3, false);
            mazeListH4.add(4, false);

            mazeListH5.add(0, true);
            mazeListH5.add(1, true);
            mazeListH5.add(2, true);
            mazeListH5.add(3, true);
            mazeListH5.add(4, true);

            mazeListV1.add(0, true);
            mazeListV1.add(1, false);
            mazeListV1.add(2, false);
            mazeListV1.add(3, false);
            mazeListV1.add(4, false);
            mazeListV1.add(5, true);

            mazeListV2.add(0, true);
            mazeListV2.add(1, false);
            mazeListV2.add(2, false);
            mazeListV2.add(3, false);
            mazeListV2.add(4, false);
            mazeListV2.add(5, true);

            mazeListV3.add(0, true);
            mazeListV3.add(1, false);
            mazeListV3.add(2, false);
            mazeListV3.add(3, false);
            mazeListV3.add(4, false);
            mazeListV3.add(5, true);

            mazeListV4.add(0, true);
            mazeListV4.add(1, false);
            mazeListV4.add(2, false);
            mazeListV4.add(3, false);
            mazeListV4.add(4, false);
            mazeListV4.add(5, true);
        }
        generateMaze();
    }
}
