package utils;

//import java.io.FileInputStream;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {
	
	private Map<String,String> program_configuration;
	private Properties properties;
	
	public  Configuration() {
	     this.program_configuration = new HashMap<>();
	     this.properties = new Properties();
    }
	
	public void loadConfig(/*String path*/) {
        //InputStream input = new FileInputStream(path);
        InputStream input = getClass().getResourceAsStream("/configuration_file.txt");
        
        try {
        	properties.load(input);
        } catch(Exception E){
        	
        }

        for (final String name: properties.stringPropertyNames()) {
        	program_configuration.put(name, properties.getProperty(name).toLowerCase());
        }
    }
	
	/* Getting particle modifiers*/
	
	public double getLength() {
		return Double.parseDouble(program_configuration.get("length"));
	}
	
	/* Getting simulation modifiers*/
	
	public int getFinalStep() {
		return Integer.parseInt(program_configuration.get("final_step"));
	}
	
	public int getDimension() {
		return Integer.parseInt(program_configuration.get("dimension"));
	}
	
	public String getRule() {
		return program_configuration.get("rule");
	}
	
	/* Getting matrix modifiers*/
	
	public int getParticlePerRow() {
		return Integer.parseInt(program_configuration.get("particle_per_row"));
	}
	
	public int getParticlePerColumn() {
		return Integer.parseInt(program_configuration.get("particle_per_column"));
	}
	
	public int getParticlePerHeight() {
		return Integer.parseInt(program_configuration.get("particle_per_height"));
	}
	

	


}