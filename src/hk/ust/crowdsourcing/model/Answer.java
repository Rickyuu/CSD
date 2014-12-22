package hk.ust.crowdsourcing.model;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = -897689651971998575L;

	private int id;

	private int userId;
	private String content;
	
	private boolean isRight;
	private int credit;
	
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

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	
	public int getCredit() {
		return credit;
	}
	
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
}
