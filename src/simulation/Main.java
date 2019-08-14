package simulation;


import models.Particle;
import models.Universe;
import utils.Configuration;

public class Main {
	
	public static double particle_length;
	public static int particle_per_row;
	public static int particle_per_column;
	public static int final_step;

	
	public static void main(String[] args) {
		Configuration config = new Configuration();
		Universe universe;
		Simulation simulation;
		config.loadConfig();
		
		particle_length = config.getLength();
		particle_per_row = config.getParticlePerRow();
		particle_per_column = config.getParticlePerColumn();
		final_step = config.getFinalStep();
		
		Particle.setLength(particle_length);
		universe = new Universe(particle_per_row,particle_per_column);
		CellIndexMethod.particlePerRow = particle_per_row;
		CellIndexMethod.particlePerColumn = particle_per_column;
		simulation = new Simulation(universe);
		simulation.startSimulation();
		
		checkStage(simulation);

		while (simulation.getSteps() < final_step) {
			simulation.nextStep();
			checkStage(simulation);
		}
		
	}


	private static void checkStage(Simulation simulation) {
		Particle matrix[][] = simulation.getUniverse().getMatrix();
		System.out.println("Stage: " + simulation.getSteps() + "------------------");
		for(int i = 0; i < particle_per_row; i++) {
			for(int j = 0; j < particle_per_column; j++) {
				if(matrix[i][j].getState())
					System.out.print(" 1 ");
				else
					System.out.print(" 0 ");
				//System.out.print("( X: " + matrix[i][j].getPositionX() + " - Y: " 
					//+ matrix[i][j].getPositionY() + " ) ");
			}
			System.out.println();
		}
		System.out.println("---------------------------");
	}
}
