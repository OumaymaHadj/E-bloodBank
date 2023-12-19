package com.fsb.eblood.web.controller;

import com.fsb.eblood.dao.entities.ConfirmationEmail;
import com.fsb.eblood.service.AuthService;
import com.fsb.eblood.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fsb.eblood.web.models.request.LoginForm;
import com.fsb.eblood.web.models.request.SignUpForm;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {
		return 	authService.authenticateUser(loginRequest);
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpRequest) {

		return  authService.registerUser(signUpRequest);
	}
	@GetMapping("/confirm-account")
	public String confirmToken(@RequestParam("email")String email) {
			return authService.confirmToken(email);
	}
}


