package com.example.myweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no; //Long: int보다 숫자를 더 많이 넣을 수 있음!
	
	@Column(name="id")
	private String id;
	
	@Column(name="pw")
	private String pw;
	
	public User() {}

	public User(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}
	
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw; 
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}
