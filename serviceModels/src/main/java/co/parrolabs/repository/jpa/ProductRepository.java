package co.parrolabs.repository.jpa;

import co.parrolabs.dto.ProductDto;
import co.parrolabs.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository   extends JpaRepository<Product, UUID> {

    @Query(value = "Select * from product p where BIN_TO_UUID(p.ID) IN (:productsIds)", nativeQuery = true)
    List<Product> getProductsByIds(@Param("productsIds") List<String> productsIds);

}
