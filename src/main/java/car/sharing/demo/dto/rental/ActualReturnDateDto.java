package car.sharing.demo.dto.rental;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ActualReturnDateDto {
    @NotNull
    private LocalDate actualReturnDate;
}
