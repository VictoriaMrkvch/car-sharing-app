package car.sharing.demo.mapper.rental;

import car.sharing.demo.config.MapperConfig;
import car.sharing.demo.dto.rental.CreateRentalDto;
import car.sharing.demo.dto.rental.RentalDto;
import car.sharing.demo.model.Car;
import car.sharing.demo.model.Rental;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface RentalMapper {
    @Mapping(source = "car.id", target = "carId")
    RentalDto toDto(Rental rental);

    @Mapping(target = "car", source = "carId", qualifiedByName = "carFromId")
    Rental toModel(CreateRentalDto rentalDto);

    @Named("carFromId")
    default Car carFromId(Long id) {
        return Optional.ofNullable(id)
                .map(Car::new)
                .orElse(null);
    }
}
