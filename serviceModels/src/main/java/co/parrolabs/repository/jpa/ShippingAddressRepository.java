package co.parrolabs.repository.jpa;

import co.parrolabs.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, UUID> {
}
