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
	
	public void getStadistics(Simulation simulation) {
		setCurrentPopulation(simulation);
		setCurrentMaxDistance(simulation);
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
		double centerX;
		double centerY;
		double centerZ;
		double maxDistance;
		
		if(dimension == TWO_DIMENSION) {
			centerX = (simulation.getUniverse2D().getParticlePerRow() * Particle.getLength()) / 2;
			centerY = (simulation.getUniverse2D().getParticlePerColumn() * Particle.getLength()) / 2;
			maxDistance = getMaxDistance(centerX, centerY, simulation.getUniverse2D().getMatrix(), simulation.getUniverse2D().getParticlePerRow(), simulation.getUniverse2D().getParticlePerColumn(), simulation);
		}else {
			centerX = (simulation.getUniverse3D().getParticlePerRow() * Particle.getLength()) / 2;
			centerY = (simulation.getUniverse3D().getParticlePerColumn() * Particle.getLength()) / 2;
			centerZ = (simulation.getUniverse3D().getParticlePerHeight() * Particle.getLength()) / 2;
			maxDistance = getMaxDistance(centerX, centerY, centerZ, simulation.getUniverse3D().getMatrix() ,
					simulation.getUniverse3D().getParticlePerRow(), simulation.getUniverse3D().getParticlePerColumn(), simulation.getUniverse3D().getParticlePerHeight());
		}
		
		this.maxDistance[iteration] = maxDistance;
	}
	
	public double getMaxDistance(double x, double y, Particle matrix[][], int dimensionX, int dimensionY, Simulation simulation) {
		double currentMax = 0.0;
		double currentDistance = 0.0;
		int row = 0;
		int column = 0;
		for(int i = 0; i < dimensionX; i++)
			for(int j = 0; j < dimensionY; j++) {
				if(matrix[i][j].getState()) {
					currentDistance = Math.sqrt( (Math.pow((matrix[i][j].getPositionX() - x),2) + Math.pow((matrix[i][j].getPositionY() - y),2)) );
					if(currentDistance > currentMax) {
						currentMax = currentDistance;
						row = i;
						column = j;
					}
				}
			}
		//simulation.getUniverse2D().getMatrix()[row][column].signed = true;
		System.out.println("Found max at: [" + row + "," + column + "]");
		
		return currentDistance;
	}

	public double getMaxDistance(double x, double y, double z, Particle matrix[][][], int dimensionX, int dimensionY, int dimensionZ) {
		double currentMax = 0.0;
		double currentDistance = 0.0;
		for(int i = 0; i < dimensionX; i++)
			for(int j = 0; j < dimensionY; j++) 
				for(int k = 0; k < dimensionZ; k++) {
					if(matrix[i][j][k].getState()) {
						currentDistance = Math.sqrt((Math.pow((matrix[i][j][k].getPositionX() - x),2) + Math.pow((matrix[i][j][k].getPositionY() - y),2) + Math.pow((matrix[i][j][k].getPositionZ() - z),2)));
						if(currentDistance > currentMax)
							currentMax = currentDistance;
					}
				}
		return currentDistance;
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
	
	public void printStadistics() {
		
		for(int i = 0; i < this.iteration; i++) {
			System.out.println("-------Step: " + i + "--------");
			System.out.println("Population: " + this.population[i]);
			System.out.println("Max distance from center: " + this.maxDistance[i]);
			System.out.println("Reproduction: " + this.reproduction[i]);
			System.out.println("Supervivance: " + this.supervivance[i]);
			System.out.println("Mortality over: " + this.mortalityOver[i]);
			System.out.println("Mortality under: " + this.mortalityUnder[i]);
		}
	}

}
