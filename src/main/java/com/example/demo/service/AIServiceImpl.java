package com.example.demo.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Expense;

@Service
public class AIServiceImpl implements AIService{

	@Autowired
	ExpenseService expenseService;
	
	
	@Override
	public String processQuestion(String question) {
		
		question=question.toLowerCase();
		
		if (question.contains("today")) {
            return getTodayExpense();
        }

        if (question.contains("month")) {
            return getMonthlyExpense();
        }
        if (question.contains("food")) return getCategoryExpense("food");
        if (question.contains("tea")) return getCategoryExpense("tea");
        if (question.contains("travel")) return getCategoryExpense("travel");
        
        
        if (question.contains("yesterday")) {
            return getYesterdayExpense();
        }

        return "Sorry, I didn’t understand your question.";
	}
	
	
	private String getTodayExpense() {
		List<Expense> expenses = expenseService.getExpenseByUser();
		LocalDate today = LocalDate.now();
		
		double total = expenses.stream()
                .filter(e -> e.getExpenseDate().equals(today))
                .mapToDouble(Expense::getAmount)
                .sum();
		
		return "Your total expense today is ₹" + total;
	}
	
	private String getMonthlyExpense() {
		
		List<Expense> expenses = expenseService.getExpenseByUser();
		
		YearMonth currMonth = YearMonth.now();
		
		double total = expenses.stream()
				.filter(e -> YearMonth.from(e.getExpenseDate()).equals(currMonth))
				.mapToDouble(Expense :: getAmount)
				.sum();
		
		return "Your total expense this month is ₹" + total;
	}
	
	private String getCategoryExpense(String category) {

	    List<Expense> expenses = expenseService.getExpenseByUser();

	    String key = category.toLowerCase().trim();

	    double total = expenses.stream()
	            .filter(e -> e.getCategory() != null &&
	                         e.getCategory().toLowerCase().contains(key))
	            .mapToDouble(Expense::getAmount)
	            .sum();

	    return "Your total expense on " + category + " is ₹" + total;
	}
	 private String getYesterdayExpense() {

		    List<Expense> expenses = expenseService.getExpenseByUser();

		    LocalDate yesterday = LocalDate.now().minusDays(1);

		    double total = expenses.stream()
		            .filter(e -> e.getExpenseDate().equals(yesterday))
		            .mapToDouble(Expense::getAmount)
		            .sum();

		    return "Your total expense yesterday was ₹" + total;
		}

}
