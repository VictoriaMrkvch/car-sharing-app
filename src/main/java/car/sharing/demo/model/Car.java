package car.sharing.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Accessors(chain = true)
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cars SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String brand;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    @Column(nullable = false)
    private int inventory;
    @Column(name = "daily_fee", nullable = false)
    private BigDecimal dailyFee;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public Car(Long id) {
        this.id = id;
    }
}
