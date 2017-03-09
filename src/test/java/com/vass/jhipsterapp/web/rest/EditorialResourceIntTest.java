package com.vass.jhipsterapp.web.rest;

import com.vass.jhipsterapp.MyJhipsterApp;

import com.vass.jhipsterapp.domain.Editorial;
import com.vass.jhipsterapp.repository.EditorialRepository;
import com.vass.jhipsterapp.service.EditorialService;
import com.vass.jhipsterapp.service.dto.EditorialDTO;
import com.vass.jhipsterapp.service.mapper.EditorialMapper;
import com.vass.jhipsterapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EditorialResource REST controller.
 *
 * @see EditorialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyJhipsterApp.class)
public class EditorialResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EditorialRepository editorialRepository;

    @Autowired
    private EditorialMapper editorialMapper;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEditorialMockMvc;

    private Editorial editorial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EditorialResource editorialResource = new EditorialResource(editorialService);
        this.restEditorialMockMvc = MockMvcBuilders.standaloneSetup(editorialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Editorial createEntity(EntityManager em) {
        Editorial editorial = new Editorial()
                .nombre(DEFAULT_NOMBRE);
        return editorial;
    }

    @Before
    public void initTest() {
        editorial = createEntity(em);
    }

    @Test
    @Transactional
    public void createEditorial() throws Exception {
        int databaseSizeBeforeCreate = editorialRepository.findAll().size();

        // Create the Editorial
        EditorialDTO editorialDTO = editorialMapper.editorialToEditorialDTO(editorial);

        restEditorialMockMvc.perform(post("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editorialDTO)))
            .andExpect(status().isCreated());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeCreate + 1);
        Editorial testEditorial = editorialList.get(editorialList.size() - 1);
        assertThat(testEditorial.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createEditorialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = editorialRepository.findAll().size();

        // Create the Editorial with an existing ID
        Editorial existingEditorial = new Editorial();
        existingEditorial.setId(1L);
        EditorialDTO existingEditorialDTO = editorialMapper.editorialToEditorialDTO(existingEditorial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEditorialMockMvc.perform(post("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEditorialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEditorials() throws Exception {
        // Initialize the database
        editorialRepository.saveAndFlush(editorial);

        // Get all the editorialList
        restEditorialMockMvc.perform(get("/api/editorials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(editorial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getEditorial() throws Exception {
        // Initialize the database
        editorialRepository.saveAndFlush(editorial);

        // Get the editorial
        restEditorialMockMvc.perform(get("/api/editorials/{id}", editorial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(editorial.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEditorial() throws Exception {
        // Get the editorial
        restEditorialMockMvc.perform(get("/api/editorials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEditorial() throws Exception {
        // Initialize the database
        editorialRepository.saveAndFlush(editorial);
        int databaseSizeBeforeUpdate = editorialRepository.findAll().size();

        // Update the editorial
        Editorial updatedEditorial = editorialRepository.findOne(editorial.getId());
        updatedEditorial
                .nombre(UPDATED_NOMBRE);
        EditorialDTO editorialDTO = editorialMapper.editorialToEditorialDTO(updatedEditorial);

        restEditorialMockMvc.perform(put("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editorialDTO)))
            .andExpect(status().isOk());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeUpdate);
        Editorial testEditorial = editorialList.get(editorialList.size() - 1);
        assertThat(testEditorial.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingEditorial() throws Exception {
        int databaseSizeBeforeUpdate = editorialRepository.findAll().size();

        // Create the Editorial
        EditorialDTO editorialDTO = editorialMapper.editorialToEditorialDTO(editorial);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEditorialMockMvc.perform(put("/api/editorials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editorialDTO)))
            .andExpect(status().isCreated());

        // Validate the Editorial in the database
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEditorial() throws Exception {
        // Initialize the database
        editorialRepository.saveAndFlush(editorial);
        int databaseSizeBeforeDelete = editorialRepository.findAll().size();

        // Get the editorial
        restEditorialMockMvc.perform(delete("/api/editorials/{id}", editorial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Editorial> editorialList = editorialRepository.findAll();
        assertThat(editorialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Editorial.class);
    }
}
