package com.pratice.controller;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratice.sevice.EmailUtil;

import jakarta.mail.MessagingException;
import lombok.Data;

@Controller
@RestController
@Data
public class EmailController {
	
	@Autowired
	private EmailUtil emailUtil ;
	
	@PostMapping(value = "/send",consumes =  "multipart/form-data")
	public ResponseEntity<String> sendEmail(@RequestParam String to,@RequestParam String cc,@RequestParam String  bcc,@RequestParam String subject,
            @RequestParam String body,
            @RequestPart(required = false) MultipartFile file ) {
		 try {
			emailUtil.sendEmail(to, bcc, bcc, subject, body, file);
		 } catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return ResponseEntity.ok("Email sent successfully");

	}

}
