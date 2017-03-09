package com.vass.jhipsterapp.service.mapper;

import com.vass.jhipsterapp.domain.*;
import com.vass.jhipsterapp.service.dto.BookDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Book and its DTO BookDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookMapper {

    @Mapping(source = "editorial.id", target = "editorialId")
    BookDTO bookToBookDTO(Book book);

    List<BookDTO> booksToBookDTOs(List<Book> books);

    @Mapping(source = "editorialId", target = "editorial")
    Book bookDTOToBook(BookDTO bookDTO);

    List<Book> bookDTOsToBooks(List<BookDTO> bookDTOs);

    default Editorial editorialFromId(Long id) {
        if (id == null) {
            return null;
        }
        Editorial editorial = new Editorial();
        editorial.setId(id);
        return editorial;
    }
}
