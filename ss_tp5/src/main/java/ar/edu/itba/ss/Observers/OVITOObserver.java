package ar.edu.itba.ss.Observers;

import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Particles.Body;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class OVITOObserver implements SpaceObserver {
    private BufferedWriter writer;
    private Double totalTime;

    private Set<Body> bodies;
    private Container container;
    private Double time;

    private Long progress = 0l;

    public OVITOObserver(String filename, Double totalTime) throws IOException{
        this.writer = new BufferedWriter(new FileWriter(filename));
        this.totalTime = totalTime;
    }

    public void closeFile() throws IOException {
        writer.close();
    }

    @Override
    public void injectData(Set<Body> bodies, Container container, Double time) {
        this.bodies = bodies;
        this.container = container;
        this.time = time;
    }

    @Override
    public void observe() throws IOException {
        writer.write(bodies.size() - 2 + "\n\n");
        for(Body b : bodies) {
            if(!b.isFixed()) {
                writer.write(b + "\n");
            }
        }
    }

    @Override
    public Boolean simulationMustEnd() {
        if(progress != Math.round((time/totalTime) * 100)) {
            progress = Math.round((time/totalTime) * 100);
            System.out.println("Progress: " + progress + "%");
        }
        if(time >= totalTime) {
            return true;
        }
        return false;
    }
}
