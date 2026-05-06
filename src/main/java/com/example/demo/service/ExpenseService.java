package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import jakarta.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
@Service
public class ExpenseService {
	@Autowired
	public ExpenseRepository expenseRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	 @Autowired
	 private JwtUtil jwtUtil;
	 


	 public String addExpense(Expense expense) {

	     String email = SecurityContextHolder.getContext()
	             .getAuthentication()
	             .getName();

	     User user = userRepository.findByEmail(email)
	             .orElseThrow(() -> new RuntimeException("User not found"));

	     expense.setUser(user);
	     expenseRepository.save(expense);

	     return "Expense added successfully";
	 }
	 
	 public List<Expense> getExpenseByUser(){

		    String email = SecurityContextHolder.getContext()
		            .getAuthentication()
		            .getName();

		    User user = userRepository.findByEmail(email)
		            .orElseThrow(() -> new RuntimeException("User not found"));

		    return expenseRepository.findByUser(user);
	}
	 
	 public String deleteExpenseById(int id) {
    	 expenseRepository.deleteById(id);
    	 return "Expense Deleted Successfully";
    }
	 
	public String updateExpenseById(int id, Expense expense) {
		Expense ex = expenseRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
		
	
		
		ex.setAmount(expense.getAmount());
		ex.setCategory(expense.getCategory());
		ex.setDescription(expense.getDescription());
		ex.setExpenseDate(expense.getExpenseDate());
		
		expenseRepository.save(ex);
		
		return "Updated Successfully..";
		
	}
	
	public Expense getById(int id) {
	    return expenseRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Expense not found"));
	}
//
//	public void clearAllExpense() {
//		 expenseRepository.deleteAll();
//		 
//	}
	
	
	
	@Transactional
	public void clearAllExpenseByUser(String email) {

	    User user = userRepository.findByEmail(email)
	                    .orElseThrow(() -> new RuntimeException("User not found"));

	    expenseRepository.deleteByUserId(user.getId());
	}
	
	
	public List<Expense> filterExpenses(String fromDate, String toDate, String category) {

	    String email = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    LocalDate from = (fromDate != null && !fromDate.isEmpty())
	            ? LocalDate.parse(fromDate)
	            : null;

	    LocalDate to = (toDate != null && !toDate.isEmpty())
	            ? LocalDate.parse(toDate)
	            : null;

	    String cat = (category != null && !category.isEmpty())
	            ? category
	            : null;

	    return expenseRepository.filterExpenses(user, cat, from, to);
	}
	
}
