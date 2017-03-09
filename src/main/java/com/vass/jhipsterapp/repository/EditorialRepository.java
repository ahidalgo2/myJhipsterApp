package com.vass.jhipsterapp.repository;

import com.vass.jhipsterapp.domain.Editorial;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Editorial entity.
 */
@SuppressWarnings("unused")
public interface EditorialRepository extends JpaRepository<Editorial,Long> {

}
