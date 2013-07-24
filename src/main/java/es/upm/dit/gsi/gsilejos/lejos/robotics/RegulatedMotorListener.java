package es.upm.dit.gsi.gsilejos.lejos.robotics;


/**
 * 
 * Interface for the Listener.
 * Just a placeholder, does nothing.
 * 
 * @author amardomingo
 *
 */
public interface RegulatedMotorListener {


    public void rotationStarted(RegulatedMotor motor, int tachoCount,
            boolean stalled, long timeStamp);
    
    public void rotationStopped(RegulatedMotor motor, int tachoCount,
            boolean stalled, long timeStamp);
    
}
