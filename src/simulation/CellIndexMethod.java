package simulation;

import models.Particle;
import models.Universe;
import models.Universe3D;

public class CellIndexMethod{
	
	public static int particlePerRow;
	public static int particlePerColumn;
	public static int particlePerHeight;
	public static String RULE;

	public static Particle[][] getNextStage2D(Universe currentState) {
		Particle[][] previous = currentState.getMatrix();
		Particle[][] next = new Particle[particlePerRow][particlePerColumn];
		Particle p;

		for (int i = 0; i < particlePerRow ; i++) {
			for (int j = 0; j < particlePerColumn ; j++) {
				p = previous[i][j];
				next[i][j] = checkNeighboursCells2D(previous, i, j, p);
			}
		}

		return next;
	}
	
	public static Particle[][][] getNextStage3D(Universe3D currentState) {
		Particle[][][] previous = currentState.getMatrix();
		Particle[][][] next = new Particle[particlePerRow][particlePerColumn][particlePerHeight];
		Particle p;

		for (int i = 0; i < particlePerRow ; i++) {
			for(int j = 0; j < particlePerColumn ; j++) {
				for(int k = 0; k < particlePerHeight; k++) {
					p = previous[i][j][k];
					next[i][j][k] = checkNeighboursCells3D(previous, i, j, k, p);
				}
			}
		}
		
		return next;
	}

	public static Particle checkNeighboursCells2D(Particle[][] matrix, int i, int j, Particle p){
		Particle newParticle = new Particle(p.getPositionX(), p.getPositionY(), false, p.getId());
		newParticle.setState(Rule.implementRule2D(RULE, matrix, i, j, newParticle));

		return newParticle;
	}
	
	public static Particle checkNeighboursCells3D(Particle[][][] matrix, int i, int j, int k, Particle p){
		Particle newParticle = new Particle(p.getPositionX(), p.getPositionY(), p.getPositionZ(), false, p.getId());
		newParticle.setState(Rule.implementRule3D(RULE, matrix, i, j, k, newParticle));

		return newParticle;
	}

}


