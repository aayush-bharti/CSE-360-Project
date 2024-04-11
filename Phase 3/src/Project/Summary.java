package Project;

import java.util.Objects;

public class Summary {
	private String summary;
	private String date;
	private Vitals vital;
	private Questionnaire questionnaire;
	
	public Summary(String date, Vitals vital, Questionnaire questionnaire) {
		this.date = date;
		this.vital = vital;
		this.questionnaire = questionnaire;
		this.summary = formatSummary(this.vital, this.questionnaire);
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary other = (Summary) o;
        return Objects.equals(date, other.date) &&
               Objects.equals(vital, other.vital) &&
               Objects.equals(questionnaire, other.questionnaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, vital, questionnaire);
    }
	
	public String getDate() {
		return date;
	}
	
	public Vitals getVital() {
		return vital;
	}
	
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public String formatSummary(Vitals vital, Questionnaire questionnaire) {
		String vitalWeight = vital.getWeight();
		String vitalHeight = vital.getHeight();
		String vitalTemp = vital.getTemperature();
		String vitalBP = vital.getBloodPressure();
		
		String vitalSummary = "Your weight was " + vitalWeight + " pounds. Your height was " + vitalHeight + 
							" inches. Your temperature was " + vitalTemp + " degrees Fahrenheit. Your Blood Pressure" +
							"was " + vitalBP + " in mmHg.";
		
		String physicalQuestion = questionnaire.getPhysicalQuestion();
		String mentalQuestion = questionnaire.getMentalQuestion();
		String immunizationQuestion = questionnaire.getImmunizationQuestion();
		
		String questionnaireSummary = " In response to the question about physical health, you answered " + physicalQuestion + 
									 ". In response to the question about mental health, you answered " + mentalQuestion + 
									 ". In response to the question about immunizations, you answered " + immunizationQuestion;
		
		String summary = vitalSummary + questionnaireSummary;
		return summary;
	}
}
