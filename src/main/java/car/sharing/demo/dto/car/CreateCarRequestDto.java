package car.sharing.demo.dto.car;

import car.sharing.demo.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateCarRequestDto {
    @NotBlank
    private String model;
    @NotBlank
    private String brand;
    @NotNull
    private Type type;
    @NotNull
    private int inventory;
    @NotNull
    private BigDecimal dailyFee;
}
