package car.sharing.demo.controller;

import car.sharing.demo.dto.user.UpdateProfileInfoRequestDto;
import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    public UserDto updateRole(@PathVariable Long id,
                              @RequestBody @Valid UpdateRoleRequestDto requestDto) {
        return userService.updateRole(id, requestDto);
    }

    @GetMapping("/me")
    public UserDto getAuthenticatedUserInfo() {
        return userService.getAuthenticatedUserInfo();
    }

    @PutMapping("/me")
    public UserDto updateProfileInfo(@RequestBody @Valid
                                         UpdateProfileInfoRequestDto requestDto) {
        return userService.updateProfileInfo(requestDto);
    }
}
