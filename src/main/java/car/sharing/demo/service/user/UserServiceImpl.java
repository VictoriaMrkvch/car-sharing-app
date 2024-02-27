package car.sharing.demo.service.user;

import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.mapper.user.UserMapper;
import car.sharing.demo.model.User;
import car.sharing.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto updateRole(Long id, UpdateRoleRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id " + id));
        user.setRole(requestDto.getRole());
        return userMapper.toDto(userRepository.save(user));
    }
}
