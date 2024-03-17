package car.sharing.demo.controller;

import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.dto.user.UserLoginRequestDto;
import car.sharing.demo.dto.user.UserLoginResponseDto;
import car.sharing.demo.dto.user.UserRegistrationRequestDto;
import car.sharing.demo.exception.RegistrationException;
import car.sharing.demo.security.AuthenticationService;
import car.sharing.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management",
        description = "Endpoints for login and registration")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login user",
            description = "Gives the ability to login user")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @Operation(summary = "Register user",
            description = "Gives the ability to register user")
    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
