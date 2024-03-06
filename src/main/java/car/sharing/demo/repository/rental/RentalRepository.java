package car.sharing.demo.repository.rental;

import car.sharing.demo.model.Rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByUserId(Long id);
}
