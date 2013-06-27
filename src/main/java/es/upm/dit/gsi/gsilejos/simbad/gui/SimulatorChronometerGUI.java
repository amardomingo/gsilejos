package es.upm.dit.gsi.gsilejos.simbad.gui;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import javax.swing.JPanel;

import javax.swing.border.BevelBorder;
import javax.vecmath.Point3d;
import simbad.sim.SimpleAgent;
import simbad.sim.Simulator;


/**
 * This code is free for any purpose of use. Just Keep this comment block as it is
 * and It will be nice if you give me credit, where you used this (not mandatory.)
 *
 * @author Ashik uddin Ahmad
 * email: ashikcu@gmail.com
 *
 * other contributors:
 * 1. name: enhancement-description
 * 2.
 */

public class SimulatorChronometerGUI extends JPanel implements Runnable {

	private AKDigitalPane p;
	private Thread _thread;
	private boolean isRunning;
	private int mm;
	private int ss;
	private int hh;
	private int xx;

        Simulator simulator;
        SimpleAgent a;

	/**
	 * Create the frame.
	 */
	public SimulatorChronometerGUI(Simulator simulator) {
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Chronometer"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

                this.simulator = simulator;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		p = new AKDigitalPane(8, 1.5f);

		p.setItalic(-10);
		p.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		p.setText("00:00:00");
		p.setFontColor(Color.RED.brighter());
		p.setBackground(Color.BLACK.brighter());
		add(p);

                ArrayList agents = simulator.getAgentList();
                a = ((SimpleAgent) agents.get(0));

                this.startCounter();

	}

        public void startCounter(){
            startThread();
        }
        public void stopCounter(){
            stopThread();
        }
        public void resetCounter(){
            hh = 0;
            mm = 0;
            ss = 0;
            xx = 0;
            refreshDisplay();
            stopThread();
        }

        public void restartCounter(){
            hh = 0;
            mm = 0;
            ss = 0;
            xx = 0;
            refreshDisplay();
            stopThread();
            startThread();
        }

	private void startThread() {
		_thread = new Thread(this);
		isRunning = true;
		_thread.start();
	}

	private void stopThread() {
		isRunning = false;
	}

	private void refreshDisplay() {
		String s = hh + ":";
		s = s + ((mm > 9)? "":"0") + mm + ":";
		s = s + ((ss > 9)? "":"0") + ss + ":";
		s = s + ((xx > 9)? "":"0") + xx;
		p.setText(s);
	}

	//@Override
	public void run() {

		while (isRunning) {
			xx++;
			if (xx >= 100) {
				xx -= 100;
				ss++;
				if (ss >= 60) {
					ss -= 60;
					mm++;
					if (mm >= 60) {
						mm -= 60;
						hh++;
					}
				}
			}
			refreshDisplay();
			try {
				Thread.sleep((long) (10 / simulator.getVirtualTimeFactor()));
                                Point3d p = new Point3d();
                                a.getCoords(p);
                                
                                if(p.getX() >= 2.5){
                                    simulator.stopSimulation();
                                    this.stopCounter();
                                }
                                if(p.getX() <= -2.5){
                                    simulator.stopSimulation();
                                    this.stopCounter();
                                }
                                if(p.getZ() >= 2){
                                    simulator.stopSimulation();
                                    this.stopCounter();
                                }
                                if(p.getZ() <= -2){
                                    simulator.stopSimulation();
                                    this.stopCounter();
                                }
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
                while (!isRunning) {
                    try {
				Thread.sleep(750);
                                p.setFontColor(Color.BLACK.brighter());
                                refreshDisplay();
                                Thread.sleep(250);
                                p.setFontColor(Color.RED.brighter());
                                refreshDisplay();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
                }
	}
}
