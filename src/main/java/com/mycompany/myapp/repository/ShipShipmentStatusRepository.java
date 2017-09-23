package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ShipShipmentStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShipShipmentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipShipmentStatusRepository extends JpaRepository<ShipShipmentStatus, Long> {

}
