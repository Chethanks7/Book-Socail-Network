package com.Book_social_Network.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid
            RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody @Valid
            AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam
            String token
    ) throws MessagingException {
        service.activateAccount(token);
    }

}
