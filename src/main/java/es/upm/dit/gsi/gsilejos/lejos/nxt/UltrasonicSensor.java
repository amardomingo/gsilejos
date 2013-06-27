package es.upm.dit.gsi.gsilejos.lejos.nxt;

import simbad.sim.RangeSensorBelt;
import simbad.sim.Robot;
import simbad.sim.RobotFactory;

public class UltrasonicSensor {

    private Robot robot;
    private RangeSensorBelt sonars;
    private SensorPort sensorPort;

    int waitTime = 250;


    ///////////////////////////
    // METODOS IMPLEMENTADOS //
    ///////////////////////////

    /**
     * Constructor.
     */
    public UltrasonicSensor(SensorPort port) {
        this.sensorPort = port;
    }

    /**
     * Setter.
     * Método que configura el Sensor, estableciendo a qué Robot pertenece.
     *
     * @param robot Robot al que se le añade el sensor.
     */
    public void setSensor(Robot robot) {
        this.robot = robot;
        this.sonars = RobotFactory.addSonarBeltSensor(this.robot, 4);
    }

    /**
     * Setter.
     * Método que establece el tiempo de espera entre medidas.
     *
     * @param waitTime Tiempo de espera.
     */
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * Getter.
     * Método que devuelve la distancia del sonar.
     *
     * @return distance Distancia al primer obstáculo.
     */
    public int getDistance() {
        wait(this.waitTime); // para poder refrescar el sonar
        int dist;
        int port = 0;
        if(this.sensorPort.equals(SensorPort.S1)) port = 1;
        if(this.sensorPort.equals(SensorPort.S2)) port = 3;
        if(this.sensorPort.equals(SensorPort.S3)) port = 0;
        if((dist = (int)(this.sonars.getMeasurement(port)*100/4)) == Integer.MAX_VALUE) {
            return 255;
        } else {
            return dist;
        }
    }

    /**
     * Getter.
     *
     * @return El objeto sonar.
     */
    private RangeSensorBelt getSensor() {
        return this.sonars;
    }

    private static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do {
            t1=System.currentTimeMillis();
        } while (t1-t0<n);
    }



    //////////////////////////////
    // METODOS NO IMPLEMENTADOS //
    //////////////////////////////

    public float getRange() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int getData(int register, byte [] buf, int len)
                       throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int sendData(int register, byte [] buf, int len)
                        throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
	
    public int getDistances(int dist[]) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public int ping() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int continuous() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int off() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int capture() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int reset() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public int getFactoryData(byte data[]) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public String getUnits() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int getCalibrationData(byte data[]) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
	
    public int setCalibrationData(byte data[]) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
	
    public byte getContinuousInterval() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
	
    public int setContinuousInterval(byte interval) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public byte getMode() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
}