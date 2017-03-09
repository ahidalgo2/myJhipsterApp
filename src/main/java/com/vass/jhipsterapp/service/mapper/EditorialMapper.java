package com.vass.jhipsterapp.service.mapper;

import com.vass.jhipsterapp.domain.*;
import com.vass.jhipsterapp.service.dto.EditorialDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Editorial and its DTO EditorialDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EditorialMapper {

    EditorialDTO editorialToEditorialDTO(Editorial editorial);

    List<EditorialDTO> editorialsToEditorialDTOs(List<Editorial> editorials);

    @Mapping(target = "books", ignore = true)
    Editorial editorialDTOToEditorial(EditorialDTO editorialDTO);

    List<Editorial> editorialDTOsToEditorials(List<EditorialDTO> editorialDTOs);
}
