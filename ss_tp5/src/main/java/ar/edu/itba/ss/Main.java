package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Box;
import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Observers.OVITOObserver;
import ar.edu.itba.ss.Observers.SpaceObserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Double diameter, width, height, friction, minRadius, maxRadius, mass, kn, dt, tf;
    private static int N;

    public static void main( String[] args ) throws Exception {
        // Parse arguments
        parse("params.txt");
        Logger.log("Arguments parsed!");

        // Create observer
        SpaceObserver observer = new OVITOObserver("ovito_out/ovito.xyz", tf, 25.0);
        Logger.log("Observer created!");

        // Create space
        Space space = new Space(width, height, diameter, N);
        Logger.log("Space created!");

        // Attach observer to space
        space.attachObserver(observer);
        Logger.log("Observer attached to space!");

        // Create simulator
        Simulator simulator = new Simulator(space, observer, dt);
        Logger.log("Simulator created!");

        // Simulate
        Logger.log("Simulating...");
        simulator.simulate();

    }

    public static void parse(String argsFile) throws IOException {
        FileReader input = new FileReader(argsFile);
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        int i = 0;
        while ( (myLine = bufRead.readLine()) != null)
        {
            String[] aux = myLine.toLowerCase().split(" = ");
            switch (aux[0]){
                case "diameter" : diameter = Double.parseDouble(aux[1]); break;
                case "width" : width = Double.parseDouble(aux[1]); break;
                case "height" : height = Double.parseDouble(aux[1]); break;
                case "friction" : friction = Double.parseDouble(aux[1]); break;
                case "minradius" : minRadius = Double.parseDouble(aux[1]); break;
                case "maxradius" : maxRadius = Double.parseDouble(aux[1]); break;
                case "n" : N = Integer.parseInt(aux[1]); break;
                case "mass" : mass = Double.parseDouble(aux[1]); break;
                case "kn" :
                    kn = Double.parseDouble(aux[1]);
                    double m = (minRadius+maxRadius)/2;
                    // dt es el paso de la simulación
                    dt = 0.00001;
                    break;
                case "tf" : tf = Double.parseDouble(aux[1]); break;
                default:
                    throw new RuntimeException("NOT VALID INPUT IN PARAMS.TXT");
            }
            i++;
        }

        if(i<10){
            throw new RuntimeException("THERE ARE PARAMETERS MISSING");
        }
    }


}
