package ar.edu.itba.ss;

import ar.edu.itba.ss.CellIndex.Grid;
import ar.edu.itba.ss.Containers.Box;
import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Forces.*;
import ar.edu.itba.ss.Integrators.Beeman;
import ar.edu.itba.ss.Integrators.Integrator;
import ar.edu.itba.ss.Observers.SpaceObserver;
import ar.edu.itba.ss.Particles.Body;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Space {

    // Space domain
    private Container container;
    private Set<Body> bodies;
    private Double elapsedTime = 0.0;

    // Utilities
    private Integrator integrator;
    private SpaceObserver observer;
    private Grid<Body> grid;

    // Concurrency
     ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Space(Double width, Double height, Double diameter, Integer particleQuantity) {
        // Create container
        this.container = new Box(diameter, height, width);

        // Create bodies set
        this.bodies = new HashSet<>();

        // Insert opening edge bodies into set
        this.bodies.addAll(this.container.getOpeningBodies());

        // Insert bodies
        insertBodies(particleQuantity);

        // Create Grid
        this.grid = new Grid<>(0.03);

        // Add particles to grid
        this.grid.injectElements(bodies);

        // Create integrator
        this.integrator = new Beeman();
    }

    public void attachObserver(SpaceObserver observer) {
        this.observer = observer;

        // Inject initial data to observer
        observer.injectData(bodies, container, elapsedTime);
    }

    public void simulationStep(Double dt) throws InterruptedException {
        Long startTime = System.currentTimeMillis();

        List<Callable<Object>> tasks = new ArrayList<>(bodies.size());

        // Update grid before simulating
        this.grid.updateGrid();

        for(Body body : bodies) {
            tasks.add((() -> {
                singularSimulationStep(body, dt);
                return null;
            }));
        }

        executorService.invokeAll(tasks);

        // Update bodies state
        bodies.forEach(Body::update);

        // Update time
        this.elapsedTime += dt;

        // Update observer
        if(observer != null) {
            observer.injectData(bodies, container, elapsedTime);
        }

        // System.out.println("Time taken for 1 step: " + (System.currentTimeMillis()-startTime) + "ms");
    }

    private void singularSimulationStep(Body body, Double dt) {
        // Set dt on particle
        body.setDtBetweenStates(dt);//

        // If the body is fixed, just skip it
        if(body.isFixed()) {
            return;
        }

        Set<Force> appliedForces = new HashSet<>();
        // Check collisions against neighbours
        for(Body neighbour : getNeighbours(body)) {
            // Check if touching
            if(body.touches(neighbour)) {
                appliedForces.add(new ParticleCollisionForce(body, neighbour));
            }
        }

        // Check collisions against walls
        Set<Body> wallCollisions = container.getWallCollision(body);
        if(wallCollisions.size() > 0) {
            wallCollisions.parallelStream()
                    .map(wallBody -> new ParticleCollisionForce(body, wallBody))
                    .forEach(appliedForces::add);
        }

        // Add gravity force
        appliedForces.add(new GForce(body));

        // Sum forces
        Force appliedForce = new SumForce(appliedForces);

        // Integrate
        integrator.calculate(body, dt, appliedForce);

        // Update periodic
        updatePeriodic(body);
    }

    private Set<Body> getNeighbours(Body body) {
        return grid.getNeighbours(body);
        // Set<Body> neighbours = new HashSet<>(bodies);
        // neighbours.remove(body);
        // return neighbours;
    }

    private void insertBodies(Integer quantity) {
        // TODO: parametrize rand ranges
        Logger.log("Trying to add "+ quantity +" particles!");
        Random rand = new Random();
        Integer currentQuantity = 0;
        while(currentQuantity < quantity) {
            Body newBody = new Body(
                rand.nextDouble()*container.getWidth(0.0),
                rand.nextDouble()*container.getHeight(),
                0.0,
                0.0,
                0.01,
                (rand.nextDouble()*0.01+0.02)/2.0
            );
            // Check that newBody doesn't touch any other body
            if(bodies.stream().noneMatch(newBody::touches) && !container.touchesWall(newBody)) {
                bodies.add(newBody);
                Logger.log("Particle "+currentQuantity+"/"+quantity+" added!");
                currentQuantity++;
            }
        }
    }

    private void updatePeriodic(Body b) {
        if(b.isFixed()) {
            return;
        }
        if(b.getPositionY() < container.getHeight()*(-0.1)) {
            b.setPositionY(container.getHeight());
            b.shouldResetMovement();
            // Logger.log("Updated body position!");
        }
    }

    public void finishExectutor() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
