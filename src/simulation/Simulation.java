package simulation;

import java.util.Random;

import models.Particle;
import models.Universe;

public class Simulation {
	
	private Universe universe;
	private int steps;
	
	public Simulation(Universe universe) {
		this.universe = universe;
		this.setSteps(0);
	}

	public Universe getUniverse() {
		return universe;
	}
	
	public int getSteps() {
		return steps;
	}

	public void setUniverse(Universe universe) {
		this.universe = universe;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public void startSimulation() {
		int rowDim = this.getUniverse().getParticlePerRow();
		int columnDim = this.getUniverse().getParticlePerColumn();
		Particle[][] matrix = new Particle[rowDim][columnDim];
		
		for(int i = 0; i < rowDim; i++)
			for(int j = 0; j < columnDim; j++) {
				matrix[i][j] = createParticle(i,j);
			}
		
		this.getUniverse().setMatrix(matrix);
	}
	
	public Particle createParticle(int positionX, int positionY) {
		Particle particle;
		double positionParticleX;
		double positionParticleY;
		boolean state;
		Random rand;
		
		positionParticleX = (double) (positionX + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleY = (double) (positionY + 1) * Particle.getLength() - Particle.getLength() / 2;
		rand = new Random();
		state = rand.nextBoolean();
	    
		particle = new Particle(positionParticleX, positionParticleY, state);
		return particle;
	}
	
	public void nextStep() {
		Particle newMatrix[][] = CellIndexMethod.getNextStage(this.universe);
		this.universe.setMatrix(newMatrix);
		steps++;
	}
	

}
