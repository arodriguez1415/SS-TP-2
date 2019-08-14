package models;

public class Universe {
	
	private Particle[][] matrix;
	private int particlePerRow;
	private int particlePerColumn;
	
	public Universe(int particlePerRow, int particlePerColumn) {
		this.particlePerRow = particlePerRow;
		this.particlePerColumn = particlePerColumn;
		this.matrix = new Particle[particlePerRow][particlePerColumn];
	}
	
	public Particle[][] getMatrix() {
		return matrix;
	}
	
	public int getParticlePerColumn() {
		return particlePerColumn;
	}
	
	public int getParticlePerRow() {
		return particlePerRow;
	}
	
	public void setMatrix(Particle[][] matrix) {
		this.matrix = matrix;
	}


	public void setParticlePerRow(int particlePerRow) {
		this.particlePerRow = particlePerRow;
	}

	public void setParticlePerColumn(int particlePerColumn) {
		this.particlePerColumn = particlePerColumn;
	}

}
