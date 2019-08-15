package simulation;


import models.Particle;
import models.Universe;
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
	public static int final_step;
	public static List<List<double[]>> ovitoInput;


	public static void main(String[] args) {
		Configuration config = new Configuration();
		Universe universe;
		Simulation simulation;
		config.loadConfig();


		particle_length = config.getLength();
		particle_per_row = config.getParticlePerRow();
		particle_per_column = config.getParticlePerColumn();
		final_step = config.getFinalStep();
		ovitoInput = new ArrayList<>();

		Particle.setLength(particle_length);
		universe = new Universe(particle_per_row,particle_per_column);
		CellIndexMethod.particlePerRow = particle_per_row;
		CellIndexMethod.particlePerColumn = particle_per_column;
		simulation = new Simulation(universe);
		simulation.startSimulation();


		while (simulation.getSteps() < final_step) {
			simulation.nextStep();
			List<double[]> currentPositions = new ArrayList<>();

			for(Particle[] row : simulation.getUniverse().getMatrix()) {
				for(Particle p : row) {
					double id = p.getId();
					double x = p.getPositionX();
					double y = p.getPositionY();
					double ra = config.getLength();
					double r;
					double g;
					double b;
					if(p.getState()) {
						r = Color.white.getRed();
						g = Color.white.getGreen();
						b = Color.white.getBlue();
					}else {
						r = Color.black.getRed();
						g = Color.black.getGreen();
						b = Color.black.getBlue();
					}
					double currentParticle[] = {id,x,y,ra,r,g,b};
					currentPositions.add(currentParticle);
				}
			}
			ovitoInput.add(currentPositions);
		}

		generateOvitoInput(ovitoInput, config.getParticlePerColumn()*config.getParticlePerRow(), final_step);
		
	}


	private static void checkStage(Simulation simulation) {
		Particle matrix[][] = simulation.getUniverse().getMatrix();
		System.out.println("Stage: " + simulation.getSteps() + "------------------");
		for(int i = 0; i < particle_per_row; i++) {
			for(int j = 0; j < particle_per_column; j++) {
				if(matrix[i][j].getState())
					System.out.print(" 1 ");
				else
					System.out.print(" 0 ");
				//System.out.print("( X: " + matrix[i][j].getPositionX() + " - Y: " 
					//+ matrix[i][j].getPositionY() + " ) ");
			}
			System.out.println();
		}
		System.out.println("---------------------------");
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
				writer.write(quantity + "\n");
				writer.write("\\ID" + "\t" + "X" + "\t" + "Y" + "\t" + "Radius" + "\t" + "Red"+ "\t" + "Green" + "\t" + "Blue" + "\n");

				for (int i = 0; i < quantity; i++) {
					writer.write((int) list.get(j).get(i)[0] + "\t" + list.get(j).get(i)[1] + "\t" + list.get(j).get(i)[2] + "\t" 
								+ list.get(j).get(i)[3] + "\t" + list.get(j).get(i)[4] + "\t" + list.get(j).get(i)[5] + "\t" + list.get(j).get(i)[6] + "\n");
				}
				j++;
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
