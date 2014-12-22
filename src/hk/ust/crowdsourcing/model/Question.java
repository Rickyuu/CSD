package hk.ust.crowdsourcing.model;

import java.io.Serializable;

public class Question implements Serializable {

	private static final long serialVersionUID = 8705921392318858261L;

	private int id;
	
	private int userId;
	
	private String content;
	private int credit;
	private int target;
	private int expireTime;
	private Answer[] targetAnswers;
	
	private String finalAnswer;
	private boolean isAccepted;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}
	public Answer[] getTargetAnswers() {
		return targetAnswers;
	}
	public void setTargetAnswers(Answer[] targetAnswers) {
		this.targetAnswers = targetAnswers;
	}
	public String getFinalAnswer() {
		return finalAnswer;
	}
	public void setFinalAnswer(String finalAnswer) {
		this.finalAnswer = finalAnswer;
	}
	public boolean isAccepted() {
		return isAccepted;
	}
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	
}
