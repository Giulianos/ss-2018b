package ar.edu.itba.ss;

import ar.edu.itba.ss.Observers.SpaceObserver;

public class Simulator {
    private Space space;
    private SpaceObserver observer;
    private Double timeStep;

    public Simulator(Space space, SpaceObserver observer, Double timeStep) {
        this.space = space;
        this.observer = observer;
        this.timeStep = timeStep;
    }

    public void simulate() throws Exception {
        while(!observer.simulationMustEnd()) {
           space.simulationStep(timeStep);
           observer.observe();
        }
    }
}
