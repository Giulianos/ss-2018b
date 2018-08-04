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
		int l = 10;
		int rc = 1;
		double m = 2;
		int size = (int)Math.floor(l/m);
		List<Particle> particles = new ArrayList<>(); 
		
		Map<Cell, List<Particle>> cells = new HashMap<>();
		
		/**TODO: read from file*/
		particles.add(new Particle(0.1,0.1,0.1));
		particles.add(new Particle(3,4,0.1));
		particles.add(new Particle(4,2,0.1));
		particles.add(new Particle(6,1,0.1));
		particles.add(new Particle(5,5,0.1));
		particles.add(new Particle(1,4,0.1));
		particles.add(new Particle(2,2,0.1));
		
		/** add each particle at corresponding cell*/
		for(Particle p : particles) {
			Cell cell = p.getCell(m);
			
			if(!cells.containsKey(cell)) {
				cells.put(cell, new ArrayList<>());
			}
			cells.get(cell).add(p);
		}
		
		for(Particle particle : particles) {
			Cell particleCell = particle.getCell(m);
			List<Particle> neighbours = new ArrayList<>();
			neighbours.addAll(cells.get(particle.getCell(m)));

			for(Cell c : particleCell.getNeighbours(size)) {
				neighbours.addAll(cells.get(c));
			}
			/** neighbours now contains all particles that need to be compared */			
			
			/** TODO: p.distanceTo(cada particula de neighbour. si no cumple elimino)*/
			neighbours.removeIf(p -> {return p.getR() > 0;});
		}
		
	}
}
