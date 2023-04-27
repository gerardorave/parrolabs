package co.parrolabs.repository.jpa;

import co.parrolabs.model.OrderCustomer;
import co.parrolabs.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    @Query(value = "Select * from order_product op where BIN_TO_UUID(op.order_customer_id) = :orderId", nativeQuery = true)
    List<OrderProduct> getOrderProductsByOrderId(@Param("orderId") String orderId);
}