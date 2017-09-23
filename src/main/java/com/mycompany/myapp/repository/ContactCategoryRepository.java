package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContactCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactCategoryRepository extends JpaRepository<ContactCategory, Long> {

}
