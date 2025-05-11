package org.teta.controller.rest;

import constants.PathConstants;
import org.teta.dto.request.*;
import org.teta.dto.response.AuthUserResponse;
import org.teta.dto.response.AuthenticationResponse;
import org.teta.mapper.AuthenticationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping(PathConstants.LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.login(request, bindingResult));
    }

    @PostMapping(PathConstants.FORGOT_EMAIL)
    public ResponseEntity<String> getExistingEmail(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.getExistingEmail(request.getEmail(), bindingResult));
    }

    @PostMapping(PathConstants.FORGOT)
    public ResponseEntity<String> sendPasswordResetCode(@Valid @RequestBody ProcessEmailRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.sendPasswordResetCode(request.getEmail(), bindingResult));
    }

    @GetMapping(PathConstants.RESET_CODE)
    public ResponseEntity<AuthUserResponse> getUserByPasswordResetCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(authenticationMapper.getUserByPasswordResetCode(code));
    }

    @PostMapping(PathConstants.RESET)
    public ResponseEntity<String> passwordReset(@Valid @RequestBody PasswordResetRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.passwordReset(request, bindingResult));
    }

    @PostMapping(PathConstants.RESET_CURRENT)
    public ResponseEntity<String> currentPasswordReset(@Valid @RequestBody CurrentPasswordResetRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.currentPasswordReset(request, bindingResult));
    }
}
