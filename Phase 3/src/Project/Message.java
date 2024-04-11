package Project;

import java.util.Objects;

public class Message {
	private String dateTime;
	private String recipient;
	private String subject;
	private String messageBody;
	
	public Message(String dateTime, String recipient, String subject, String messageBody) {
		this.dateTime = dateTime;
		this.recipient = recipient;
		this.subject = subject;
		this.messageBody = messageBody;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Message message = (Message) o;
	    return Objects.equals(dateTime, message.dateTime) &&
	    	   Objects.equals(recipient, message.recipient) &&
	           Objects.equals(subject, message.subject) &&
	           Objects.equals(messageBody, message.messageBody);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(dateTime, recipient, subject, messageBody);
	}

	public String getDateTime() {
		return dateTime;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getMessageBody() {
		return messageBody;
	}
}
