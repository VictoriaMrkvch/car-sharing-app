package car.sharing.demo.controller;

import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.dto.user.UserLoginRequestDto;
import car.sharing.demo.dto.user.UserLoginResponseDto;
import car.sharing.demo.dto.user.UserRegistrationRequestDto;
import car.sharing.demo.exception.RegistrationException;
import car.sharing.demo.security.AuthenticationService;
import car.sharing.demo.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
