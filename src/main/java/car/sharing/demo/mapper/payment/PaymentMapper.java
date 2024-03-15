package car.sharing.demo.mapper.payment;

import car.sharing.demo.config.MapperConfig;
import car.sharing.demo.dto.payment.PaymentDto;
import car.sharing.demo.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "rentalId", source = "rental.id")
    PaymentDto toDto(Payment payment);
}
