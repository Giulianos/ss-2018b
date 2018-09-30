package ss.g3;

import ss.g3.integrators.*;
import ss.g3.types.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Double mass = null;
    private static Double k = null;
    private static Double gamma = null;
    private static Double tf = null;
    private static Double x0 = null;
    private static Double y0 = null;
    private static Double vx0 = null;
    private static Double vy0 = null;
    private static Double dt = null;
    private static Integrator integrator = null;
    private static Boolean error = null;

    public static void parse(String argsFile) throws IOException {
        FileReader input = new FileReader(argsFile);
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        int i = 0;
        while ( (myLine = bufRead.readLine()) != null)
        {
            String[] aux = myLine.toLowerCase().split(" = ");
            switch (aux[0]){
                case "m" : mass = Double.parseDouble(aux[1]); break;
                case "k" : k = Double.parseDouble(aux[1]); break;
                case "gamma" : gamma = Double.parseDouble(aux[1]); break;
                case "tiempo final" : tf = Double.parseDouble(aux[1]); break;
                case "x0" : x0 = Double.parseDouble(aux[1]); break;
                case "y0" : y0 = Double.parseDouble(aux[1]); break;
                case "vx0" : vx0 = Double.parseDouble(aux[1]); break;
                case "vy0" : vy0 = Double.parseDouble(aux[1]); break;
                case "dt" : dt = Double.parseDouble(aux[1]); break;
                case "integrator" : integrator = getIntegrator(aux[1]) ; break;
                case "error" : error = Boolean.parseBoolean(aux[1]); break;
                default:
                    throw new RuntimeException("NOT VALID INPUT IN PARAMS.TXT");
            }
            i++;
        }

        if(i<11){
            throw new RuntimeException("THERE ARE PARAMETERS MISSING");
        }
    }

    private static Integrator getIntegrator(String integrator) {
        switch (integrator){
            case "verlet":
                return new Verlet();
            case "beeman":
                return new Beeman();
            case "gear":
                return new GearPredictorCorrector();
            case "real":
                return new Real();
            case "euler":
                return new Euler();
                default:
                    return null;
        }
    }

    public static void main(String[] args) throws IOException {


        if(args[0].equals("space")) {
            Space space = new Space();
            space.simulateSpace(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            return;
        }

        parse(args[0]);
        Spring spring = new Spring(mass,k,gamma,x0,y0,vx0,vy0);

        if(error){
            spring.simulateError(dt,tf,integrator);
        }else{
            spring.simulation(dt,tf,integrator);
        }
    }
}
