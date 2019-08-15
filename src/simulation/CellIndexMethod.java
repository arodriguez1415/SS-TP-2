package simulation;

import models.Particle;
import models.Universe;
import models.Universe3D;


public class CellIndexMethod{
	
	public static int particlePerRow;
	public static int particlePerColumn;
	public static int particlePerHeight;
	public static final int RULE_1_2D = 2;
	public static final int RULE_2_2D = 3;
	
	public static final int RULE_1_3D = 2;
	public static final int RULE_2_3D = 3;
	public static final int RULE_3_3D = 4;

	public static Particle[][] getNextStage(Universe currentState) {
		Particle[][] previous = currentState.getMatrix();
		Particle[][] next = new Particle[particlePerRow][particlePerColumn];
		Particle p;

		for (int i = 0; i < particlePerRow ; i++) {
			for(int j = 0; j < particlePerColumn ; j++) {
				p = previous[i][j];
				next[i][j] = checkNeighboursCells(previous, i, j, p);
			}
		}
		
		return next;
	}

	public static Particle checkNeighboursCells(Particle[][] matrix, int i, int j, Particle p){
		int count = 0;
		Particle newParticle = new Particle(p.getPositionX(), p.getPositionY(), false);
		for(int x = i - 1 ; x <= i + 1 && x < particlePerRow; x++)
			for(int y = j - 1; y <= j + 1 && y < particlePerColumn; y++) {
				if(x >= 0 && y >= 0 && matrix[x][y].getId() != p.getId() && matrix[x][y].getState())
					count++;
					
			}

		if(count >= RULE_1_2D && count <= RULE_2_2D)
			newParticle.setState(true);
		else
			newParticle.setState(false);
		
		//System.out.println("Particle: (" + i + "," + j + ")" + "  -  count: " + count);

		return newParticle;
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
	
	public static Particle checkNeighboursCells3D(Particle[][][] matrix, int i, int j, int k, Particle p){
		int count = 0;
		Particle newParticle = new Particle(p.getPositionX(), p.getPositionY(), p.getPositionZ(), false);
		for(int x = i - 1 ; x <= i + 1 && x < particlePerRow; x++)
			for(int y = j - 1; y <= j + 1 && y < particlePerColumn; y++) 
				for(int z = k - 1; z <= k + 1 && z < particlePerHeight; z++){
					if(x >= 0 && y >= 0 && z >= 0 && matrix[x][y][z].getId() != p.getId() && matrix[x][y][z].getState())
						count++;	
				}

		if(count >= RULE_1_2D && count <= RULE_2_2D)
			newParticle.setState(true);
		else
			newParticle.setState(false);

		return newParticle;
	}
	
	


}


