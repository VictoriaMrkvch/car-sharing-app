package car.sharing.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import car.sharing.demo.dto.car.CarDto;
import car.sharing.demo.dto.car.CreateCarRequestDto;
import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.mapper.car.CarMapper;
import car.sharing.demo.model.Car;
import car.sharing.demo.model.Type;
import car.sharing.demo.repository.car.CarRepository;
import car.sharing.demo.service.car.CarServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;

    @Test
    @DisplayName("Verify save() method works")
    public void save_ValidCreateCarRequestDto_ReturnsCarDto() {
        // Given
        CreateCarRequestDto requestDto = new CreateCarRequestDto();
        requestDto.setModel("Model");
        requestDto.setBrand("Brand");
        requestDto.setType(Type.SEDAN);
        requestDto.setInventory(10);
        requestDto.setDailyFee(BigDecimal.valueOf(40));

        Car car = new Car();
        car.setModel(requestDto.getModel());
        car.setBrand(requestDto.getBrand());
        car.setType(requestDto.getType());
        car.setInventory(requestDto.getInventory());
        car.setDailyFee(requestDto.getDailyFee());

        CarDto carDto = getCarDtoFRomCar(car);
        carDto.setId(1L);

        when(carMapper.toModel(requestDto)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(carDto);

        // When
        CarDto savedCarDto = carService.save(requestDto);

        // Then
        assertThat(savedCarDto).isEqualTo(carDto);

        verify(carRepository, times(1)).save(car);
        verifyNoMoreInteractions(carRepository, carMapper);
    }

    @Test
    @DisplayName("Verify findAll() method works")
    public void findAll_ValidPageable_ReturnsAllCartDto() {
        // Given
        Car car = getDefaultCar();
        car.setId(1L);

        CarDto carDto = getCarDtoFRomCar(car);
        carDto.setId(1L);

        Pageable pageable = PageRequest.of(0,10);
        List<Car> cars = List.of(car);
        Page<Car> carPage = new PageImpl<>(cars, pageable, cars.size());

        when(carRepository.findAll(pageable)).thenReturn(carPage);
        when(carMapper.toDto(car)).thenReturn(carDto);

        // When
        List<CarDto> carDtos = carService.findAll(pageable);

        // Then
        assertThat(carDtos).hasSize(1);
        assertThat(carDtos.get(0)).isEqualTo(carDto);

        verify(carRepository, times(1)).findAll(pageable);
        verify(carMapper, times(1)).toDto(car);
        verifyNoMoreInteractions(carRepository, carMapper);
    }

    @Test
    @DisplayName("Verify findCarById() method works")
    public void findCarById_ValidCarId_ReturnsCarDto() {
        // Given
        Long carId = 1L;
        Car car = getDefaultCar();
        car.setId(carId);

        CarDto carDto = getCarDtoFRomCar(car);
        carDto.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carMapper.toDto(car)).thenReturn(carDto);

        // When
        CarDto carDtoById = carService.findCarById(carId);

        // Then
        assertThat(carDtoById).isEqualTo(carDto);

        verify(carRepository, times(1)).findById(carId);
        verify(carMapper, times(1)).toDto(car);
        verifyNoMoreInteractions(carRepository, carMapper);
    }

    @Test
    @DisplayName("Verify findCarById() method works with non existing id")
    public void findCarById_InvalidCarId_ShouldThrowException() {
        // Given
        Long carId = 100L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> carService.findCarById(carId));

        // Then
        String expected = "Can't find car by id " + carId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(carRepository, times(1)).findById(carId);
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    @DisplayName("Verify updateCarById() method works with non existing id")
    public void updateCarById_InvalidCarId_ShouldThrowException() {
        // Given
        Long carId = 100L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> carService.updateCarById(carId, new CreateCarRequestDto()));

        // Then
        String expected = "Can't find car by id " + carId;
        String actual = exception.getMessage();
        assertEquals(expected, actual);

        verify(carRepository, times(1)).findById(carId);
        verifyNoMoreInteractions(carRepository);
    }

    private Car getDefaultCar() {
        return new Car().setModel("Model")
                .setBrand("Brand")
                .setType(Type.SEDAN)
                .setInventory(10)
                .setDailyFee(BigDecimal.valueOf(25));
    }

    private CarDto getCarDtoFRomCar(Car car) {
        return new CarDto().setModel(car.getModel())
                .setBrand(car.getBrand())
                .setType(car.getType())
                .setInventory(car.getInventory())
                .setDailyFee(car.getDailyFee());
    }

}
