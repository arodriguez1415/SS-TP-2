package models;

public class Universe3D {
	
	private Particle[][][] matrix;
	private int particlePerRow;
	private int particlePerColumn;
	private int particlePerHeight;
	
	public Universe3D (int particlePerRow, int particlePerColumn, int particlePerHeight) {
		this.particlePerRow = particlePerRow;
		this.particlePerColumn = particlePerColumn;
		this.particlePerHeight = particlePerHeight;
		this.matrix = new Particle[particlePerRow][particlePerColumn][particlePerHeight];
	}
	
	public Particle[][][] getMatrix() {
		return matrix;
	}
	
	public int getParticlePerColumn() {
		return particlePerColumn;
	}
	
	public int getParticlePerRow() {
		return particlePerRow;
	}
	
	public int getParticlePerHeight() {
		return particlePerHeight;
	}
	
	public void setMatrix(Particle[][][] matrix) {
		this.matrix = matrix;
	}

}
