package com.ssafy.engki.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Kid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private long id;

	@Column(nullable = false, updatable = false)
	private long parentId;

	@Column(nullable = false, updatable = false, length = 20)
	private String name;

	@Column(nullable = false, updatable = false)
	private Date birthday;

	@Column(nullable = false, length = 50)
	private String icon;

	@Column(nullable = false)
	private long exp;

	public void updateIcon(String icon) {
		this.icon = icon;
	}

	public void addExp(int adding) {
		this.exp += adding;
	}
}
