package com.example.demo.controller;
import com.example.demo.service.ExpenseService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Expense;
import com.example.demo.service.ExpenseService;

@RestController
@RequestMapping("/expense")

public class ExpenseController {
	
	@Autowired
	public ExpenseService expenseService;
	
	
	@PostMapping("/add")
	public String addExpense(@RequestBody Expense expense) {
	    return expenseService.addExpense(expense);
	}
	
	@GetMapping("/getExpense")
	public List<Expense> getExpenseByUser(){

	    return expenseService.getExpenseByUser();
	}
	
	@DeleteMapping("/deleteExpense/{id}")
	public ResponseEntity<String> deleteExpenseById(@PathVariable Integer id) {
		 expenseService.deleteExpenseById(id);
		 return ResponseEntity.ok("Deleted Successfully");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateExpenseById(@PathVariable Integer id, @RequestBody Expense expense) {
		
		 expenseService.updateExpenseById(id, expense);
		 return ResponseEntity.ok("Updated Successfully");
		
	}
	
	@GetMapping("/getExpenseById/{id}")
	public ResponseEntity<Expense> getExpenseById(@PathVariable Integer id) {

	    Expense expense = expenseService.getById(id);

	    return ResponseEntity.ok(expense);
	}
	
	@DeleteMapping("/clearAll")
	public ResponseEntity<String> clearAllExpense(){
		
		
		String email=SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();
		
		expenseService.clearAllExpenseByUser(email);
		
		return ResponseEntity.ok("Cleard All");
	}
	
	
	@GetMapping("/filter")
	public ResponseEntity<List<Expense>> filterExpenses(
	        @RequestParam(required = false) String fromDate,
	        @RequestParam(required = false) String toDate,
	        @RequestParam(required = false) String category) {

	    return ResponseEntity.ok(
	        expenseService.filterExpenses(fromDate, toDate, category));
	}
}
