package car.sharing.demo.controller;

import car.sharing.demo.dto.rental.ActualReturnDateDto;
import car.sharing.demo.dto.rental.CreateRentalDto;
import car.sharing.demo.dto.rental.RentalDto;
import car.sharing.demo.exception.RentalException;
import car.sharing.demo.service.rental.RentalService;
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

@RequiredArgsConstructor
@RequestMapping("/rentals")
@RestController
public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    public RentalDto addRental(@RequestBody @Valid CreateRentalDto rentalDto)
            throws RentalException {
        return rentalService.addRental(rentalDto);
    }

    @GetMapping
    public List<RentalDto> getRentalsForUser() {
        return rentalService.getRentalsForSpecificUser();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public List<RentalDto> getRentalsByUserId(@PathVariable Long id,
                                              @RequestParam boolean isActive) {
        return rentalService.getRentalsByUserId(id, isActive);
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasAuthority('MANAGER')")
    public RentalDto setActualReturnDate(@PathVariable Long id,
                                         @RequestBody @Valid ActualReturnDateDto returnDateDto)
            throws RentalException {
        return rentalService.setActualReturnDate(id, returnDateDto);
    }
}
