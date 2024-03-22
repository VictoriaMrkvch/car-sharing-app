package car.sharing.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import car.sharing.demo.model.Rental;
import car.sharing.demo.repository.rental.RentalRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RentalRepositoryTest {
    @Autowired
    private RentalRepository rentalRepository;

    @Test
    @DisplayName("Verify findAllByUserId() method works")
    @Sql(scripts = {
            "classpath:database/repository/insert-two-rentals.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/repository/remove-two-rentals.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByUserId_ValidUserId_ReturnsRentals() {
        List<Rental> rentals = rentalRepository.findAllByUserId(1L);
        assertEquals(2, rentals.size());
    }

    @Test
    @DisplayName("Verify findOverdueRentals() method works")
    @Sql(scripts = {
            "classpath:database/repository/insert-two-rentals.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/repository/remove-two-rentals.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findOverdueRentals_ValidLocalDate_ReturnsRentals() {
        List<Rental> rentals = rentalRepository.findOverdueRentals(LocalDate.now());
        assertEquals(1, rentals.size());
        assertEquals(1, rentals.get(0).getId());
    }
}
