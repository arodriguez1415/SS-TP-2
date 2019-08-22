package simulation;

import java.util.Random;

import models.Particle;
import models.Stadistics;
import models.Universe;
import models.Universe3D;

public class Simulation {

	private Universe universe2D;
	private Universe3D universe3D;
	private int totalRow;
	private int totalColumn;
	private int totalHeight;
	private int steps;
	private String initialPattern;

	public Simulation(Universe universe2D, String pattern) {
		this.universe2D 	= universe2D;
		this.totalRow 		= universe2D.getParticlePerRow();
		this.totalColumn 	= universe2D.getParticlePerColumn();
		this.initialPattern = pattern;
		this.setSteps(0);
	}

	public Simulation(Universe3D universe3D, String pattern) {
		this.universe3D 	= universe3D;
		this.totalRow 		= universe3D.getParticlePerRow();
		this.totalColumn 	= universe3D.getParticlePerColumn();
		this.totalHeight 	= universe3D.getParticlePerHeight();
		this.initialPattern	= pattern;
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

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void startSimulation2D() {
		int rowDim 		= this.getUniverse2D().getParticlePerRow();
		int columnDim 	= this.getUniverse2D().getParticlePerColumn();
		Particle[][] matrix = new Particle[rowDim][columnDim];


		for (int i = 0; i < rowDim; i++) {
			for (int j = 0; j < columnDim; j++) {
				matrix[i][j] = createParticle(i, j);
			}
		}


		this.getUniverse2D().setMatrix(matrix);
		Rule.stadistics.getStadistics(this);
		Rule.stadistics.incrementIteration();
	}

	public void startSimulation3D() {
		int rowDim 		= this.getUniverse3D().getParticlePerRow();
		int columnDim 	= this.getUniverse3D().getParticlePerColumn();
		int heightDim 	= this.getUniverse3D().getParticlePerHeight();
		Particle[][][] matrix = new Particle[rowDim][columnDim][heightDim];

		for (int i = 0; i < rowDim; i++) {
			for (int j = 0; j < columnDim; j++) {
				for (int k = 0; k < heightDim; k++) {
					matrix[i][j][k] = createParticle(i, j, k);
				}
			}
		}

		this.getUniverse3D().setMatrix(matrix);
		Rule.stadistics.getStadistics(this);
		Rule.stadistics.incrementIteration();
	}

	public Particle createParticle(int positionX, int positionY) {
		Particle particle;
		double positionParticleX;
		double positionParticleY;
		boolean state;

		positionParticleX = (double) (positionX + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleY = (double) (positionY + 1) * Particle.getLength() - Particle.getLength() / 2;

		if (initialPattern.toLowerCase().equals("battle")) {
			state = battle2DPattern(positionX, positionY);
		} else if (initialPattern.toLowerCase().equals("linear")) {
			state = lineal2DPattern(positionX, positionY);
		} else if (initialPattern.toLowerCase().equals("cube")) {
			state = cube2DPattern(positionX, positionY);
		} else if (initialPattern.toLowerCase().equals("cube2")) {
			state = cubeModified2DPattern(positionX, positionY);
		} else if (initialPattern.toLowerCase().equals("oscilator2")) {
			state = oscilatorModified2DPattern(positionX,positionY);
		} else if (initialPattern.toLowerCase().equals("oscilator")){
			state = oscilator2DPattern(positionX, positionY);
		} else if (initialPattern.toLowerCase().equals("fatality")){
			state = fatality2DPattern(positionX, positionY);
		} else {
			state = randomPattern();
		}

		particle = new Particle(positionParticleX, positionParticleY, state);
		return particle;
	}

	public boolean randomPattern() {
		Random rand = new Random();
		float fl = rand.nextFloat();
		if (fl <= 0.80) {
			return true;
		}
		return false;
	}

	public boolean cube2DPattern(int i, int j) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;

		if(i == aux1 && j == aux2 || i == aux1+1 && j == aux2 || i == aux1 && j == aux2+1 ||
				i == aux1+1 && j == aux2+1) {
			return true;
		}

		return false;
	}

	public boolean cubeModified2DPattern(int i, int j) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;

		if(i == aux1 && j == aux2 || i == aux1+1 && j == aux2 || i == aux1 && j == aux2+1 ||
				i == aux1+1 && j == aux2+1) {
			return true;
		}

		if(i == aux1 && j== aux2-1 || i == aux1-1 && j== aux2+1 || i == aux1+1 && j== aux2+2 || i == aux1+1 && j== aux2+3 ||
				i == aux1+1 && j== aux2+4 || i == aux1+2 && j== aux2){
			return true;
		}

		return false;
	}

	public boolean fatality2DPattern(int i, int j) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;

		for(int k = 0; k< 6; k++) {
			if (i == aux1 + k && j == aux2) {
				return true;
			}
			if (i == aux1 + k && j == aux2 + 6) {
				return true;
			}
		}

		for(int k = 0; k< 6; k++) {
			if (i == aux1 && j == aux2 + k ) {
				return true;
			}
			if (i == aux1 + 1 && j == aux2 + k) {
				return true;
			}
			if (i == aux1 + 3 && j == aux2 + k) {
				return true;
			}
			if (i == aux1 + 4 && j == aux2 + k) {
				return true;
			}
			if (i == aux1 + 5 && j == aux2 + k) {
				return true;
			}
		}

		if (i == aux1 + 6 && (j == aux2 + 2 || j == aux2 + 3 || j == aux2 + 4)) {
			return true;
		}

		return false;
	}

	public boolean oscilator2DPattern(int i, int j) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;

		for(int k = 0; k < 5; k ++) {
			if(i == aux1 + k && j == aux2){
				return true;
			}

			if(i == aux1 + k && j == aux2 + 4){
				return true;
			}
		}

		if (i == aux1 && j == aux2 + 2) {
			return true;
		}

		if (i == aux1 + 4 && j == aux2 + 2) {
			return true;
		}

		return false;
	}

	public boolean oscilatorModified2DPattern(int i, int j) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;

		for(int k = 0; k < 5; k ++) {
			if(i == aux1 + k && j == aux2){
				return true;
			}

			if(i == aux1 + k && j == aux2 - 4){
				return true;
			}
		}

		if (i == aux1 && j == aux2 + 2) {
			return true;
		}

		if (i == aux1 + 4 && j == aux2 + 2) {
			return true;
		}

		return false;
	}

	public boolean battle2DPattern(int i, int j){
		int aux1 = totalRow/2 - 10;
		int aux2 = totalColumn/2;

		if(i == aux1 && j == aux2 || i == aux1 + 1 && j == aux2
				|| i == aux1 && j == aux2 + 1 || i == aux1 + 1 && j == aux2 + 1) {
			return true;
		}

		if(i == aux1 + 34 && j == aux2 - 2 || i == aux1 + 35 && j == aux2 - 2
				|| i == aux1 + 35 && j == aux2 - 1 || i == aux1 + 34 && j == aux2 - 1) {
			return true;
		}

		if(i == aux1 + 5 && j == aux2 + 2 || i == aux1 + 5 && j == aux2 + 3) {
			return true;
		}

		if(i == aux1 + 10 && j == aux2 + 4 || i == aux1 + 10 && j == aux2 - 2 ) {
			return true;
		}

		int k;

		for (k = 0; k<4; k++) {
			if (i == aux1 + 10 + k  && j == aux2 + 3)
				return true;
			if (i == aux1 + 11 + k  && j == aux2 + 2)
				return true;
			if (i == aux1 + 11 + k  && j == aux2)
				return true;
			if (i == aux1 + 10 + k  && j == aux2 - 1)
				return true;
		}

		if (i == aux1 + 11 && j == aux2 + 1 || i == aux1 + 14 && j == aux2 + 1) {
			return true;
		}

		for(k = 0; k < 3; k ++) {
			if (i == aux1 + 19 && j == aux2 - k)
				return true;
			if (i == aux1 + 20 && j == aux2 - k)
				return true;
			if (i == aux1 + 24 && j == aux2 - k)
				return true;
		}

		if(i == aux1 + 21 && j == aux2 + 1 || i == aux1 + 22 && j == aux2 + 2 ||
				i == aux1 + 23 && j == aux2 + 1){
			return true;
		}

		if(i == aux1 + 21 && j == aux2 - 3 || i == aux1 + 22 && j == aux2 - 4 ||
				i == aux1 + 23 && j == aux2 - 3 ){
			return true;
		}


		return false;
	}



	public boolean lineal2DPattern(int i, int j){
		if(i == totalRow/2) {
			return true;
		}

		if(i == totalRow/4) {
			return true;
		}

		if(i == (totalRow/2 + totalRow/4)) {
			return true;
		}

		return false;
	}

	public Particle createParticle(int positionX, int positionY, int positionZ) {
		Particle particle;
		double positionParticleX;
		double positionParticleY;
		double positionParticleZ;
		boolean state;

		positionParticleX = (double) (positionX + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleY = (double) (positionY + 1) * Particle.getLength() - Particle.getLength() / 2;
		positionParticleZ = (double) (positionZ + 1) * Particle.getLength() - Particle.getLength() / 2;

		if (initialPattern.toLowerCase().equals("cube")) {
			state = cube3DPattern(positionX, positionY, positionZ);
		} else if (initialPattern.toLowerCase().equals("border")) {
			state = border3DPattern(positionX, positionY, positionZ);
		} else if (initialPattern.toLowerCase().equals("linear")) {
			state = linear3DPattern(positionX, positionY, positionZ);
		} else if (initialPattern.toLowerCase().equals("glider")) {
			state = glider3DPattern(positionX, positionY, positionZ);
		} else {
			state = random3DPattern();
		}

		particle = new Particle(positionParticleX, positionParticleY, positionParticleZ, state);
		return particle;
	}

	public boolean cube3DPattern(int i, int j, int z) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;
		int aux3 = totalHeight/2;

		for(int k = 0; k < totalRow/10; k++) {
			for (int l = 0; l < totalColumn / 10; l++) {
				for (int m = 0; m < totalHeight / 10; m++) {
					if (i == aux1 + k && j == aux2 + l && z == aux3 + m) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean random3DPattern() {
		Random rand = new Random();
		float fl = rand.nextFloat();
		if (fl <= 0.005) {
			return true;
		}
		return false;
	}

	public boolean border3DPattern(int i, int j, int z) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;
		int aux3 = totalHeight/2;

		for(int k = 0; k < totalRow; k++) {
			for(int l = 0; l < totalColumn; l++) {
				for(int m = 0; m < totalHeight; m++) {
					if(i == 0 && j == 0 & z != 0 ) {
						return true;
					}
					if(i != 0 && j == 0 & z == 0 ) {
						return true;
					}
					if(i == 0 && j != 0 & z == 0 ) {
						return true;
					}

					if(i == totalRow-1 && j != totalColumn-1 & z == 0 ) {
						return true;
					}

					if(i == 0 && j != totalColumn-1 & z == totalHeight-1 ) {
						return true;
					}

					if(i == totalRow-1 && j == 0 & z != totalHeight-1 ) {
						return true;
					}

					if(i == totalRow-1 && j == totalColumn-1 & z != totalHeight-1 ) {
						return true;
					}

					if(i != totalRow-1 && j == totalColumn-1 & z == totalHeight-1 ) {
						return true;
					}

					if(i == totalRow-1 && j != totalColumn-1 & z == totalHeight-1 ) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean linear3DPattern(int i, int j, int z) {
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;
		int aux3 = totalHeight/2;

		for(int k = 0; k < aux2/10; k++) {
			for (int l = 0; l < aux1/10; l++) {
				for (int m = -totalHeight/3; m < totalHeight/3; m++) {
					if (i == aux1 + k && j == totalColumn/2 && z == aux3 + m) {
						return true;
					}

					if (i == aux1 + k + totalRow/4 && j == totalColumn/2 && z == aux3 + m) {
						return true;
					}

					if (i == aux1 + k - totalRow/4 && j == totalColumn/2 && z == aux3 + m) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean glider3DPattern(int i, int j, int z) { //oscila con 5655
		int aux1 = totalRow/2;
		int aux2 = totalColumn/2;
		int aux3 = totalHeight/2;

		if(i == aux1 && j == aux2 && z == aux3 || i == aux1 + 1 && j == aux2 && z == aux3
		|| i == aux1 + 2 && j == aux2 && z == aux3){
			return true;
		}
		if(i == aux1 && j == aux2 && z == aux3 + 2 || i == aux1 + 1 && j == aux2 && z == aux3 + 2
				|| i == aux1 + 2 && j == aux2 && z == aux3 + 2){
			return true;
		}
		if(i == aux1 + 1 && j == aux2 && z == aux3 + 1){
			return true;
		}

		return false;
	}


	public void nextStep2D() {
		Particle newMatrix[][] = CellIndexMethod.getNextStage2D(this.universe2D);
		this.universe2D.setMatrix(newMatrix);
		Rule.stadistics.getStadistics(this);
		Rule.stadistics.incrementIteration();
		steps++;
	}

	public void nextStep3D() {
		Particle newMatrix[][][] = CellIndexMethod.getNextStage3D(this.universe3D);
		this.universe3D.setMatrix(newMatrix);
		Rule.stadistics.getStadistics(this);
		Rule.stadistics.incrementIteration();
		steps++;
	}


}