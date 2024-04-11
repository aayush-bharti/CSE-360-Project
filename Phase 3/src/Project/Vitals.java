package Project;

import java.util.Objects;

public class Vitals {
	private String date;
	private String weight;
	private String height;
	private String temperature;
	private String bloodPressure;
	
	public Vitals(String date, String weight, String height, String temperature, String bloodPressure) {
		this.date = date;
		this.weight = weight;
		this.height = height;
		this.temperature = temperature;
		this.bloodPressure = bloodPressure;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Vitals vitals = (Vitals) o;
	    return Objects.equals(date, vitals.date) &&
	           Objects.equals(weight, vitals.weight) &&
	           Objects.equals(height, vitals.height) &&
	           Objects.equals(temperature, vitals.temperature) &&
	           Objects.equals(bloodPressure, vitals.bloodPressure);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(date, weight, height, temperature, bloodPressure);
	}
	
	public String getDate() { 
		return date;
	}
	
	public String getWeight() { 
		return weight;
	}
	
	public String getHeight() { 
		return height;
	}
	
	public String getTemperature() { 
		return temperature;
	}
	
	public String getBloodPressure() { 
		return bloodPressure;
	}
	
	
}
