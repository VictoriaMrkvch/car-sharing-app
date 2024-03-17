package car.sharing.demo.controller;

import car.sharing.demo.dto.rental.ActualReturnDateDto;
import car.sharing.demo.dto.rental.CreateRentalDto;
import car.sharing.demo.dto.rental.RentalDto;
import car.sharing.demo.exception.RentalException;
import car.sharing.demo.service.rental.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rental management", description = "Endpoints for managing rentals")
@RequiredArgsConstructor
@RequestMapping("/rentals")
@RestController
public class RentalController {
    private final RentalService rentalService;

    @Operation(summary = "Add a new rental", description = "Create and save a new rental")
    @PostMapping
    public RentalDto addRental(@RequestBody @Valid CreateRentalDto rentalDto)
            throws RentalException {
        return rentalService.addRental(rentalDto);
    }

    @Operation(summary = "Get rentals", description = "Get rentals for authenticated user")
    @GetMapping
    public List<RentalDto> getRentalsForUser() {
        return rentalService.getRentalsForSpecificUser();
    }

    @Operation(summary = "Get rentals by user id", description = "Get rentals by specific user id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<RentalDto> getRentalsByUserId(@PathVariable Long id,
                                              @RequestParam boolean isActive) {
        return rentalService.getRentalsByUserId(id, isActive);
    }

    @Operation(summary = "Set return date", description = "Set actual return date")
    @PostMapping("/{id}/return")
    @PreAuthorize("hasAuthority('MANAGER')")
    public RentalDto setActualReturnDate(@PathVariable Long id,
                                         @RequestBody @Valid ActualReturnDateDto returnDateDto)
            throws RentalException {
        return rentalService.setActualReturnDate(id, returnDateDto);
    }
}
