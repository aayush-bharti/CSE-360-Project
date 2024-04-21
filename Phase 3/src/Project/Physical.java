package Project;

import java.util.Objects;

public class Physical {
	private String firstName;
	private String lastName;
	private String DOB;
	private String examDate;
	private float temperature;
	private String heartRate;
	private String bloodPressure;
	private String patientID;
	private String appearanceResult;
	private String entResult;
	private String lungResult;
	private String vascularResult;
	private String appearanceComment;
	private String entComment;
	private String lungComment;
	private String vascularComment;

	public Physical(String examDate, float temperature, String heartRate, String bloodPressure, String appearanceResult, 
					String entResult, String lungResult, String vascularResult, String appearanceComment, 
					String entComment, String lungComment, String vascularComment) {
		
		this.examDate = examDate;
		this.temperature = temperature;
		this.heartRate = heartRate;
		this.bloodPressure = bloodPressure;
		this.appearanceResult = appearanceResult;
		this.entResult = entResult;
		this.lungResult = lungResult;
		this.vascularResult = vascularResult;
		this.appearanceComment = appearanceComment;
		this.entComment = entComment;
		this.lungComment = lungComment;
		this.vascularComment = vascularComment; 
	}
	
	public Physical(String examDate, float temperature, String heartRate, String bloodPressure, String appearanceResult, 
			String entResult, String lungResult, String vascularResult) {

		this.examDate = examDate;
		this.temperature = temperature;
		this.heartRate = heartRate;
		this.bloodPressure = bloodPressure;
		this.appearanceResult = appearanceResult;
		this.entResult = entResult;
		this.lungResult = lungResult;
		this.vascularResult = vascularResult; 
	}
	
	public Physical(String firstName, String lastName, String DOB, String examDate, String temperature, 
					String heartRate, String bloodPressure, String patientID, String appearanceComment, String entComment, 
					String lungComment, String vascularComment) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
		this.examDate = examDate;
		this.temperature = Float.parseFloat(temperature);
		this.heartRate = heartRate;
		this.bloodPressure = bloodPressure;
		this.patientID = patientID;
		this.appearanceComment = appearanceComment;
		this.entComment = entComment;
		this.lungComment = lungComment;
		this.vascularComment = vascularComment; 
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Physical physical = (Physical) o;
        return Objects.equals(examDate, physical.examDate) &&
                Objects.equals(temperature, physical.temperature) &&
                Objects.equals(heartRate, physical.heartRate) &&
                Objects.equals(bloodPressure, physical.bloodPressure) &&
                Objects.equals(appearanceComment, physical.appearanceComment) &&
                Objects.equals(entComment, physical.entComment) &&
                Objects.equals(lungComment, physical.lungComment) &&
                Objects.equals(vascularComment, physical.vascularComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examDate, temperature, heartRate, bloodPressure, appearanceComment, entComment, lungComment, vascularComment);
    }
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getDOB() {
		return DOB;
	}
	
	public String getExamDate() {
		return examDate;
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public String getHeartRate() {
		return heartRate;
	}
	
	public String getBloodPressure() {
		return bloodPressure;
	}
	
	public String getPatientID() {
		return patientID;
	}
	
	public String getAppearanceResult() {
		return appearanceResult;
	}
	
	public String getEntResult() {
		return entResult;
	}
	
	public String getLungResult() {
		return lungResult;
	}
	
	public String getVascularResult() {
		return vascularResult;
	}
	
	public String getAppearanceComment() {
		return appearanceComment;
	}
	
	public String getEntComment() {
		return entComment;
	}
	
	public String getLungComment() {
		return lungComment;
	}
	
	public String getVascularComment() {
		return vascularComment;
	}
}
