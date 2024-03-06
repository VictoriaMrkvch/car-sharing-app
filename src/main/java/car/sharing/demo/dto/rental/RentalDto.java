package car.sharing.demo.dto.rental;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RentalDto {
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;
    private Long carId;
}
