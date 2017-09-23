package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ShipProduct;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShipProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipProductRepository extends JpaRepository<ShipProduct, Long> {

}
