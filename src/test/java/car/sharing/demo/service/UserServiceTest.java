package car.sharing.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import car.sharing.demo.dto.user.UpdateRoleRequestDto;
import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.repository.user.UserRepository;
import car.sharing.demo.service.user.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Verify updateRole() method works with non existing id")
    public void updateRole_InvalidUserId_ShouldThrowException() {
        // Given
        Long userId = 100L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateRole(userId, new UpdateRoleRequestDto()));

        // Then
        String expected = "Can't find user by id " + userId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}
