package com.masivian.prueba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bet")
public class Bet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@Column(name= "stakeValue")
	private double stakeValue;
	@Column(name= "stakeNumber", nullable = true)
	private Integer stakeNumber;
	@Column(name= "color", nullable = true)
	private String color;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_user")
	@JsonIgnore
	private User user;	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_roulette")
	@JsonIgnore
	private Roulette roulette;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getStakeValue() {
		return stakeValue;
	}
	public void setStakeValue(double stakeValue) {
		this.stakeValue = stakeValue;
	}
	public Integer getStakeNumber() {
		return stakeNumber;
	}
	public void setStakeNumber(Integer stakeNumber) {
		this.stakeNumber = stakeNumber;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Roulette getRoulette() {
		return roulette;
	}
	public void setRoulette(Roulette roulette) {
		this.roulette = roulette;
	}
}
