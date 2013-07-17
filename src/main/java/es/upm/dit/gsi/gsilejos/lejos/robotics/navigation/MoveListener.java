package es.upm.dit.gsi.gsilejos.lejos.robotics.navigation;

public interface MoveListener {
	
	/**
	 * Called when a Move Provider starts a move
	 *  
	 * @param event the movement
	 * @param mp the movement provider
	 */
	public void moveStarted(Move event, MoveProvider mp);
	
	/**
	 * Called by the movement provider when a move stops
	 * 
	 * @param event the movement
	 * @param mp movement provider
	 */
	public void moveStopped(Move event, MoveProvider mp);
}