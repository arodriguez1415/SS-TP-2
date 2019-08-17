package simulation;

import java.util.Random;

import models.Particle;
import models.Universe;
import models.Universe3D;

public class Simulation {
	
	private Universe universe2D;
	private Universe3D universe3D;
	private int steps;
	
	public Simulation(Universe universe2D) {
		this.universe2D = universe2D;
		this.setSteps(0);
	}
	
	public Simulation(Universe3D universe3D) {
		this.universe3D = universe3D;
		this.setSteps(0);
	}

	public Universe getUniverse2D() {
		return universe2D;
	}
	
	public Universe3D getUniverse3D() {
		return universe3D;
	}
	
	public int getSteps() {
		return steps;
	}

	public void setUniverse2D(Universe universe2D) {
		this.universe2D = universe2D;
	}
	
	public void setUniverse3D(Universe3D universe3D) {
		this.universe3D = universe3D;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public void startSimulation2D() {
		int rowDim = this.getUniverse2D().getParticlePerRow();
		int columnDim = this.getUniverse2D().getParticlePerColumn();
		Particle[][] matrix = new Particle[rowDim][columnDim];
		
		for(int i = 0; i < rowDim; i++)
			for(int j = 0; j < columnDim; j++) {
				matrix[i][j] = createParticle(i,j);
			}
		
		this.getUniverse2D().setMatrix(matrix);
	}
	
	public void startSimulation3D() {
		int rowDim = this.getUniverse3D().getParticlePerRow();
		int columnDim = this.getUniverse3D().getParticlePerColumn();
		int heightDim = this.getUniverse3D().getParticlePerHeight();
		Particle[][][] matrix = new Particle[rowDim][columnDim][heightDim];
		
		for(int i = 0; i < rowDim; i++)
			for(int j = 0; j < columnDim; j++) 
				for(int k = 0; k < heightDim; k++)
					matrix[i][j][k] = createParticle(i,j,k);
			
		
		this.getUniverse3D().setMatrix(matrix);
	}
	
	public Particle createParticle(int positionX, int positionY) {
		Particle particle;
		double positionParticleX;
		double positionParticleY;
		boolean state;
		Random rand1;
		Random rand2;
		
		positionParticleX = (double) (positionX + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleY = (double) (positionY + 1) * Particle.getLength() - Particle.getLength() / 2;
		rand1 = new Random();
		rand2 = new Random();
		state = rand1.nextBoolean() || rand2.nextBoolean();
	    
		particle = new Particle(positionParticleX, positionParticleY, state);
		return particle;
	}
	
	public Particle createParticle(int positionX, int positionY, int positionZ) {
		Particle particle;
		double positionParticleX;
		double positionParticleY;
		double positionParticleZ;
		boolean state;
		Random rand1;
		Random rand2;
		
		positionParticleX = (double) (positionX + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleY = (double) (positionY + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleZ = (double) (positionZ + 1) * Particle.getLength() - Particle.getLength() / 2;
		rand1 = new Random();
		rand2 = new Random();
		state = rand1.nextBoolean() || rand2.nextBoolean();
	    
		particle = new Particle(positionParticleX, positionParticleY, positionParticleZ, state);
		return particle;
	}
	
	public void nextStep2D() {
		Particle newMatrix[][] = CellIndexMethod.getNextStage2D(this.universe2D);
		this.universe2D.setMatrix(newMatrix);
		steps++;
	}
	
	public void nextStep3D() {
		System.out.println("next step");
		Particle newMatrix[][][] = CellIndexMethod.getNextStage3D(this.universe3D);
		this.universe3D.setMatrix(newMatrix);
		steps++;
	}
	

}
