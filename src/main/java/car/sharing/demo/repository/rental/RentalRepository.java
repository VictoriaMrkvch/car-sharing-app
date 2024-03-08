package car.sharing.demo.repository.rental;

import car.sharing.demo.model.Rental;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByUserId(Long id);

    @Query("SELECT r FROM Rental r WHERE r.returnDate <= :tomorrow "
            + "AND r.actualReturnDate = null")
    List<Rental> findOverdueRentals(LocalDate tomorrow);
}
