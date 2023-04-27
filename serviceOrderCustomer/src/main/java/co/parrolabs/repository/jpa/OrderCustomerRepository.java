package co.parrolabs.repository.jpa;

import co.parrolabs.model.OrderCustomer;
import co.parrolabs.model.OrderProduct;
import co.parrolabs.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, UUID> {

    @Query(value = "Select * from order_customer oc where oc.order_number = :orderNumber", nativeQuery = true)
    Optional<OrderCustomer> findOrderByOrderNumber(@Param("orderNumber") Long orderNumber);


}