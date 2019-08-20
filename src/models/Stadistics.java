package models;

import simulation.Simulation;

public class Stadistics {
	
	public final static int TWO_DIMENSION = 2;
	public final static int THREE_DIMENSION = 3;
	
	private int dimension;
	private int iteration;
	private int population[];
	private double maxDistance[];
	private int reproduction[];
	private int mortalityOver[];
	private int mortalityUnder[];
	private int supervivance[];
	
	public Stadistics(int dimension, int times) {
		this.dimension = dimension;
		this.iteration = 0;
		this.population = new int[times];
		this.maxDistance = new double[times];
		this.reproduction = new int[times];
		this.mortalityOver = new int[times];
		this.mortalityUnder = new int[times];
		this.supervivance = new int[times];
	}
	
	public void setCurrentPopulation(Simulation simulation) {
		this.population[iteration] = countParticlesAlive(simulation);
	}
	
	private int countParticlesAlive(Simulation simulation) {
		int alive = 0;
		
		if(dimension == TWO_DIMENSION) {
			Universe universe = simulation.getUniverse2D();
			for(int i = 0; i < universe.getParticlePerRow(); i++)
				for(int j = 0; j < universe.getParticlePerColumn(); j++) {
					if(universe.getMatrix()[i][j].getState())
						alive++;
				}
		}else {
			Universe3D universe = simulation.getUniverse3D();
			if(dimension == THREE_DIMENSION) {
				for(int i = 0; i < universe.getParticlePerRow(); i++)
					for(int j = 0; j < universe.getParticlePerColumn(); j++) 
						for(int k = 0; k < universe.getParticlePerHeight(); k++) {
							if(universe.getMatrix()[i][j][k].getState())
								alive++;
						}
			}
		}
		
		return alive;
	}
	
	public void setCurrentMaxDistance(Simulation simulation) {
		
	}
	
	public int[] getReproduction() {
		return reproduction;
	}
	
	public int[] getMortalityOver() {
		return mortalityOver;
	}
	
	public int[] getMortalityUnder() {
		return mortalityUnder;
	}
	
	public int[] getSupervivance() {
		return supervivance;
	}
	
	public void incrementReproduction(){
		this.reproduction[iteration]++;
	}
	
	public void incrementMortalityOver(){
		this.mortalityOver[iteration]++;
	}
	
	public void incrementMortalityUnder(){
		this.mortalityUnder[iteration]++;
	}
	
	public void incrementSupervivance(){
		this.supervivance[iteration]++;
	}
	
	public void incrementIteration() {
		this.iteration++;
	}

}
