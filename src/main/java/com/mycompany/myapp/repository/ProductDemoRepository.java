package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductDemo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ProductDemo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDemoRepository extends JpaRepository<ProductDemo, Long> {
    @Query("select distinct product_demo from ProductDemo product_demo left join fetch product_demo.salesOrders")
    List<ProductDemo> findAllWithEagerRelationships();

    @Query("select product_demo from ProductDemo product_demo left join fetch product_demo.salesOrders where product_demo.id =:id")
    ProductDemo findOneWithEagerRelationships(@Param("id") Long id);

}
