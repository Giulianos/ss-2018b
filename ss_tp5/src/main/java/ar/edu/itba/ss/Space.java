package ar.edu.itba.ss;

import ar.edu.itba.ss.Containers.Container;
import ar.edu.itba.ss.Forces.Force;
import ar.edu.itba.ss.Forces.SumForce;
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

    public Space(Container container, Set<Body> bodies, double gravity, double dt) {
        this.container = container;
        this.bodies = bodies;
        this.gravity = gravity;
        this.l = container.getTotalLength()*1.1;
        this.integrator = new GearPredictorCorrector();
        this.dt = dt;
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
    private void bruteForce(){
        for(Body body: bodies){
            Set<Force> forces = new HashSet<>();
            // forces.add();    agregar la fuerza que le ejerce la gravedad a body
            for(Body other: bodies){
                if(Body.bodiesInTouch(body,other)){
                    // forces.add();   agregar la fuerza que hace other sobre body
                }
            }
            // forces.add();    agregar la fuerza que le ejercen las paredes a body si esta en contacto con alguna
            udateParticle(body,new SumForce(forces),dt);
        }
    }

}
