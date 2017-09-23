package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SalesOrderProduct;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SalesOrderProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesOrderProductRepository extends JpaRepository<SalesOrderProduct, Long> {

}
