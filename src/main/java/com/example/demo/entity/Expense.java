package com.example.demo.entity;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="expense")
@Setter
@Getter
@Entity
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="description")
	private String description;
	
	@Column(name="category")
	private String category;
	@Column(name="amount")
	private int amount;
	
	@Column(name = "expense_date")
	private LocalDate expenseDate;
	  @ManyToOne
	  @JoinColumn(name = "user_id")
	  private User user;
}
