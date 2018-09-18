package ss.g3;
import ss.g3.integrators.Verlet;

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

    public static void parse() throws IOException {
        FileReader input = new FileReader("/Users/florenciacavallin/Documents/4rto-1cuatri/SS/tp/ss/ss_tp4/params.txt");
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
        parse();
        Spring spring = new Spring(mass,k,gamma,x0,y0,vx0,vy0);

//        spring.simulateVerlet(0.001, 5.0);
        spring.simulateError(new Verlet(),dt, tf);
    }
}
