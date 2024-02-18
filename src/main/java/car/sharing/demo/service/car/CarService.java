package car.sharing.demo.service.car;

import car.sharing.demo.dto.car.CarDto;
import car.sharing.demo.dto.car.CreateCarRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarService {
    CarDto save(CreateCarRequestDto requestDto);

    List<CarDto> findAll(Pageable pageable);

    CarDto findCarById(Long id);

    CarDto updateCarById(long id, CreateCarRequestDto requestDto);

    void deleteCarById(Long id);
}
