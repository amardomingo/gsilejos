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
     * @param wheelDiameter El simulador no la utiliza.
     * @param trackWidth El simulador no la utiliza.
     * @param leftMotor Motor de la izquierda.
     * @param rightMotor Motor de la derecha.
     */
    public DifferentialPilot(final float wheelDiameter, final float trackWidth,
            final RegulatedMotor leftMotor, final RegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * Método que configura e inicializa Simbad.
     *
     * @param x,y 'int' que marca la posición inicial del robot de Simbad.
     * @param waitTime 'int' tiempo de espera entre medidas de los sensores
     * @param sensor 'UltrasonicSensor' que utilizará el piloto.
     * @param realism para activar variables aleatorias en movimientos
     * @param maze para elegir laberinto
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
     * Crea el robot que manejará el simulador.
     *
     * @param posicion 'Vec
     */
    private void setPiloto(Vector3d posicion, String nombre) {
        this.robot = new Robot(posicion, nombre);
    }

    /**
     * Setter.
     * Configura los motores estableciendo sus direcciones y el piloto al que
     * pertenecen.
     */
    private void setMotores() {
        this.leftMotor.setMotor(this, (byte) 1);
        this.rightMotor.setMotor(this, (byte) 2);
    }

    /**
     * Genera el laberinto a partir del array de paredes.
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
     * Método que auto calibra simbad, estableciendo el valor de la variable
     * 'waitTime' en función de las pruebas que realiza.
     */
    public void calibrarSimbad() {
        int toWaitTime = this.toWaitTime;
        calibrarSimbad(toWaitTime);
    }

    /**
     * Método que calibra simbad, estableciendo el valor de la variable 'waitTime'
     * al valor del argumento.
     *
     * @param toWaitTime Valor que tendrá la variable 'waitTime'
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
     * Establece la velocidad de traslación del piloto.
     *
     * @param speed Velocidad a establecer en el piloto.
     */
    /*public void setSpeed(final int speed) {
        this.speed = speed / 10;
        this.robot.setTranslationalVelocity(this.speed);
    }*/

    /**
     * Setter.
     * Establece la velocidad de traslación o rotación del piloto, en función
     * del argumento 'byte'.
     * En caso de que sea cero(0), modifica la velocidad de traslación, y en caso
     * de que sea uno(1), modifica la de rotación.
     *
     * @param speed Velocidad a establecer.
     * @param tipo Tipo de velocidad.
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
     * Establece la velocidad de traslación del piloto.
     *
     * @param speed Velocidad a establecer.
     */
    public void setTravelSpeed(double speed) {
        this.setSpeed(speed, (byte) 0);
    }
    
    public void setRotateSpeed(double speed) {
        this.setSpeed(speed, (byte) 1);
    }

    /**
     * Getter.
     * Devuelve la velocidad de traslación del piloto.
     */
    public float getTravelSpeed() {
        return (float) this.speed;
    }

    /**
     * Método que establece la velocidad de traslación del piloto.
     */
    public void forward() {
        this.robot.setTranslationalVelocity(speed);
    }

    /**
     * Método que establece la velocidad de traslación del piloto, inversa a
     * 'speed'.
     */
    public void backward() {
        this.robot.setTranslationalVelocity((-1) * speed);
    }

    /**
     * Pasa el ángulo a radianes y rota el robot.
     *
     * @param angle Ángulo a rotar, en grados.
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
     * Ejecuta el método rotate(int).
     * No utiliza el parámetro boolean.
     *
     * @param angle Ángulo a rotar.
     * @param immediateReturn No se utiliza.
     */
    public void rotate(double angle, boolean immediateReturn) {
        this.rotate(angle);
    }
    
    /**
     * Gira 90º a la izquierda
     * 
     */
    public void rotateLeft() {
    	this.rotate(90);
    }
    
    /**
     * Gira 90º a la derecha
     * 
     */
    public void rotateRight() {
    	this.rotate(-90);
    }

    /**
     * Establece la velocidad de traslación a cero(0), y la velocidad de rotación
     * a la que se le pasa como parámetro.
     *
     * @param velocidad Velocidad de rotación.
     */
    /*public void rotate(double velocidad) {
        this.robot.setTranslationalVelocity(0);
        this.robot.setRotationalVelocity(velocidad);
    }*/

    /**
     * Getter.
     * Devuelve 'true' si el robot se encuentra rotando.
     *
     * @return Si el piloto está en rotación.
     */
    /*
    public boolean getRotacionMotor() {
        return this.rotacionMotor;
    }*/

    /**
     * Setter.
     * Establece el estado de rotación del piloto.
     */
    /*public void setRotacionMotor(boolean estado) {
        this.rotacionMotor = estado;
    }*/

    /**
     * Para el robot, poniendo las velocidades de traslación y rotación a  cero(0).
     */
    public void stop() {
        this.robot.setTranslationalVelocity(0);
        this.robot.setRotationalVelocity(0);
    }
    
    /**
     * Para el robot, poniendo las velocidades de traslación y rotación a  cero(0).
     * 
     * En el simulador, no hay diferencia con stop()
     */
    public void quickStop() {
    	stop();
    }

    /**
     * Pone a cero(0) una velocidad determinada, en función del argumento.
     *
     * @param velocidad Discrimina entre Traslación(0) y Rotación(1).
     */
    /*public void stop(byte velocidad) {
        if (velocidad == 0) {
            this.robot.setTranslationalVelocity(0);
        } else {
            this.robot.setRotationalVelocity(0);
        }
    }*/

    /**
     * Devuelve 'true' si alguna de las velocidades del robot es distinta de cero(0).
     *
     * @return Si el piloto está en movimiento o no.
     */
    public boolean isMoving() {
        boolean isMoving;
        if (this.robot.getTranslationalVelocity() != 0 || this.robot.getRotationalVelocity() != 0) {
            isMoving = true;
        } else {
            isMoving = false;
        }
        return isMoving;
    }

    /**
     * Devuelve 'true' si la velocidad que se le ha pasado como parámetro es
     * distinta de cero(0).
     *
     * @return Si el piloto está o no en movimiento.
     */
    /*public double isMoving(byte velocidad) {
        if (velocidad == 0) {
            return this.robot.getTranslationalVelocity();
        } else {
            return this.robot.getRotationalVelocity();
        }
    }*/

    /**
     * Getter.
     *
     * @return Distancia recorrida
     */
    /*public float getTravelDistance() {
        float odometro = (float) this.robot.getOdometer() * 1000;
        return odometro;
    }*/

    /**
     * Desplaza el piloto una distancia determinada.
     *
     * @param distance Distancia que se desplazará el piloto.
     */
    public void travel(double distance) {
        double inicio = this.robot.getOdometer();
        boolean noProblem = true; //Para evitar bloqueos contra paredes
        float travelTemp = 0;
        int count = 0;

        //Variable aleatoria para añadir realismo al avance
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

            //Para evitar que se quede bloqueado contra un obstáculo al avanzar
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
     * Ejecuta el método 'travel(final float)'.
     *
     * @param distance Distancia que se desplazará el piloto.
     * @param immediateReturn No se utiliza.
     */
    public void travel(double distance, boolean immediateReturn) {
        this.travel(distance);
    }

    /**
     * Método utilizado para las esperas.
     *
     * @param n Milisegundos a esperar.
     */
    private static void wait(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while (t1 - t0 < n);
    }

    /////////////////////////////
    // METODOS SIN IMPLEMENTAR //
    /////////////////////////////
    public DifferentialPilot(final float wheelDiameter, final float trackWidth,
            final Motor leftMotor, final Motor rightMotor,
            final boolean reverse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public DifferentialPilot(final float leftWheelDiameter,
            final float rightWheelDiameter, final float trackWidth,
            final Motor leftMotor, final Motor rightMotor,
            final boolean reverse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void addMoveListener(MoveListener m) {
    	throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public boolean isStalled() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    protected void movementStart(boolean alert) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }	

    public void setSpeed(final int leftSpeed, final int rightSpeed)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void setAcceleration(int acceleration) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void setMinRadius(double radius) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public float getMaxTravelSpeed() throws UnsupportedOperationException {
        // Simbad no está limitado por la velocidad
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public double getRotateSpeed() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public float getMaxRotateSpeed() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public double getRotateMaxSpeed() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public double getMinRadiuos() throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public Move getMovement() throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public float getAngleIncrement() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void reset() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void steer(double turnRate)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void steerBackward(double turnRate)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void steer(double turnRate, double angle)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void steer(double turnRate, double angle, boolean immediateReturn)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void rotationStarted(RegulatedMotor motor, int tachoCount, boolean stall, long ts)
    		throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void	rotationStopped(RegulatedMotor motor, int tachoCount, boolean stall, long ts)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    //public void arc(final double radius) throws UnsupportedOperationException {
    //    throw new UnsupportedOperationException("Este método no está implementado.");
    //}

    public void arc(final double radius, final double angle)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void arc(final double radius, final double angle,
            final boolean immediateReturn) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void arcBackward(final double radius)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void travelArc(double radius, double distance) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void travelArc(double radius, double distance, boolean immediateReturn)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    /**
     * Laberintos
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
