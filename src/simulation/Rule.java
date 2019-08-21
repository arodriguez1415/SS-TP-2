package simulation;

import models.Particle;
import models.Stadistics;

public class Rule {
	
	public static int particlePerRow;
	public static int particlePerColumn;
	public static int particlePerHeight;
	public static Stadistics stadistics;
	
	private static final int RULE_GENERAL2D_1 = 2;
	private static final int RULE_GENERAL2D_2 = 3;
	
	private static final int RULE_GENERAL3D_1 = 6;
	private static final int RULE_GENERAL3D_2 = 9;
	
	public static boolean implementRule2D(String rule, Particle[][] matrix, int i, int j, Particle p) {
		boolean result = false;
		if (rule.equals("general_2d")) {
			result = general2D(matrix, i, j, p);
		}
		
		return result;
	}
	
	public static boolean implementRule3D(String rule, Particle[][][] matrix, int i, int j, int k, Particle p) {
		boolean result = false;
		if(rule.equals("general_3d")) 
			result = general3D(matrix, i, j, k, p);
		
		return result;
	}
	
	private static boolean general2D(Particle[][] matrix, int i, int j, Particle p) {
		int count = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				if (x == particlePerRow && y == particlePerColumn && matrix[0][0].getState()) {
					count++;
				} else if (x == particlePerRow && y >= 0 && y <= particlePerColumn-1 && matrix[0][y].getState()) {
					count++;
				} else if (x > particlePerRow-1 && y < 0 && matrix[0][particlePerColumn-1].getState()) {
					count++;
				} else if (x >= 0 && y == particlePerColumn && x < particlePerRow && matrix[x][0].getState()) {
					count++;
				} else if (x < 0 && y == particlePerColumn && matrix[particlePerRow-1][0].getState()) {
					count++;
				} else if (x >= 0 && y >= 0 && x < particlePerRow && y < particlePerColumn
						&& matrix[x][y].getId() != p.getId() && matrix[x][y].getState()) {
					count++;
				} else if (x >= 0 && y < 0 && x < particlePerRow && matrix[x][particlePerColumn-1].getState()) {
					count++;
				} else if (x < 0 && y < 0 && matrix[particlePerRow-1][particlePerColumn-1].getState()) {
					count++;
				} else if (x < 0 && y >= 0 && y < particlePerColumn && matrix[particlePerRow-1][y].getState()) {
					count++;
				}
			}
		}


		if (currentState == true) {
			if (count >= RULE_GENERAL2D_1 && count <= RULE_GENERAL2D_2) {
				return true;
			} else {
				return false;
			}
		} else {
			if (count == RULE_GENERAL2D_2) {
				return true;
			} else {
				return false;
			}
		}
	}


	private static boolean general3D(Particle[][][] matrix, int i, int j, int k, Particle p){
		int count = 0;
		
		for (int x = i - 1 ; x <= i + 1 && x < particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y < particlePerColumn; y++) {
				for (int z = k - 1; z <= k + 1 && z < particlePerHeight; z++) {
					if (x >= 0 && y >= 0 && z >= 0 && matrix[x][y][z].getId() != p.getId() && matrix[x][y][z].getState()) {
						count++;
					}
				}
			}
		}

		if (count >= RULE_GENERAL3D_1 && count == RULE_GENERAL3D_2) {
			return true;
		}

		return false;
	}

}
