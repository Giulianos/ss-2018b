package ss_tp1;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CellMethodIndex {
	private static Scanner staticFile;
	private static Scanner dinamicFile;
	private static boolean periodic;
	private static boolean punctual; /** Si vamos a considerar particulas puntuales o no */
	private static double max_r;
	private static double l; /** longitud de un lado */
	private static double rc; /** radio de busqueda de vecinos */
	private static int m; /** celdas por lado */
	private static double noiseAmplitude = 0.1;
	private static Set<Particle> particles;

	public static void main(String[] args) throws IOException {
		periodic = Boolean.parseBoolean(args[2]);
		punctual = Boolean.parseBoolean(args[3]); /** Si vamos a considerar particulas puntuales o no */
		max_r = 0;
		l = 15; /** longitud de un lado */
		rc = Double.parseDouble(args[0]); /** radio de busqueda de vecinos */
		m = Integer.parseInt(args[1]); /** celdas por lado */
		particles = new HashSet<>();
		
		try {
			staticFile = new Scanner(new FileReader("static.txt"));
			dinamicFile = new Scanner(new FileReader("dinamic.txt"));
			
			staticFile.nextInt();
			l = staticFile.nextDouble();
			dinamicFile.nextInt();
			while(staticFile.hasNextDouble() && dinamicFile.hasNextLine()) {
				double readX = dinamicFile.nextDouble();
				double readY = dinamicFile.nextDouble();
				double readR = staticFile.nextDouble();
				double readVx = dinamicFile.nextDouble();
				double readVy = dinamicFile.nextDouble();
				particles.add(new Particle(readX, readY, readR, readVx, readVy));
				max_r = (max_r < readR) ? readR : max_r;
			}
			dinamicFile.close();
			staticFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Particle.setL(l);
		
		if(!punctual && (l/m) < (rc + max_r)) {
			m = (int)Math.ceil(l/(rc + max_r));
			System.out.println("Se actualizó M para cumplir la condicion Rc+max_R < L/M");
		}


        int quantity = 300;
        double noise = new Random().nextDouble() * noiseAmplitude - noiseAmplitude/2;
		for(int i = 0; i < quantity; i++) {
		    update(noise);
            printFrame(particles, i);
        }

	}

	public static void calculateNeighbours() {
		Map<Cell, Set<Particle>> cells = new HashMap<>();
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
			particle.removeCurrentNeighbours();

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
				particle.getGhostNeighbours().removeIf(p -> p.ghostDistanceTo(particle) > rc);
				/** add new neighbours */
				for(Particle neighbour : particle.getGhostNeighbours()) {
//					System.out.println("add relation" + particle.getId() + "-" + neighbour.getId());
					neighbour.getNeighbours().add(particle);
					particle.getNeighbours().add(neighbour);
				}
			}
		}
	}

	public static void offLattice(double noise) {
		double currentSinAVG;
		double currentCosAVG;

		for(Particle p : particles) {
			currentSinAVG = 0;
			currentCosAVG = 0;
			for(Particle neighbour : p.getNeighbours()) {
				currentSinAVG += Math.sin(neighbour.getTita());
				currentCosAVG += Math.cos(neighbour.getTita());
			}
            currentSinAVG += Math.sin(p.getTita());
            currentCosAVG += Math.cos(p.getTita());
			currentCosAVG /= p.getNeighbours().size() + 1;
			currentSinAVG /= p.getNeighbours().size() + 1;
			p.setNewTita(Math.atan2(currentSinAVG,currentCosAVG) + noise);
		}
		for(Particle p : particles) {
			p.updateVelocity();
			p.setX(p.getX() + p.getV_x());
			p.setY(p.getY() + p.getV_y());
		}
	}

	public static void update(double noise) {
		calculateNeighbours();
		offLattice(noise);
	}

	public static void printFrame(Set<Particle> ps, int frameNumber) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("simulation/frame_"+frameNumber+".txt"));
		writer.write(ps.size()+"\n");
		for (Particle p : ps) {
			writer.write("\n"+p);
		}
		writer.close();
	}
}
