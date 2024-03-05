package car.sharing.demo.service.user;

import car.sharing.demo.dto.user.UpdateProfileInfoRequestDto;
import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.dto.user.UserRegistrationRequestDto;
import car.sharing.demo.exception.RegistrationException;
import car.sharing.demo.model.User;

public interface UserService {
    UserDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserDto updateRole(Long id, UpdateRoleRequestDto requestDto);

    User getAuthenticatedUser();

    UserDto getAuthenticatedUserInfo();

    UserDto updateProfileInfo(UpdateProfileInfoRequestDto requestDto);
}
