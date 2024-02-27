package car.sharing.demo.mapper.user;

import car.sharing.demo.config.MapperConfig;
import car.sharing.demo.dto.user.UserDto;
import car.sharing.demo.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);
}
