package es.upm.dit.gsi.gsilejos.lejos.robotics;

import es.upm.dit.gsi.gsilejos.lejos.robotics.navigation.DifferentialPilot;
import es.upm.dit.gsi.gsilejos.lejos.robotics.navigation.MoveListener;

public class RegulatedMotor {

    private double speed;          // Velocidad
    private byte direction;     // Dirección: 0F 1L 2R
    private boolean moving;     // T or F
    private int anguloRotado;   // Angulo rotado por el motor

    DifferentialPilot piloto;


    ///////////////////////////
    // METODOS IMPLEMENTADOS //
    ///////////////////////////

    /**
     * Constructor.
     * La velocidad por defecto es 2 (en simbad 0.2).
     * La dirección por defecto es 2 (dcha, como establece el algoritmo).
     * El valor de 'move' es cero(0).
     */
    public RegulatedMotor() {
       this.speed = 2;
       this.direction = 2;
       this.moving = false;
    }
    

    /**
     * Setter.
     * Establece el piloto al que pertenece el motor y la dirección del mismo.
     * 
     * Solo para el simulador
     *
     * @param piloto
     * @param direction
     */
    public void setMotor(DifferentialPilot piloto, byte direction) {
        this.piloto = piloto;
        this.direction = direction;
    }

    /**
     * Arranca el motor a la velocidad de la variable 'speed'.
     * En función del valor de 'direction', gira el piloto en un sentido u otro.
     *
     * @throws IllegalStateException Lanza la excepción si el piloto se encuentra
     *                               ya rotando en ese momento.
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
     * Arranca el motor a la velocidad de la variable 'speed'.
     * En función del valor de 'direction', gira el piloto en un sentido u otro.
     * En nuestro caso, es inverso al método 'forward()'
     *
     * @throws IllegalStateException Lanza la excepción si el piloto se encuentra
     *                               ya rotando en ese momento.
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
     * Para el motor y establece como 'false' el estado de rotación del mismo.
     */
    public void stop() {
        //byte motor = 1;
        this.piloto.stop();
        this.moving = false;
        //this.piloto.setRotacionMotor(false);
    }

    /**
     * Devuelve la variable 'move' del objeto.
     *
     * @return moving Si está en movimiento, devuelve 'true'.
     */
    public boolean isMoving() {
        return this.moving;
    }

    /**
     * Rota el piloto a la posición que se le pasa como argumento.
     *
     * @param limitAngle Angulo al que rotará el piloto.
     */
    public void rotateTo(int limitAngle) {
        int anguloActual = this.anguloRotado%360;
        this.rotate(limitAngle-anguloActual);
    }

    /**
     * Ejecuta el método rotateTo(int)
     */
    public void rotateTo(int limitAngle,boolean immediateReturn) {
        this.rotateTo(limitAngle);
    }

    /**
     * Rota el piloto los grados que se le pasan como argumentos.
     *
     * @param angle Angulo que rotará el piloto.
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
     * Ejecuta el método rotate(int)
     */
    public void rotate(int angle, boolean immediateReturn) {
        this.rotate(angle);
    }

    /**
     * Establece el valor de la variable que se le pasa como argumento a la
     * 'speed' del motor.
     *
     * @param speed Entero que representa la velocidad a la que se moverá el piloto.
     */
    public void setSpeed (int speed) {
        this.speed = speed;
    }

    /**
     * Devuelve la velocidad actual de rotación del motor (esté o no en movimiento).
     *
     * @return speed Entero con el valor de la velocidad actual del motor.
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Devuelve el mismo valor de getSpeed().
     *
     * @return speed Entero con el valor de la velocidad actual del motor.
     */
    public double getRotationSpeed() {
        return this.getSpeed();
    }

    /**
     * Getter.
     * Devuelve el ángulo que ha rotado el motor desde su creación.
     *
     * @return Angulo rotado.
     */
    /*public int getAnguloRotado() {
        return this.anguloRotado;
    }*/

   
   
    //////////////////////////////
    // METODOS NO IMPLEMENTADOS //
    //////////////////////////////

    public void addMoveListener(RegulatedMotorListener m) {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public RegulatedMotorListener removeListener() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void flt() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    void updateState( int mode) {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public int getLimitAngle() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public float getMaxSpeed() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public boolean isStalled() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void setStallThreshold(int error, int time){
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public int getTachoCount() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void resetTachoCount() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

    public void setAcceleration() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }
    
    public void waitComplete() {
        throw new UnsupportedOperationException("Este método no está implementado.");
    }

}







