package car.sharing.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.net.URL;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@SQLDelete(sql = "UPDATE payments SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    @OneToOne
    @MapsId
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;
    private URL url;
    @Column(name = "session_id")
    private String sessionId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
