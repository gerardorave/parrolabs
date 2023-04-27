package co.parrolabs.repository.jpa;

import co.parrolabs.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, UUID> {
}
