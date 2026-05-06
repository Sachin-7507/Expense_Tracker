package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AIRequest;
import com.example.demo.dto.AIResponse;
import com.example.demo.service.AIService;

@RestController
@RequestMapping("/ai")
public class AIController {

	@Autowired
	AIService aiService;
	@PostMapping("/ask")
	public ResponseEntity<?> askQuestion(@RequestBody AIRequest request){

	    String answer = aiService.processQuestion(request.getMessage());

	    return ResponseEntity.ok(new AIResponse(answer));
	}
}
