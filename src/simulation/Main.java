package simulation;


import models.Particle;
import models.Stadistics;
import models.Universe;
import models.Universe3D;
import utils.Configuration;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static double particle_length;
	public static int particle_per_row;
	public static int particle_per_column;
	public static int particle_per_height;
	public static int final_step;
	public static int dimension;
	public static String rule;
	public static String initialPattern;
	
	public final static int DIMENSION_2D = 2;
	public final static int DIMENSION_3D = 3;

	public static void main (String[] args) {
		Configuration config = new Configuration();
		Simulation simulation;
		config.loadConfig();

		particle_length 	= config.getLength();
		particle_per_row	= config.getParticlePerRow();
		particle_per_column = config.getParticlePerColumn();
		particle_per_height = config.getParticlePerHeight();
		final_step 			= config.getFinalStep();
		dimension			= config.getDimension();
		rule 				= config.getRule();
		initialPattern		= config.getInitialPattern();
		List<List<double[]>> ovitoInput		= new ArrayList<>();
		
		setVariables();
		
		Stadistics stadistics = new Stadistics(dimension, final_step + 1);
		Rule.stadistics = stadistics;

		if (dimension == DIMENSION_2D) {
			simulation = new Simulation(new Universe(particle_per_row, particle_per_column), initialPattern);
			simulation.startSimulation2D();
		} else {
			simulation = new Simulation(new Universe3D(particle_per_row, particle_per_column, particle_per_height), initialPattern);
			simulation.startSimulation3D();
		}

		int loopcount = 0;
		while (simulation.getSteps() < final_step) {
			List<double[]> currentPositions = new ArrayList<>();
			
			if (dimension == DIMENSION_2D) {
				if(loopcount != 0)
					simulation.nextStep2D();
				loopcount++;
				for (Particle[] row : simulation.getUniverse2D().getMatrix()) {
					for (Particle p : row) {
						double id = p.getId();
						double x = p.getPositionX();
						double y = p.getPositionY();
						double ra = config.getLength();
						double r;
						double g;
						double b;
						if (p.getState()) {
							r = Color.white.getRed();
							g = Color.white.getGreen();
							b = Color.white.getBlue();
						} else {
							r = Color.black.getRed();
							g = Color.black.getGreen();
							b = Color.black.getBlue();
						}


						double currentParticle[] = {id,x,y,ra,r,g,b};
						currentPositions.add(currentParticle);
					}
				}
			} else {
				if(loopcount != 0)
					simulation.nextStep3D();
				loopcount++;
				for (int i = 0; i < particle_per_row; i++) {
					for (int j = 0; j < particle_per_column; j++) {
						for (int k = 0; k < particle_per_height; k++) {
							Particle p = simulation.getUniverse3D().getMatrix()[i][j][k];
							double id = p.getId();
							double x = p.getPositionX();
							double y = p.getPositionY();
							double z = p.getPositionZ();
							double ra = config.getLength();
							double r;
							double g;
							double b;
							double alpha;
							double maxz = particle_per_height * ra;
							double maxx = particle_per_row * ra;
							double maxy = particle_per_column * ra;

							if (p.getState()) {
								if(z>=(maxz/2 + maxz/4)) {
									r = Color.red.getRed();
									g = Color.red.getGreen();
									b = Color.red.getBlue();
									alpha = 0.2;
								} else if(z<(maxz/2 + maxz/4) && z>=maxz/2) {
									r = Color.green.getRed();
									g = Color.green.getGreen();
									b = Color.green.getBlue();
									alpha = 0.2;
								} else if(z>=maxz/4 && z<maxz/2) {
									r = Color.yellow.getRed();
									g = Color.yellow.getGreen();
									b = Color.yellow.getBlue();
									alpha = 0.2;
								} else {
									r = Color.blue.getRed();
									g = Color.blue.getGreen();
									b = Color.blue.getBlue();
									alpha = 0.2;
								}
							} else {
								r = Color.black.getRed();
								g = Color.black.getGreen();
								b = Color.black.getBlue();
								alpha = 1;
							}
							double currentParticle[] = {id, x, y, z, ra, r, g, b, alpha};
							currentPositions.add(currentParticle);
						}
					}
				}
			}

			ovitoInput.add(currentPositions);
			
		}
		Rule.stadistics.printStadistics();
		if (dimension == DIMENSION_2D) {
			generateOvitoInput(ovitoInput, config.getParticlePerColumn() * config.getParticlePerRow(), final_step);
		} else {
			generateOvitoInput(ovitoInput, config.getParticlePerColumn() * config.getParticlePerRow() * config.getParticlePerHeight(), final_step);
		}
	}
	
	private static void setVariables() {
		Particle.setLength(particle_length);

		CellIndexMethod.particlePerRow 		= particle_per_row;
		CellIndexMethod.particlePerColumn 	= particle_per_column;
		CellIndexMethod.particlePerHeight 	= particle_per_height;
		CellIndexMethod.RULE 				= rule;
		Rule.particlePerRow 				= particle_per_row;
		Rule.particlePerColumn 				= particle_per_column;
		Rule.particlePerHeight 				= particle_per_height;
	}

	public static void generateOvitoInput(List<List<double[]>> list, int quantity, int final_step) {
		//System.out.println("N = " + quantity);

		File file = new File("./data.txt");
		//Create the file
		try {
			file.createNewFile();
			System.out.println("File is created!");
		} catch (IOException e) {
			System.out.println("File could not be created");
		}

		//Write Content
		int j = 0;
		FileWriter writer;
		try {
			writer = new FileWriter(file);
			while (j < final_step) {
				if (dimension == DIMENSION_2D) {
					writer.write(quantity + "\n");
					writer.write("\\ID" + "\t" + "X" + "\t" + "Y" + "\t" + "Radius" + "\t" + "Red"+ "\t" + "Green" + "\t" + "Blue" + "\n");

					for (double[] d : list.get(j)) {
						writer.write((int) d[0] + "\t" + d[1] + "\t" + d[2] + "\t" + d[3] + "\t" + d[4] + "\t" + d[5] + "\t" + d[6] + "\n");
					}
				} else {
					writer.write(quantity + "\n");
					writer.write("\\ID" + "\t" + "X" + "\t" + "Y" + "\t" + "Z" + "\t" + "Radius" + "\t" + "Red"+ "\t" + "Green"
							+ "\t" + "Blue" + "\t" + "Transparency" + "\n");

					for (double[] d : list.get(j)) {
						writer.write((int) d[0] + "\t" + d[1] + "\t" + d[2] + "\t" + d[3] + "\t" + d[4] + "\t" + d[5] + "\t" + d[6] + "\t" + d[7] + "\t" + d[8] + "\n");
					}
				}

				j++;
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
