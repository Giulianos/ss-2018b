package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Box;
import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Forces.*;
import ar.edu.itba.ss.Integrators.GearPredictorCorrector;
import ar.edu.itba.ss.Integrators.Integrator;
import ar.edu.itba.ss.Observers.SpaceObserver;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Types.Collision;
import ar.edu.itba.ss.Types.Vector;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Space {
    private Container container;
    private Set<Body> bodies;
    private Integrator integrator;
    private SpaceObserver observer;
    private Double elapsedTime = 0.0;

    public Space(Double width, Double height, Double diameter, Integer particleQuantity) {
        // Create container
        this.container = new Box(diameter, height, width);

        // Create bodies set
        this.bodies = new HashSet<>();

        // Insert ooening edge bodies into set
        this.bodies.addAll(this.container.getOpeningBodies());

        // Insert bodies
        insertBodies(particleQuantity);

        // Create integrator
        this.integrator = new GearPredictorCorrector();
    }

    public void attachObserver(SpaceObserver observer) {
        this.observer = observer;

        // Inject initial data to observer
        observer.injectData(bodies, container, elapsedTime);
    }

    public void simulationStep(Double dt) {
        Set<Body> updatedBodies = new HashSet<>();

        for(Body body : bodies) {
            // If the body is fixed, just skip it
            if(body.isFixed()) {
                updatedBodies.add(body);
                continue;
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
            Set<Collision> wallCollisions = container.getWallCollision(body);
            if(wallCollisions.size() > 0) {
                wallCollisions.parallelStream()
                        .map(collision -> new WallCollisionForce(body, collision))
                        .forEach(appliedForces::add);
            }

            // Add gravity force
            appliedForces.add(new GForce(body));

            // Sum forces
            Force appliedForce = new SumForce(appliedForces);

            // Integrate
            Body updatedBody = integrator.calculate(body, dt, appliedForce);

            // Add body to set of updated bodies
            updatedBodies.add(updatedBody);
        }

        // Replace bodies with updatedBodies
        this.bodies = updatedBodies;

        // Move particles to top when falling more
        // than 10% of container height past the opening
        updatePeriodic();

        // Update time
        this.elapsedTime += dt;

        // Update observer
        if(observer != null) {
            observer.injectData(bodies, container, elapsedTime);
        }
    }

    private Set<Body> getNeighbours(Body body) {
        // Naive implementation
        // TODO: use cell index
        Set<Body> neighbours = new HashSet<>(bodies);
        neighbours.remove(body);
        return neighbours;
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
                rand.nextDouble()*0.01+0.02
            );
            // Check that newBody doesn't touch any other body
            if(bodies.stream().noneMatch(newBody::touches)) {
                bodies.add(newBody);
                Logger.log("One particle added!");
                currentQuantity++;
            }
        }
    }

    private void updatePeriodic() {
        for(Body b : bodies) {
            if(b.isFixed()) {
                break;
            }
            if(b.getPosition().y < container.getHeight()*(-0.1)) {
                b.setPosition(new Vector(b.getPosition().x, container.getHeight()));
            }
        }
    }
}
