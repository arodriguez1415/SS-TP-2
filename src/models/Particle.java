package models;


public class Particle {
	
	private static double length;
	private static int particleID = 0;
	
	private double positionX;
	private double positionY;
	private int id;
	private boolean state;
	
	public Particle(double positionX, double positionY, boolean state) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.id = particleID++;
		this.state = state;
	}

	public double getPositionX() {
		return positionX;
	}
	
	public double getPositionY() {
		return positionY;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	
	public boolean getState() {
		return state;
	}
	
	public int getId() {
		return id;
	}
	
	public static double getLength() {
		return length;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static void setLength(double length) {
		Particle.length = length;
	}
	
	public void printParticleInfo() {
		System.out.println(this.positionX + " " + this.positionY);
	}
	

}
