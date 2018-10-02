package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Spring {
    private static Container box;
    private static Space space;
    private static Vector radiusInterval;
    private static int N;
    private static double mass;
    private static double tf;
    private static double dt;

    public Spring( Container container,double gravity,double min, double max, int N, double mass, double dt, double friction, double kn, double tf) {
        this.radiusInterval = new Vector(min,max);
        this.N = N;
        this.mass = mass;
        this.tf = tf;
        this.dt = dt;
        this.box = container;
        Set<Body> bodies = initParticles();
        this.space = new Space(container,bodies,gravity,dt,friction,kn);
        simulation();
    }
    // generar algun algoritmo para llenar el contenedor de forma mas eficiente y rapida
    public Set<Body> initParticles(){

        Set<Body> bodies = new HashSet<>();

        Random r = new Random();
        while(bodies.size() < N){
            Vector position = new Vector(r.nextDouble(),r.nextDouble()*3);
            Vector velocity = new Vector(0.0,0.0);
            double radius = r.nextDouble()*(radiusInterval.y-radiusInterval.x)+radiusInterval.x;
            Body body = new Body(position,velocity,radius,mass);
            if(box.touchesWall(body)==null && !Body.bodyInTouch(bodies,body)) {
                bodies.add(body);
            }
        }

       return bodies;
    }

    public void simulation(){
        BufferedWriter writer=initalizeBW("/Users/florenciacavallin/Documents/4rto-2cuatri/SS/ss/ss_tp5/simulation/sim_"+LocalDateTime.now());
        appendToFile(writer,Integer.toString(space.getBodies().size())+"\n"+"\n");
        double time = 0.0;
        while(time < tf){
            space.bruteForce();
            printBodies(writer);
            time += dt;
        }
        closeBW(writer);
    }

    public void printBodies(BufferedWriter writer){

        Set<Body> bodies = space.getBodies();

        for(Body body: bodies){
            System.out.println(bodies);
            appendToFile(writer,body.toString());
        }
    }

    private BufferedWriter initalizeBW(String outPath) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(outPath+".txt", true));
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return bw;
    }

    private void closeBW(BufferedWriter bw) {
        if (bw != null) try {
            bw.flush();
            bw.close();
        } catch (IOException ioe2) {
            // just ignore it
        }
    }

    public static void appendToFile (BufferedWriter bw , String data) {
        try {
            bw.write(data);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}