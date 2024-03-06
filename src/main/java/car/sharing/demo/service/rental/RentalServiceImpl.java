package car.sharing.demo.service.rental;

import car.sharing.demo.dto.rental.ActualReturnDateDto;
import car.sharing.demo.dto.rental.CreateRentalDto;
import car.sharing.demo.dto.rental.RentalDto;
import car.sharing.demo.exception.EntityNotFoundException;
import car.sharing.demo.exception.RentalException;
import car.sharing.demo.mapper.rental.RentalMapper;
import car.sharing.demo.model.Rental;
import car.sharing.demo.model.User;
import car.sharing.demo.repository.rental.RentalRepository;
import car.sharing.demo.service.car.CarService;
import car.sharing.demo.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final UserService userService;
    private final CarService carService;

    @Override
    public RentalDto addRental(CreateRentalDto rentalDto) throws RentalException {
        Rental rental = rentalMapper.toModel(rentalDto);
        rental.setActualReturnDate(null);
        rental.setUser(userService.getAuthenticatedUser());
        carService.decreaseCarInventory(rentalDto.getCarId());
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    @Override
    public List<RentalDto> getRentalsForSpecificUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        return rentalRepository.findAllByUserId(authenticatedUser.getId())
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public RentalDto setActualReturnDate(Long id, ActualReturnDateDto returnDateDto)
            throws RentalException {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find rental by id " + id));
        if (rental.getActualReturnDate() != null) {
            throw new RentalException("You can't return car twice");
        }
        rental.setActualReturnDate(returnDateDto.getActualReturnDate());
        carService.increaseCarInventory(rental.getCar().getId());
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    @Override
    public List<RentalDto> getRentalsByUserId(Long id, boolean isActive) {
        return rentalRepository.findAllByUserId(id)
                .stream()
                .filter(rental -> (rental.getActualReturnDate() == null) == isActive)
                .map(rentalMapper::toDto)
                .toList();
    }
}
