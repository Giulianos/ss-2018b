package ss.g3;

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
                default:
                    System.out.println("NOT VALID INPUT IN PARAMS.TXT");
            }
            i++;
        }

        if(i<9){
            System.out.println("THERE ARE PARAMETERS MISSING");
        }
    }

    public static void main(String[] args) throws IOException {
        Space space = new Space();
        if(args[0].equals("space")) {
            space.simulateSpace(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            return;
        }

        parse(args[0]);
        Spring spring = new Spring(mass,k,gamma,x0,y0,vx0,vy0);

        switch(args[1]) {
            case "verlet":
                spring.simulateVerlet(dt, tf);
                break;
            case "beeman":
                spring.simulateBeeman(dt, tf);
                break;
            case "gear-predictor":
                spring.simulateGearPredictorCorrector(dt, tf);
                break;
            case "euler":
                spring.simulateEuler(dt, tf);
                break;
            default:
                System.out.println("INVALID/UNSPECIFIED INTEGRATION METHOD");
        }
        //spring.simulateError(new Verlet(),dt, tf);
    }
}
