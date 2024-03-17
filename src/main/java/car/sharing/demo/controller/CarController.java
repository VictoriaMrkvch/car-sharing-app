package car.sharing.demo.controller;

import car.sharing.demo.dto.car.CarDto;
import car.sharing.demo.dto.car.CreateCarRequestDto;
import car.sharing.demo.service.car.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Car management", description = "Endpoints for managing cars")
@RequiredArgsConstructor
@RequestMapping("/cars")
@RestController
public class CarController {
    private final CarService carService;

    @Operation(summary = "Create a new car", description = "Create and save a new car")
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public CarDto save(@RequestBody @Valid CreateCarRequestDto requestDto) {
        return carService.save(requestDto);
    }

    @Operation(summary = "Get all cars", description = "Get list of available cars")
    @GetMapping
    public List<CarDto> findAll(Pageable pageable) {
        return carService.findAll(pageable);
    }

    @Operation(summary = "Find car by id", description = "Find car by specific id")
    @GetMapping("/{id}")
    public CarDto findById(@PathVariable Long id) {
        return carService.findCarById(id);
    }

    @Operation(summary = "Update car by id", description = "Update car by specific id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public CarDto updateById(@PathVariable Long id,
                             @RequestBody @Valid CreateCarRequestDto requestDto) {
        return carService.updateCarById(id, requestDto);
    }

    @Operation(summary = "Delete car by id", description = "Delete car by specific id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/id")
    public void deleteById(@PathVariable Long id) {
        carService.deleteCarById(id);
    }
}
