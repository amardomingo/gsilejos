package es.upm.dit.gsi.gsilejos.simbad.gui;

import simbad.sim.*;

class AgentFollower implements Runnable {

    World world;
    SimpleAgent agent;
    Thread thread;
    boolean stopped;
    boolean changed;
    int viewPointType;

    AgentFollower(World world, SimpleAgent agent) {
        this.agent = agent;
        this.world = world;
        viewPointType = World.VIEW_ABOVE_AGENT;
        stopped = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            changed= false;
            if (!stopped)
            			world.changeViewPoint(viewPointType, agent);
            try {
                for (int i=0;i<30;i++){
                    Thread.sleep(100);
                    if (changed) break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void setViewPointType(int type){
        viewPointType = type;
    }
    protected void suspend() {
        stopped = true;
        changed= true;
    }

    protected void resume() {
        stopped = false;
        changed= true;
    }

}