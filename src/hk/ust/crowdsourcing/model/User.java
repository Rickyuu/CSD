package hk.ust.crowdsourcing.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 3856853893838180175L;
	private int id;
	private String userName;
	private String password;
	private int credit;
	
	// solve the problem
	private int correctAnswer;
	private int wrongAnswer;
	
	// seek the problem
	private int acceptAnswer;
	private int declineAnswer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public int getWrongAnswer() {
		return wrongAnswer;
	}
	public void setWrongAnswer(int wrongAnswer) {
		this.wrongAnswer = wrongAnswer;
	}
	public int getAcceptAnswer() {
		return acceptAnswer;
	}
	public void setAcceptAnswer(int acceptAnswer) {
		this.acceptAnswer = acceptAnswer;
	}
	public int getDeclineAnswer() {
		return declineAnswer;
	}
	public void setDeclineAnswer(int declineAnswer) {
		this.declineAnswer = declineAnswer;
	}
	
}
