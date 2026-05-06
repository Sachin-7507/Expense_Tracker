package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.entity.Expense;
import com.example.demo.entity.User;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>{
	public List<Expense> findByUser(User user);
	
	@Modifying
	@Query("DELETE FROM Expense e WHERE e.user.id = :userId")
	void deleteByUserId(int userId);
	
	@Query("SELECT e FROM Expense e WHERE e.user = :user AND (:category IS NULL OR e.category = :category) AND (:fromDate IS NULL OR e.expenseDate >= :fromDate) AND (:toDate IS NULL OR e.expenseDate <= :toDate)")
	List<Expense> filterExpenses(
	        @Param("user") User user,
	        @Param("category") String category,
	        @Param("fromDate") LocalDate fromDate,
	        @Param("toDate") LocalDate toDate);
}
