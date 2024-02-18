package car.sharing.demo.service.car;

import car.sharing.demo.dto.car.CarDto;
import car.sharing.demo.dto.car.CreateCarRequestDto;
import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.mapper.car.CarMapper;
import car.sharing.demo.model.Car;
import car.sharing.demo.repository.car.CarRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarDto save(CreateCarRequestDto requestDto) {
        Car car = carMapper.toModel(requestDto);
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    public List<CarDto> findAll(Pageable pageable) {
        return carRepository.findAll(pageable)
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public CarDto findCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find car by id " + id)
        );
        return carMapper.toDto(car);
    }

    @Override
    public CarDto updateCarById(long id, CreateCarRequestDto requestDto) {
        carRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find car by id " + id));
        Car updatedCar = carMapper.toModel(requestDto);
        updatedCar.setId(id);
        return carMapper.toDto(carRepository.save(updatedCar));
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}
