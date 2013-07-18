package es.upm.dit.gsi.gsilejos.lejos.robotics;

public interface RegulatedMotorListener {


    public void rotationStarted(RegulatedMotor motor, int tachoCount,
            boolean stalled, long timeStamp);
    
    public void rotationStopped(RegulatedMotor motor, int tachoCount,
            boolean stalled, long timeStamp);
    
}
