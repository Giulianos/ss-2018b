package ss.g3;

import ss.g3.integrators.Beeman;
import ss.g3.integrators.Verlet;

public class Main {

    public static void main(String[] args) {
        Spring spring = new Spring();

//        spring.simulateVerlet(0.001, 5.0);
        spring.simulateError(new Verlet(),0.001, 5.0);
    }
}
