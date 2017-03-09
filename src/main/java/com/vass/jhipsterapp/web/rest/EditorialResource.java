package com.vass.jhipsterapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vass.jhipsterapp.service.EditorialService;
import com.vass.jhipsterapp.web.rest.util.HeaderUtil;
import com.vass.jhipsterapp.service.dto.EditorialDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Editorial.
 */
@RestController
@RequestMapping("/api")
public class EditorialResource {

    private final Logger log = LoggerFactory.getLogger(EditorialResource.class);

    private static final String ENTITY_NAME = "editorial";
        
    private final EditorialService editorialService;

    public EditorialResource(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    /**
     * POST  /editorials : Create a new editorial.
     *
     * @param editorialDTO the editorialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new editorialDTO, or with status 400 (Bad Request) if the editorial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/editorials")
    @Timed
    public ResponseEntity<EditorialDTO> createEditorial(@RequestBody EditorialDTO editorialDTO) throws URISyntaxException {
        log.debug("REST request to save Editorial : {}", editorialDTO);
        if (editorialDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new editorial cannot already have an ID")).body(null);
        }
        EditorialDTO result = editorialService.save(editorialDTO);
        return ResponseEntity.created(new URI("/api/editorials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /editorials : Updates an existing editorial.
     *
     * @param editorialDTO the editorialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated editorialDTO,
     * or with status 400 (Bad Request) if the editorialDTO is not valid,
     * or with status 500 (Internal Server Error) if the editorialDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/editorials")
    @Timed
    public ResponseEntity<EditorialDTO> updateEditorial(@RequestBody EditorialDTO editorialDTO) throws URISyntaxException {
        log.debug("REST request to update Editorial : {}", editorialDTO);
        if (editorialDTO.getId() == null) {
            return createEditorial(editorialDTO);
        }
        EditorialDTO result = editorialService.save(editorialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, editorialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /editorials : get all the editorials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of editorials in body
     */
    @GetMapping("/editorials")
    @Timed
    public List<EditorialDTO> getAllEditorials() {
        log.debug("REST request to get all Editorials");
        return editorialService.findAll();
    }

    /**
     * GET  /editorials/:id : get the "id" editorial.
     *
     * @param id the id of the editorialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the editorialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/editorials/{id}")
    @Timed
    public ResponseEntity<EditorialDTO> getEditorial(@PathVariable Long id) {
        log.debug("REST request to get Editorial : {}", id);
        EditorialDTO editorialDTO = editorialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(editorialDTO));
    }

    /**
     * DELETE  /editorials/:id : delete the "id" editorial.
     *
     * @param id the id of the editorialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/editorials/{id}")
    @Timed
    public ResponseEntity<Void> deleteEditorial(@PathVariable Long id) {
        log.debug("REST request to delete Editorial : {}", id);
        editorialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
