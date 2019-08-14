package simulation;

import models.Particle;
import models.Universe;


public class CellIndexMethod{
	
	public static int particlePerRow;
	public static int particlePerColumn;
	public static final int RULE_1 = 2;
	public static final int RULE_2 = 3;

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

		if(count >= RULE_1 && count <= RULE_2)
			newParticle.setState(true);
		else
			newParticle.setState(false);
		
		//System.out.println("Particle: (" + i + "," + j + ")" + "  -  count: " + count);

		return newParticle;
	}


}


