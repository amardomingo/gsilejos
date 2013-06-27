package es.upm.dit.gsi.gsilejos.simbad.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simbad.demo.DemoManager;
import simbad.gui.AgentInspector;
import simbad.gui.WorldWindow;
import simbad.sim.Agent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.SimpleAgent;
import simbad.sim.Simulator;
import simbad.sim.World;
//import javax.swing.UIManager;

public class Simbad extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    static final String version="1.4";
    static int SIZEX = 740;
    static int SIZEY = 660;
    JMenuBar menubar;
    JDesktopPane desktop;
    WorldWindow worldWindow=null;
    ControlWindow controlWindow=null;
    World world;
    Simulator simulator;
    Console console=null;
    AgentInspector agentInspector=null;
    boolean backgroundMode;

    static  Simbad simbadInstance=null;

    public Simbad(EnvironmentDescription ed, boolean backgroundMode) {
        super("Simbad  - version "+ version);
        simbadInstance = this;
        this.backgroundMode = backgroundMode;
        desktop = new JDesktopPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZEX, SIZEY);
        createGUI();
        start(ed);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGUI() {
        desktop.setFocusable(true);
        getContentPane().add(desktop);
        menubar = new JMenuBar();
        menubar.add(DemoManager.createMenu(this));
        //setJMenuBar(menubar);
    }

    private void start(EnvironmentDescription ed){
        System.out.println("Starting environmentDescription: "+ed.getClass().getName());
        world = new World(ed);
        simulator = new Simulator(desktop, world, ed);
        createInternalFrames();
        if (backgroundMode) {
            runBackgroundMode();
        }
    }

    public void startSimbad(String viewPoint, boolean auto) {
        AgentFollower agentFollower;
        agentFollower = new AgentFollower(world, (SimpleAgent)simulator.getAgentList().get(0));
        if (viewPoint.equals("topview")) {
            agentFollower.suspend();
            world.changeViewPoint(World.VIEW_FROM_TOP, null);
        } else if (viewPoint.equals("eastview")) {
            agentFollower.suspend();
            world.changeViewPoint(World.VIEW_FROM_EAST, null);
        } else if (viewPoint.equals("followfar")) {
            agentFollower.setViewPointType(World.VIEW_ABOVE_AGENT);
            agentFollower.resume();
        } else if (viewPoint.equals("follownear")) {
            agentFollower.setViewPointType(World.VIEW_ABOVE_AGENT_NEAR);
            agentFollower.resume();
        } else if (viewPoint.equals("followside")) {
            agentFollower.setViewPointType(World.VIEW_AGENT_SIDE);
            agentFollower.resume();
        }
        if (auto) simulator.startSimulation();
    }

    private void releaseRessources(){
        simulator.dispose();
        world.dispose();
        disposeInternalFrames();
    }

    private void createInternalFrames() {
        worldWindow = new WorldWindow(world);
        desktop.add(worldWindow);
        this.setResizable(true);
        worldWindow.show();
        worldWindow.setLocation(20, 20);
        worldWindow.setSize(680, 440);
        worldWindow.setTitle("LEGO NXT LSIN");
        worldWindow.setResizable(true);
        //agentInspector = createAgentInspector(simulator, 20, 20);
        if (!backgroundMode){
        controlWindow = new ControlWindow(world, simulator);
        desktop.add(controlWindow);
        controlWindow.show();
        controlWindow.setSize(680, 140);
        controlWindow.setLocation(20, 465);
        controlWindow.setResizable(true);
        }
    }

    private void disposeInternalFrames() {
        simulator.dispose();
        worldWindow.dispose();
        if (agentInspector != null) agentInspector.dispose();
        if (controlWindow != null){
            controlWindow.dispose();
        }
    }

    private AgentInspector createAgentInspector(Simulator simulator, int x,
            int y) {
        ArrayList agents = simulator.getAgentList();
        SimpleAgent a = ((SimpleAgent) agents.get(0));
        if (a instanceof Agent) {
            AgentInspector ai = new AgentInspector((Agent) a, !backgroundMode,simulator);
            desktop.add(ai);
            ai.show();
            ai.setLocation(x, y);
            return ai;
        } else {
            return null;
        }
    }

    public void actionPerformed(ActionEvent event) {
       if (event.getActionCommand()=="demo"){
            releaseRessources();
            start( DemoManager.getDemoFromActionEvent(event));
       }
    }

    private void runBackgroundMode(){
        //TODO pb with collision , pb with camera in this mode.
        setTitle(this.getTitle()+" - Background Mode");
        System.out.println("---------------------------------------------");
        System.out.println("Simbad is running in 'Background Mode");
        System.out.println("World is rendered very rarely. UI is disabled");
        System.out.println("--------------------------------------------");
        // slow down
        agentInspector.setFramesPerSecond(0.5f);
        // Show a small indication window
        JInternalFrame frame = new JInternalFrame();
        JPanel p = new JPanel();
        p.add(new JLabel("BACKGROUND MODE"));
        frame.setContentPane(p); frame.setClosable(false);
        frame.pack();frame.setLocation(SIZEX/2,SIZEY*3/4);
        desktop.add(frame);
        frame.show();
        world.changeViewPoint(World.VIEW_FROM_TOP,null);
        // start
        simulator.startBackgroundMode();
    }

    public static void main(String[] args) {
        // process options
        boolean backgroundMode = false;
        for (int i = 0; i < args.length;i++)
        {
            if ("-bg".compareTo(args[i])==0) backgroundMode = true;
        }
        // Check java3d presence
        try {
            Class.forName("javax.media.j3d.VirtualUniverse");
        } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(null,  "Simbad requires Java 3D", "Simbad 3D",JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        //request antialising
        System.setProperty("j3d.implicitAntialiasing", "true");

        new Simbad(new simbad.demo.BaseDemo() ,backgroundMode);
     }

    public JDesktopPane getDesktopPane() {
        return desktop;
    }

    public static Simbad getSimbadInstance() {
        return simbadInstance;
    }

}
