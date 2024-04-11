package Project;

import java.util.Objects;

public class Questionnaire {
	private String dateTime;
	private String physicalQuestion;
	private String mentalQuestion;
	private String immunizationQuestion;

	public Questionnaire(String dateTime, String physicalQuestion, String mentalQuestion, String immunizationQuestion) {
        this.dateTime = dateTime;
        this.physicalQuestion = physicalQuestion;
        this.mentalQuestion = mentalQuestion;
        this.immunizationQuestion = immunizationQuestion;
    }
	
	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questionnaire that = (Questionnaire) o;
        return Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(physicalQuestion, that.physicalQuestion) &&
                Objects.equals(mentalQuestion, that.mentalQuestion) &&
                Objects.equals(immunizationQuestion, that.immunizationQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, physicalQuestion, mentalQuestion, immunizationQuestion);
    }
	
	public String getPhysicalQuestion() {
		return physicalQuestion;
	}
	
	public String getMentalQuestion() {
		return mentalQuestion;
	}

	public String getImmunizationQuestion() {
		return immunizationQuestion;
	}
	
	public String getDateTime() {
	    return dateTime;
	}

}





