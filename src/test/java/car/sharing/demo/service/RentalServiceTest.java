package car.sharing.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.repository.rental.RentalRepository;
import car.sharing.demo.service.rental.RentalServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {
    @InjectMocks
    private RentalServiceImpl rentalService;
    @Mock
    private RentalRepository rentalRepository;

    @Test
    @DisplayName("Verify getRentalById() method works with non existing id")
    public void getRentalById_InvalidId_ShouldThrowException() {
        // Given
        Long rentalId = 100L;

        when(rentalRepository.findById(rentalId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> rentalService.getRentalById(rentalId));

        // Then
        String expected = "Can't find rental by id " + rentalId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(rentalRepository, times(1)).findById(rentalId);
        verifyNoMoreInteractions(rentalRepository);
    }
}
