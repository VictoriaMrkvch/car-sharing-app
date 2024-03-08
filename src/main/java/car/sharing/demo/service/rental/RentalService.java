package car.sharing.demo.service.rental;

import car.sharing.demo.dto.rental.ActualReturnDateDto;
import car.sharing.demo.dto.rental.CreateRentalDto;
import car.sharing.demo.dto.rental.RentalDto;
import car.sharing.demo.exception.RentalException;
import java.util.List;

public interface RentalService {
    RentalDto addRental(CreateRentalDto rentalDto) throws RentalException;

    List<RentalDto> getRentalsForSpecificUser();

    RentalDto setActualReturnDate(Long id, ActualReturnDateDto returnDateDto)
            throws RentalException;

    List<RentalDto> getRentalsByUserId(Long id, boolean isActive);

    void checkOverdueRentals();
}
