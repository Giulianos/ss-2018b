package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Forces.*;
import ar.edu.itba.ss.Integrators.GearPredictorCorrector;
import ar.edu.itba.ss.Integrators.Integrator;
import ar.edu.itba.ss.Particles.Body;
import ar.edu.itba.ss.Particles.Vector;

import java.util.HashSet;
import java.util.Set;

public class Space
{
    private Container container;
    private Set<Body> bodies;
    private Integrator integrator;
    private double gravity;
    private double l;
    private double dt;

    public Space(Container container, Set<Body> bodies, double gravity, double dt, double friction, double kn) {
        this.container = container;
        this.bodies = bodies;
        this.gravity = gravity;
        this.l = container.getTotalLength()*1.1;
        this.integrator = new GearPredictorCorrector();
        this.dt = dt;
        GForce.setGravity(gravity);
        ParticleCollisionForce.setKn(kn);
        ParticleCollisionForce.setFriction(friction);
    }

    public double calculatePressure(double depth){
        return 1.0-Math.exp(-4.0* container.getFriction()*depth/container.getDiameterAt(depth));
    }

    public double beverlooFlow(){
        return density() * Math.sqrt(gravity) * Math.pow(container.getOpeningDiameter() - averageRadius(),1.5);
    }

    private double averageRadius() {
        double average = 0.0;
        for(Body body : bodies){
            average += body.getRadius();
        }
        return average/ bodies.size();
    }

    private double density() {
        return bodies.size()/container.getArea();
    }

    private Vector updatePeriodic(Body body, Vector position){
        double aux = body.getPosition().y + position.y;
        if(aux <= 0){
            body.setPosition(new Vector(body.getPosition().x,aux + l));
            body.setVelocity(new Vector(0.0,0.0));
        }
        return new Vector(body.getPosition().x,aux);
    }

    private void udateParticle(Body b, Force f, double dt){
        Body newb = integrator.calculate(b,dt,f);
        newb.setPosition(updatePeriodic(b,newb.getPosition()));
        b.setPosition(newb.getPosition());
    }
    // usar cellIndexMethod u otro algoritmo para no recorrer todas las particulas para ver si esta en contacto con body
    public void bruteForce(){
        for(Body body: bodies){
            Set<Force> forces = new HashSet<>();
            forces.add(new GForce(body));
            for(Body other: bodies){
                if(Body.bodiesInTouch(body,other)){
                    forces.add(new ParticleCollisionForce(body,other));
                }
            }
            Vector wall = container.touchesWall(body);
            if(wall != null){
                forces.add(new WallCollisionForce(body,wall));
            }
            Body edge = container.touchesEdge(body);
            if(edge != null ){
                forces.add(new ParticleCollisionForce(edge,body));
            }
            udateParticle(body,new SumForce(forces),dt);
        }
    }

    public Set<Body> getBodies() {
        return this.bodies;
    }


}
