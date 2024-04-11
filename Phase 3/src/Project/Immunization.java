package Project;

import java.util.Objects;

public class Immunization {
	private String dateTime;
	private String immunizationQuestion;
	
	public Immunization(String dateTime, String immunizationQuestion) {
		this.dateTime = dateTime;
		this.immunizationQuestion = immunizationQuestion;
	}
	
	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Immunization that = (Immunization) o;
        return Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(immunizationQuestion, that.immunizationQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, immunizationQuestion);
    }
	
	public String getImmunizationQuestion() {
	    return immunizationQuestion;
	}
	
	public String getDateTime() {
	    return dateTime;
	}
}