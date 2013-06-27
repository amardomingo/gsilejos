package es.upm.dit.gsi.gsilejos.lejos.nxt;

public class SensorPort {

    public static final SensorPort S1 = new SensorPort(0);
    public static final SensorPort S2 = new SensorPort(1);
    public static final SensorPort S3 = new SensorPort(2);
    public static final SensorPort S4 = new SensorPort(3);
    public static final SensorPort[] PORTS = {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4};

    protected SensorPort(int aId) {}

}


