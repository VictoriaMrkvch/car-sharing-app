package car.sharing.demo.service.user;

import car.sharing.demo.dto.user.UpdateProfileInfoRequestDto;
import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.dto.user.UserRegistrationRequestDto;
import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.exception.RegistrationException;
import car.sharing.demo.mapper.user.UserMapper;
import car.sharing.demo.model.Role;
import car.sharing.demo.model.User;
import car.sharing.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(Role.CUSTOMER);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateRole(Long id, UpdateRoleRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id " + id));
        user.setRole(requestDto.getRole());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with email: "
                        + authentication.getName()));
    }

    @Override
    public UserDto getAuthenticatedUserInfo() {
        return userMapper.toDto(getAuthenticatedUser());
    }

    @Override
    public UserDto updateProfileInfo(UpdateProfileInfoRequestDto requestDto) {
        User authenticatedUser = getAuthenticatedUser();
        authenticatedUser.setEmail(requestDto.getEmail());
        authenticatedUser.setFirstName(requestDto.getFirstName());
        authenticatedUser.setLastName(requestDto.getLastName());
        return userMapper.toDto(userRepository.save(authenticatedUser));
    }
}
