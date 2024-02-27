package car.sharing.demo.service.user;

import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.dto.user.UserDto;

public interface UserService {
    UserDto updateRole(Long id, UpdateRoleRequestDto requestDto);
}
