package car.sharing.demo.controller;

import car.sharing.demo.dto.user.UpdateProfileInfoRequestDto;
import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for managing users")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "Update role", description = "Update role for specific user")
    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('MANAGER')")
    public UserDto updateRole(@PathVariable Long id,
                              @RequestBody @Valid UpdateRoleRequestDto requestDto) {
        return userService.updateRole(id, requestDto);
    }

    @Operation(summary = "Get info", description = "Get info about authenticated user")
    @GetMapping("/me")
    public UserDto getAuthenticatedUserInfo() {
        return userService.getAuthenticatedUserInfo();
    }

    @Operation(summary = "Update info", description = "Update info about authenticated user")
    @PutMapping("/me")
    public UserDto updateProfileInfo(@RequestBody @Valid
                                         UpdateProfileInfoRequestDto requestDto) {
        return userService.updateProfileInfo(requestDto);
    }
}
