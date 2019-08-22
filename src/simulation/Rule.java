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
		}else if(rule.equals("general_2d_modified")) {
			result = general2DModified(matrix,i,j,p);
		}else if(rule.equals("sliced")) {
			result = sliced(matrix,i,j,p);
		}else if(rule.equals("slicedreverse")) {
			result = slicedReverse(matrix,i,j,p);
		}else if(rule.equals("parityflexible")) {
			result = parityFlexible(matrix,i,j,p);
		}else if(rule.equals("paritymortal")) {
			result = parityMortal(matrix,i,j,p);
		}else if(rule.equals("contourn")) {
			result = contourn(matrix,i,j,p);
		}else {
			System.out.println("NOT RULE FOUND: ERROR 505");
		}

		return result;
	}

	public static boolean implementRule3D(String rule, Particle[][][] matrix, int i, int j, int k, Particle p) {
		boolean result = false;
		if(rule.equals("general_3d"))
			result = general3D(matrix, i, j, k, p);
		else if(rule.equals("rule_4555")) {
			result = generic3DRule(matrix, i, j, k, p, 4, 5, 5, 5);
		}else if(rule.equals("rule_5766")) {
			result = generic3DRule(matrix, i, j, k, p, 5, 7, 6, 6);
		}else if(rule.equals("rule_4526")) {
			result = generic3DRule(matrix, i, j, k, p, 4, 5, 2, 6);
		}else if(rule.equals("rule_2333")) {
			result = generic3DRule(matrix, i, j, k, p, 2, 3, 3, 3);
		}else if(rule.equals("rule_5655")) {
			result = generic3DRule(matrix, i, j, k, p, 5, 6, 5, 5);
		}else if(rule.equals("rule_10211021")) {
			result = generic3DRule(matrix, i, j, k, p, 10, 21, 10, 21);
		}else {
			System.out.println("NOT RULE FOUND: ERROR 505");
		}

		return result;
	}

	private static boolean general2D(Particle[][] matrix, int i, int j, Particle p) {
		int count = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				count += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}


		if (currentState == true) {
			if (count >= RULE_GENERAL2D_1 && count <= RULE_GENERAL2D_2) {
				stadistics.incrementSupervivance();
				return true;
			} else if(count < RULE_GENERAL2D_1){
				stadistics.incrementMortalityUnder();
				return false;
			} else {
				stadistics.incrementMortalityOver();
				return false;
			}
		} else {
			if (count == RULE_GENERAL2D_2) {
				stadistics.incrementReproduction();
				return true;
			} else {
				return false;
			}
		}
	}

	private static boolean general2DModified(Particle[][] matrix, int i, int j, Particle p) {
		int count = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				count += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}


		if(count >= RULE_GENERAL2D_1 && count <= RULE_GENERAL2D_2) {
			if(currentState)
				stadistics.incrementSupervivance();
			else
				stadistics.incrementReproduction();
			return true;
		}else {
			if(count < RULE_GENERAL2D_1)
				stadistics.incrementMortalityUnder();
			else
				stadistics.incrementMortalityOver();
			return false;
		}
	}

	private static boolean contourn(Particle[][] matrix, int i, int j, Particle p) {
		int count4268 = 0;
		int count7913 = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				if( (x == i && (y == j + 1 || y == j - 1)) || (y == j && (x == i + 1 || x == i - 1)) )
					count4268 += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
				else
					count7913 += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}

		if(currentState) {
			if(count4268 >= count7913) {
				stadistics.incrementSupervivance();
				return true;
			}else {
				if(count4268 == count7913 && count4268 == 0)
					stadistics.incrementMortalityUnder();
				else
					stadistics.incrementMortalityOver();
				return false;
			}
		}else {
			if(count4268 > count7913) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}

	}

	//Para mantenerte viva las celdas 1478 tienen que estar mas o igual de vivass que 2369
	//Si no hay vecinas, muere
	//Si 2369 es > que 1478 muere
	//Si estaba muerta y 1478 > 2369 revive
	private static boolean sliced(Particle[][] matrix, int i, int j, Particle p) {
		int countTopLeft = 0;
		int countDownRight = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				if(y == j - 1 || (x == i + 1 && y == j))
					countTopLeft += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
				else
					countDownRight += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}

		if(currentState) {
			if(countTopLeft >= countDownRight) {
				stadistics.incrementSupervivance();
				return true;
			}else {
				if(countTopLeft == countDownRight && countTopLeft == 0)
					stadistics.incrementMortalityUnder();
				else
					stadistics.incrementMortalityOver();
				return false;
			}
		}else {
			if(countTopLeft > countDownRight) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}
	}

	//Lo contrario a sliced
	private static boolean slicedReverse(Particle[][] matrix, int i, int j, Particle p) {
		int countTopLeft = 0;
		int countDownRight = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				if(y == j - 1 || (x == i + 1 && y == j))
					countTopLeft += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
				else
					countDownRight += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}

		if(currentState) {
			if(countDownRight >= countTopLeft) {
				stadistics.incrementSupervivance();
				return true;
			}else {
				if(countTopLeft == countDownRight && countTopLeft == 0)
					stadistics.incrementMortalityUnder();
				else
					stadistics.incrementMortalityOver();
				return false;
			}
		}else {
			if(countDownRight > countTopLeft) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}
	}

	//Para mantenerse vivo debe haber cantidad multiplo de 4
	//Si hay impar muere
	//Si no tiene vecinas muere
	//Si estaba muerto debe haber un multiplo de 2
	private static boolean parityFlexible(Particle[][] matrix, int i, int j, Particle p) {
		int count = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				count += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}

		if(currentState) {
			if(count % 4 == 0 && count != 0) {
				stadistics.incrementSupervivance();
				return true;
			}else if (count == 0) {
				stadistics.incrementMortalityUnder();
			}else
				stadistics.incrementMortalityOver();
			return false;
		}else {
			if(count % 2 == 0 && count != 0) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}

	}

	//Para mantenerse vivo debe haber cantidad multiplo de 2
	//Si hay impar muere
	//Si no tiene vecinas muere
	//Si estaba muerto debe haber un multiplo de 4
	private static boolean parityMortal(Particle[][] matrix, int i, int j, Particle p) {
		int count = 0;
		boolean currentState = matrix[i][j].getState();

		for (int x = i - 1 ; x <= i + 1 && x <= particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y <= particlePerColumn; y++) {
				count += neighbours(x, y, particlePerColumn, particlePerRow, matrix, p);
			}
		}

		if(currentState) {
			if(count % 2 == 0 && count != 0) {
				stadistics.incrementSupervivance();
				return true;
			}else if (count == 0) {
				stadistics.incrementMortalityUnder();
			}else
				stadistics.incrementMortalityOver();
			return false;
		}else {
			if(count % 4 == 0 && count != 0) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}

	}


	private static int neighbours(int x, int y, int particlePerColumn, int particlePerRow, Particle[][] matrix, Particle p) {
		if (x == particlePerRow && y == particlePerColumn && matrix[0][0].getState()) {
			return 1;
		} else if (x == particlePerRow && y >= 0 && y <= particlePerColumn-1 && matrix[0][y].getState()) {
			return 1;
		} else if (x > particlePerRow-1 && y < 0 && matrix[0][particlePerColumn-1].getState()) {
			return 1;
		} else if (x >= 0 && y == particlePerColumn && x < particlePerRow && matrix[x][0].getState()) {
			return 1;
		} else if (x < 0 && y == particlePerColumn && matrix[particlePerRow-1][0].getState()) {
			return 1;
		} else if (x >= 0 && y >= 0 && x < particlePerRow && y < particlePerColumn
				&& matrix[x][y].getId() != p.getId() && matrix[x][y].getState()) {
			return 1;
		} else if (x >= 0 && y < 0 && x < particlePerRow && matrix[x][particlePerColumn-1].getState()) {
			return 1;
		} else if (x < 0 && y < 0 && matrix[particlePerRow-1][particlePerColumn-1].getState()) {
			return 1;
		} else if (x < 0 && y >= 0 && y < particlePerColumn && matrix[particlePerRow-1][y].getState()) {
			return 1;
		}

		return 0;
	}



	private static boolean general3D(Particle[][][] matrix, int i, int j, int k, Particle p){
		int count = 0;
		boolean currentState = matrix[i][j][k].getState();

		for (int x = i - 1 ; x <= i + 1 && x < particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y < particlePerColumn; y++) {
				for (int z = k - 1; z <= k + 1 && z < particlePerHeight; z++) {
					if (x >= 0 && y >= 0 && z >= 0 && matrix[x][y][z].getId() != p.getId() && matrix[x][y][z].getState()) {
						count++;
					}
				}
			}
		}

		if(currentState) {
			if(count >= RULE_GENERAL3D_1 && count <= RULE_GENERAL3D_2) {
				stadistics.incrementSupervivance();
				return true;
			}else if (count < RULE_GENERAL3D_1) {
				stadistics.incrementMortalityUnder();
				return false;
			}else {
				stadistics.incrementMortalityOver();
				return false;
			}	
		}else {
			if(count >= RULE_GENERAL3D_1 && count <= RULE_GENERAL3D_2) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}
	}
	
	private static boolean generic3DRule(Particle[][][] matrix, int i, int j, int k, Particle p, int initialE, int finalE, int initialF, int finalF){
		int count = 0;
		boolean currentState = matrix[i][j][k].getState();

		for (int x = i - 1 ; x <= i + 1 && x < particlePerRow; x++) {
			for (int y = j - 1; y <= j + 1 && y < particlePerColumn; y++) {
				for (int z = k - 1; z <= k + 1 && z < particlePerHeight; z++) {
					if (x >= 0 && y >= 0 && z >= 0 && matrix[x][y][z].getId() != p.getId() && matrix[x][y][z].getState()) {
						count++;
					}
				}
			}
		}

		if(currentState) {
			if(count >= initialE && count <= finalE) {
				stadistics.incrementSupervivance();
				return true;
			}else if (count < initialE) {
				stadistics.incrementMortalityUnder();
				return false;
			}else {
				stadistics.incrementMortalityOver();
				return false;
			}	
		}else {
			if(count >= initialF && count <= finalF) {
				stadistics.incrementReproduction();
				return true;
			}
			return false;
		}
	}
	
	

}
