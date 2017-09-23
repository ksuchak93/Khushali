package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, Long> {

}
