package ss_tp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class CellMethodIndex {
	public static void main(String[] args) {
		int n = 7;
		double l = 15; /** longitud de un lado */
		double rc = 15; /** radio de busqueda de vecinos */
		int m = 1; /** celdas por lado */
		Set<Particle> particles = new HashSet<>(); 
		
		Map<Cell, Set<Particle>> cells = new HashMap<>();
		
		/**TODO: read from file*/
		particles.add(new Particle(4.5,2.7,0.1));
		particles.add(new Particle(14.1,3.6,0.1));
		particles.add(new Particle(12.6,5.4,0.1));
		particles.add(new Particle(4.5,8.7,0.1));
		particles.add(new Particle(1.2,10.8,0.1));
		particles.add(new Particle(7.5,14.4,0.1));
		
		/** add each particle at corresponding cell*/
		for(Particle p : particles) {
			Cell cell = p.getCell(m, l);
			
			if(!cells.containsKey(cell)) {
				cells.put(cell, new HashSet<>());
			}
			cells.get(cell).add(p);
		}
		
		for(Particle particle : particles) {
			Cell particleCell = particle.getCell(m, l);
			particle.getNeighbours().addAll(cells.get(particle.getCell(m, l)));

			for(Cell c : particleCell.getNeighbours(m)) {
				Set<Particle> neighbours = cells.get(c);
				if(neighbours != null)
					particle.getNeighbours().addAll(neighbours);
			}
			/** neighbours now contains all particles that need to be compared */			
			particle.getNeighbours().removeIf(p -> p.distanceTo(particle) > rc);
			particle.getNeighbours().remove(particle);
			for(Particle neighbour : particle.getNeighbours()) {
				neighbour.getNeighbours().add(particle);
			}
		}
		printParticles(particles);
	}
	
	public static void printParticles(Set<Particle> ps) {
		for (Particle p : ps) {
			System.out.println(p + " Neighbours:");
			for (Particle neighbour : p.getNeighbours()) {
				System.out.println(" -" + neighbour);
			}
		}
	}
}
