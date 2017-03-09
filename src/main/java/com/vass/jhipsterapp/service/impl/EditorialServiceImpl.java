package com.vass.jhipsterapp.service.impl;

import com.vass.jhipsterapp.service.EditorialService;
import com.vass.jhipsterapp.domain.Editorial;
import com.vass.jhipsterapp.repository.EditorialRepository;
import com.vass.jhipsterapp.service.dto.EditorialDTO;
import com.vass.jhipsterapp.service.mapper.EditorialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Editorial.
 */
@Service
@Transactional
public class EditorialServiceImpl implements EditorialService{

    private final Logger log = LoggerFactory.getLogger(EditorialServiceImpl.class);
    
    private final EditorialRepository editorialRepository;

    private final EditorialMapper editorialMapper;

    public EditorialServiceImpl(EditorialRepository editorialRepository, EditorialMapper editorialMapper) {
        this.editorialRepository = editorialRepository;
        this.editorialMapper = editorialMapper;
    }

    /**
     * Save a editorial.
     *
     * @param editorialDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EditorialDTO save(EditorialDTO editorialDTO) {
        log.debug("Request to save Editorial : {}", editorialDTO);
        Editorial editorial = editorialMapper.editorialDTOToEditorial(editorialDTO);
        editorial = editorialRepository.save(editorial);
        EditorialDTO result = editorialMapper.editorialToEditorialDTO(editorial);
        return result;
    }

    /**
     *  Get all the editorials.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EditorialDTO> findAll() {
        log.debug("Request to get all Editorials");
        List<EditorialDTO> result = editorialRepository.findAll().stream()
            .map(editorialMapper::editorialToEditorialDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one editorial by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EditorialDTO findOne(Long id) {
        log.debug("Request to get Editorial : {}", id);
        Editorial editorial = editorialRepository.findOne(id);
        EditorialDTO editorialDTO = editorialMapper.editorialToEditorialDTO(editorial);
        return editorialDTO;
    }

    /**
     *  Delete the  editorial by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Editorial : {}", id);
        editorialRepository.delete(id);
    }
}
