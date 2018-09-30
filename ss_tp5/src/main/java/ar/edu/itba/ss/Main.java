package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Box;
import ar.edu.itba.ss.Containers.Container;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Double diameter, width, hight, friction, minRadius, maxRadius, gravity, mass, kn, kt, dt;
    private static int N, tf;
    private static Spring spring;

    public static void main( String[] args ) throws IOException {
        parse(args[0]);
        Container box = new Box(friction,diameter,hight,width);
        spring = new Spring(box,gravity,minRadius,maxRadius,N,mass);
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
                case "hight" : hight = Double.parseDouble(aux[1]); break;
                case "friction" : friction = Double.parseDouble(aux[1]); break;
                case "minradius" : minRadius = Double.parseDouble(aux[1]); break;
                case "maxradius" : maxRadius = Double.parseDouble(aux[1]); break;
                case "n" : N = Integer.parseInt(aux[1]); break;
                case "gravity" : gravity = Double.parseDouble(aux[1]); break;
                case "mass" : mass = Double.parseDouble(aux[1]); break;
                case "kn" :
                    kn = Double.parseDouble(aux[1]);
                    kt = 2 * kn;
                    double m = (minRadius+maxRadius)/2;
                    // dt es el paso de la simulación
                    dt = 0.1 * Math.sqrt(m/kn);
                    break;
                case "tf" : tf = Integer.parseInt(aux[1]); break;
                default:
                    throw new RuntimeException("NOT VALID INPUT IN PARAMS.TXT");
            }
            i++;
        }

        if(i<11){
            throw new RuntimeException("THERE ARE PARAMETERS MISSING");
        }
    }


}
