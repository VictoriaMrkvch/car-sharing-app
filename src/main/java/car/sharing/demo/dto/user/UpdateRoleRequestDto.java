package car.sharing.demo.dto.user;

import car.sharing.demo.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequestDto {
    @NotNull
    private Role role;
}
