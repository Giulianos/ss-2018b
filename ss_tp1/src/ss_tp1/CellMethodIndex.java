package ss_tp1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CellMethodIndex {
	public static void main(String[] args) {
		int n = 7;
		boolean periodic = true; 
		double l = 15; /** longitud de un lado */
		double rc = 5; /** radio de busqueda de vecinos */
		int m = 5; /** celdas por lado */
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
			
			for (Particle particle2 : cells.get(particle.getCell(m, l))) {
				if(particle.distanceTo(particle2) <= rc && !particle.equals(particle2)) {
					particle.getNeighbours().add(particle2);
					particle2.getNeighbours().add(particle);
				}
			}

			for(Cell c : particleCell.getNeighbours(m)) {
				Set<Particle> neighbours = cells.get(c);
				if(neighbours != null) {
					for (Particle particle2 : neighbours) {
						if(particle.distanceTo(particle2) <= rc) {
							particle.getNeighbours().add(particle2);
							particle2.getNeighbours().add(particle);
						}
					}
				}
			}
			
			if(periodic) {
				for(Cell c : particleCell.getGhostNeighbours(m)) {
					Set<Particle> ghostNeighbours = cells.get(c);
					if(ghostNeighbours != null)
						particle.getGhostNeighbours().addAll(ghostNeighbours);
				}
				/** ghostNeighbours now contains all ghost particles that need to be compared */			
				particle.getGhostNeighbours().removeIf(p -> p.ghostDistanceTo(particle, l) > rc);
				/** add new neighbours */
				for(Particle neighbour : particle.getGhostNeighbours()) {
					System.out.println("add relation" + particle.getId() + "-" + neighbour.getId());
					neighbour.getNeighbours().add(particle);
					particle.getNeighbours().add(neighbour);
				}
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
