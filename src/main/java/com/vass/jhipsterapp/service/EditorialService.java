package com.vass.jhipsterapp.service;

import com.vass.jhipsterapp.service.dto.EditorialDTO;
import java.util.List;

/**
 * Service Interface for managing Editorial.
 */
public interface EditorialService {

    /**
     * Save a editorial.
     *
     * @param editorialDTO the entity to save
     * @return the persisted entity
     */
    EditorialDTO save(EditorialDTO editorialDTO);

    /**
     *  Get all the editorials.
     *  
     *  @return the list of entities
     */
    List<EditorialDTO> findAll();

    /**
     *  Get the "id" editorial.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EditorialDTO findOne(Long id);

    /**
     *  Delete the "id" editorial.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
