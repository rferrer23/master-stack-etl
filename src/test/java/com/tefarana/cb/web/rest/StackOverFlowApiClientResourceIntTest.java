package com.tefarana.cb.web.rest;

import com.tefarana.cb.EtlBigdataApp;

import com.tefarana.cb.domain.StackOverFlowApiClient;
import com.tefarana.cb.domain.Extra;
import com.tefarana.cb.repository.StackOverFlowApiClientRepository;
import com.tefarana.cb.service.StackOverFlowApiClientService;
import com.tefarana.cb.service.dto.StackOverFlowApiClientDTO;
import com.tefarana.cb.service.mapper.StackOverFlowApiClientMapper;
import com.tefarana.cb.web.rest.errors.ExceptionTranslator;
import com.tefarana.cb.service.dto.StackOverFlowApiClientCriteria;
import com.tefarana.cb.service.StackOverFlowApiClientQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.tefarana.cb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StackOverFlowApiClientResource REST controller.
 *
 * @see StackOverFlowApiClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtlBigdataApp.class)
public class StackOverFlowApiClientResourceIntTest {

    private static final String DEFAULT_USERS = "AAAAAAAAAA";
    private static final String UPDATED_USERS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FIRST_PERIOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_PERIOD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_PERIOD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_PERIOD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NEXT_SEND_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXT_SEND_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    @Autowired
    private StackOverFlowApiClientRepository stackOverFlowApiClientRepository;

    @Mock
    private StackOverFlowApiClientRepository stackOverFlowApiClientRepositoryMock;

    @Autowired
    private StackOverFlowApiClientMapper stackOverFlowApiClientMapper;
    

    @Mock
    private StackOverFlowApiClientService stackOverFlowApiClientServiceMock;

    @Autowired
    private StackOverFlowApiClientService stackOverFlowApiClientService;

    @Autowired
    private StackOverFlowApiClientQueryService stackOverFlowApiClientQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStackOverFlowApiClientMockMvc;

    private StackOverFlowApiClient stackOverFlowApiClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StackOverFlowApiClientResource stackOverFlowApiClientResource = new StackOverFlowApiClientResource(stackOverFlowApiClientService, stackOverFlowApiClientQueryService);
        this.restStackOverFlowApiClientMockMvc = MockMvcBuilders.standaloneSetup(stackOverFlowApiClientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StackOverFlowApiClient createEntity(EntityManager em) {
        StackOverFlowApiClient stackOverFlowApiClient = new StackOverFlowApiClient()
            .users(DEFAULT_USERS)
            .firstPeriod(DEFAULT_FIRST_PERIOD)
            .lastPeriod(DEFAULT_LAST_PERIOD)
            .tags(DEFAULT_TAGS)
            .nextSendTime(DEFAULT_NEXT_SEND_TIME)
            .active(DEFAULT_ACTIVE)
            .secret(DEFAULT_SECRET);
        return stackOverFlowApiClient;
    }

    @Before
    public void initTest() {
        stackOverFlowApiClient = createEntity(em);
    }

    @Test
    @Transactional
    public void createStackOverFlowApiClient() throws Exception {
        int databaseSizeBeforeCreate = stackOverFlowApiClientRepository.findAll().size();

        // Create the StackOverFlowApiClient
        StackOverFlowApiClientDTO stackOverFlowApiClientDTO = stackOverFlowApiClientMapper.toDto(stackOverFlowApiClient);
        restStackOverFlowApiClientMockMvc.perform(post("/api/stack-over-flow-api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stackOverFlowApiClientDTO)))
            .andExpect(status().isCreated());

        // Validate the StackOverFlowApiClient in the database
        List<StackOverFlowApiClient> stackOverFlowApiClientList = stackOverFlowApiClientRepository.findAll();
        assertThat(stackOverFlowApiClientList).hasSize(databaseSizeBeforeCreate + 1);
        StackOverFlowApiClient testStackOverFlowApiClient = stackOverFlowApiClientList.get(stackOverFlowApiClientList.size() - 1);
        assertThat(testStackOverFlowApiClient.getUsers()).isEqualTo(DEFAULT_USERS);
        assertThat(testStackOverFlowApiClient.getFirstPeriod()).isEqualTo(DEFAULT_FIRST_PERIOD);
        assertThat(testStackOverFlowApiClient.getLastPeriod()).isEqualTo(DEFAULT_LAST_PERIOD);
        assertThat(testStackOverFlowApiClient.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testStackOverFlowApiClient.getNextSendTime()).isEqualTo(DEFAULT_NEXT_SEND_TIME);
        assertThat(testStackOverFlowApiClient.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testStackOverFlowApiClient.getSecret()).isEqualTo(DEFAULT_SECRET);
    }

    @Test
    @Transactional
    public void createStackOverFlowApiClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stackOverFlowApiClientRepository.findAll().size();

        // Create the StackOverFlowApiClient with an existing ID
        stackOverFlowApiClient.setId(1L);
        StackOverFlowApiClientDTO stackOverFlowApiClientDTO = stackOverFlowApiClientMapper.toDto(stackOverFlowApiClient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStackOverFlowApiClientMockMvc.perform(post("/api/stack-over-flow-api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stackOverFlowApiClientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StackOverFlowApiClient in the database
        List<StackOverFlowApiClient> stackOverFlowApiClientList = stackOverFlowApiClientRepository.findAll();
        assertThat(stackOverFlowApiClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClients() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stackOverFlowApiClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].users").value(hasItem(DEFAULT_USERS.toString())))
            .andExpect(jsonPath("$.[*].firstPeriod").value(hasItem(DEFAULT_FIRST_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].lastPeriod").value(hasItem(DEFAULT_LAST_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].nextSendTime").value(hasItem(DEFAULT_NEXT_SEND_TIME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET.toString())));
    }
    
    public void getAllStackOverFlowApiClientsWithEagerRelationshipsIsEnabled() throws Exception {
        StackOverFlowApiClientResource stackOverFlowApiClientResource = new StackOverFlowApiClientResource(stackOverFlowApiClientServiceMock, stackOverFlowApiClientQueryService);
        when(stackOverFlowApiClientServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restStackOverFlowApiClientMockMvc = MockMvcBuilders.standaloneSetup(stackOverFlowApiClientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients?eagerload=true"))
        .andExpect(status().isOk());

        verify(stackOverFlowApiClientServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllStackOverFlowApiClientsWithEagerRelationshipsIsNotEnabled() throws Exception {
        StackOverFlowApiClientResource stackOverFlowApiClientResource = new StackOverFlowApiClientResource(stackOverFlowApiClientServiceMock, stackOverFlowApiClientQueryService);
            when(stackOverFlowApiClientServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restStackOverFlowApiClientMockMvc = MockMvcBuilders.standaloneSetup(stackOverFlowApiClientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients?eagerload=true"))
        .andExpect(status().isOk());

            verify(stackOverFlowApiClientServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getStackOverFlowApiClient() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get the stackOverFlowApiClient
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients/{id}", stackOverFlowApiClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stackOverFlowApiClient.getId().intValue()))
            .andExpect(jsonPath("$.users").value(DEFAULT_USERS.toString()))
            .andExpect(jsonPath("$.firstPeriod").value(DEFAULT_FIRST_PERIOD.toString()))
            .andExpect(jsonPath("$.lastPeriod").value(DEFAULT_LAST_PERIOD.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.nextSendTime").value(DEFAULT_NEXT_SEND_TIME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET.toString()));
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where users equals to DEFAULT_USERS
        defaultStackOverFlowApiClientShouldBeFound("users.equals=" + DEFAULT_USERS);

        // Get all the stackOverFlowApiClientList where users equals to UPDATED_USERS
        defaultStackOverFlowApiClientShouldNotBeFound("users.equals=" + UPDATED_USERS);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByUsersIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where users in DEFAULT_USERS or UPDATED_USERS
        defaultStackOverFlowApiClientShouldBeFound("users.in=" + DEFAULT_USERS + "," + UPDATED_USERS);

        // Get all the stackOverFlowApiClientList where users equals to UPDATED_USERS
        defaultStackOverFlowApiClientShouldNotBeFound("users.in=" + UPDATED_USERS);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByUsersIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where users is not null
        defaultStackOverFlowApiClientShouldBeFound("users.specified=true");

        // Get all the stackOverFlowApiClientList where users is null
        defaultStackOverFlowApiClientShouldNotBeFound("users.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByFirstPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where firstPeriod equals to DEFAULT_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("firstPeriod.equals=" + DEFAULT_FIRST_PERIOD);

        // Get all the stackOverFlowApiClientList where firstPeriod equals to UPDATED_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("firstPeriod.equals=" + UPDATED_FIRST_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByFirstPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where firstPeriod in DEFAULT_FIRST_PERIOD or UPDATED_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("firstPeriod.in=" + DEFAULT_FIRST_PERIOD + "," + UPDATED_FIRST_PERIOD);

        // Get all the stackOverFlowApiClientList where firstPeriod equals to UPDATED_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("firstPeriod.in=" + UPDATED_FIRST_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByFirstPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where firstPeriod is not null
        defaultStackOverFlowApiClientShouldBeFound("firstPeriod.specified=true");

        // Get all the stackOverFlowApiClientList where firstPeriod is null
        defaultStackOverFlowApiClientShouldNotBeFound("firstPeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByFirstPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where firstPeriod greater than or equals to DEFAULT_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("firstPeriod.greaterOrEqualThan=" + DEFAULT_FIRST_PERIOD);

        // Get all the stackOverFlowApiClientList where firstPeriod greater than or equals to UPDATED_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("firstPeriod.greaterOrEqualThan=" + UPDATED_FIRST_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByFirstPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where firstPeriod less than or equals to DEFAULT_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("firstPeriod.lessThan=" + DEFAULT_FIRST_PERIOD);

        // Get all the stackOverFlowApiClientList where firstPeriod less than or equals to UPDATED_FIRST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("firstPeriod.lessThan=" + UPDATED_FIRST_PERIOD);
    }


    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByLastPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where lastPeriod equals to DEFAULT_LAST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("lastPeriod.equals=" + DEFAULT_LAST_PERIOD);

        // Get all the stackOverFlowApiClientList where lastPeriod equals to UPDATED_LAST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("lastPeriod.equals=" + UPDATED_LAST_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByLastPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where lastPeriod in DEFAULT_LAST_PERIOD or UPDATED_LAST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("lastPeriod.in=" + DEFAULT_LAST_PERIOD + "," + UPDATED_LAST_PERIOD);

        // Get all the stackOverFlowApiClientList where lastPeriod equals to UPDATED_LAST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("lastPeriod.in=" + UPDATED_LAST_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByLastPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where lastPeriod is not null
        defaultStackOverFlowApiClientShouldBeFound("lastPeriod.specified=true");

        // Get all the stackOverFlowApiClientList where lastPeriod is null
        defaultStackOverFlowApiClientShouldNotBeFound("lastPeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByLastPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where lastPeriod greater than or equals to DEFAULT_LAST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("lastPeriod.greaterOrEqualThan=" + DEFAULT_LAST_PERIOD);

        // Get all the stackOverFlowApiClientList where lastPeriod greater than or equals to UPDATED_LAST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("lastPeriod.greaterOrEqualThan=" + UPDATED_LAST_PERIOD);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByLastPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where lastPeriod less than or equals to DEFAULT_LAST_PERIOD
        defaultStackOverFlowApiClientShouldNotBeFound("lastPeriod.lessThan=" + DEFAULT_LAST_PERIOD);

        // Get all the stackOverFlowApiClientList where lastPeriod less than or equals to UPDATED_LAST_PERIOD
        defaultStackOverFlowApiClientShouldBeFound("lastPeriod.lessThan=" + UPDATED_LAST_PERIOD);
    }


    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where tags equals to DEFAULT_TAGS
        defaultStackOverFlowApiClientShouldBeFound("tags.equals=" + DEFAULT_TAGS);

        // Get all the stackOverFlowApiClientList where tags equals to UPDATED_TAGS
        defaultStackOverFlowApiClientShouldNotBeFound("tags.equals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByTagsIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where tags in DEFAULT_TAGS or UPDATED_TAGS
        defaultStackOverFlowApiClientShouldBeFound("tags.in=" + DEFAULT_TAGS + "," + UPDATED_TAGS);

        // Get all the stackOverFlowApiClientList where tags equals to UPDATED_TAGS
        defaultStackOverFlowApiClientShouldNotBeFound("tags.in=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where tags is not null
        defaultStackOverFlowApiClientShouldBeFound("tags.specified=true");

        // Get all the stackOverFlowApiClientList where tags is null
        defaultStackOverFlowApiClientShouldNotBeFound("tags.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByNextSendTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where nextSendTime equals to DEFAULT_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldBeFound("nextSendTime.equals=" + DEFAULT_NEXT_SEND_TIME);

        // Get all the stackOverFlowApiClientList where nextSendTime equals to UPDATED_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldNotBeFound("nextSendTime.equals=" + UPDATED_NEXT_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByNextSendTimeIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where nextSendTime in DEFAULT_NEXT_SEND_TIME or UPDATED_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldBeFound("nextSendTime.in=" + DEFAULT_NEXT_SEND_TIME + "," + UPDATED_NEXT_SEND_TIME);

        // Get all the stackOverFlowApiClientList where nextSendTime equals to UPDATED_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldNotBeFound("nextSendTime.in=" + UPDATED_NEXT_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByNextSendTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where nextSendTime is not null
        defaultStackOverFlowApiClientShouldBeFound("nextSendTime.specified=true");

        // Get all the stackOverFlowApiClientList where nextSendTime is null
        defaultStackOverFlowApiClientShouldNotBeFound("nextSendTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByNextSendTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where nextSendTime greater than or equals to DEFAULT_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldBeFound("nextSendTime.greaterOrEqualThan=" + DEFAULT_NEXT_SEND_TIME);

        // Get all the stackOverFlowApiClientList where nextSendTime greater than or equals to UPDATED_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldNotBeFound("nextSendTime.greaterOrEqualThan=" + UPDATED_NEXT_SEND_TIME);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByNextSendTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where nextSendTime less than or equals to DEFAULT_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldNotBeFound("nextSendTime.lessThan=" + DEFAULT_NEXT_SEND_TIME);

        // Get all the stackOverFlowApiClientList where nextSendTime less than or equals to UPDATED_NEXT_SEND_TIME
        defaultStackOverFlowApiClientShouldBeFound("nextSendTime.lessThan=" + UPDATED_NEXT_SEND_TIME);
    }


    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where active equals to DEFAULT_ACTIVE
        defaultStackOverFlowApiClientShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the stackOverFlowApiClientList where active equals to UPDATED_ACTIVE
        defaultStackOverFlowApiClientShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultStackOverFlowApiClientShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the stackOverFlowApiClientList where active equals to UPDATED_ACTIVE
        defaultStackOverFlowApiClientShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where active is not null
        defaultStackOverFlowApiClientShouldBeFound("active.specified=true");

        // Get all the stackOverFlowApiClientList where active is null
        defaultStackOverFlowApiClientShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsBySecretIsEqualToSomething() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where secret equals to DEFAULT_SECRET
        defaultStackOverFlowApiClientShouldBeFound("secret.equals=" + DEFAULT_SECRET);

        // Get all the stackOverFlowApiClientList where secret equals to UPDATED_SECRET
        defaultStackOverFlowApiClientShouldNotBeFound("secret.equals=" + UPDATED_SECRET);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsBySecretIsInShouldWork() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where secret in DEFAULT_SECRET or UPDATED_SECRET
        defaultStackOverFlowApiClientShouldBeFound("secret.in=" + DEFAULT_SECRET + "," + UPDATED_SECRET);

        // Get all the stackOverFlowApiClientList where secret equals to UPDATED_SECRET
        defaultStackOverFlowApiClientShouldNotBeFound("secret.in=" + UPDATED_SECRET);
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsBySecretIsNullOrNotNull() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        // Get all the stackOverFlowApiClientList where secret is not null
        defaultStackOverFlowApiClientShouldBeFound("secret.specified=true");

        // Get all the stackOverFlowApiClientList where secret is null
        defaultStackOverFlowApiClientShouldNotBeFound("secret.specified=false");
    }

    @Test
    @Transactional
    public void getAllStackOverFlowApiClientsByExtraIsEqualToSomething() throws Exception {
        // Initialize the database
        Extra extra = ExtraResourceIntTest.createEntity(em);
        em.persist(extra);
        em.flush();
        stackOverFlowApiClient.addExtra(extra);
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);
        Long extraId = extra.getId();

        // Get all the stackOverFlowApiClientList where extra equals to extraId
        defaultStackOverFlowApiClientShouldBeFound("extraId.equals=" + extraId);

        // Get all the stackOverFlowApiClientList where extra equals to extraId + 1
        defaultStackOverFlowApiClientShouldNotBeFound("extraId.equals=" + (extraId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStackOverFlowApiClientShouldBeFound(String filter) throws Exception {
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stackOverFlowApiClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].users").value(hasItem(DEFAULT_USERS.toString())))
            .andExpect(jsonPath("$.[*].firstPeriod").value(hasItem(DEFAULT_FIRST_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].lastPeriod").value(hasItem(DEFAULT_LAST_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].nextSendTime").value(hasItem(DEFAULT_NEXT_SEND_TIME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET.toString())));

        // Check, that the count call also returns 1
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStackOverFlowApiClientShouldNotBeFound(String filter) throws Exception {
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStackOverFlowApiClient() throws Exception {
        // Get the stackOverFlowApiClient
        restStackOverFlowApiClientMockMvc.perform(get("/api/stack-over-flow-api-clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStackOverFlowApiClient() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        int databaseSizeBeforeUpdate = stackOverFlowApiClientRepository.findAll().size();

        // Update the stackOverFlowApiClient
        StackOverFlowApiClient updatedStackOverFlowApiClient = stackOverFlowApiClientRepository.findById(stackOverFlowApiClient.getId()).get();
        // Disconnect from session so that the updates on updatedStackOverFlowApiClient are not directly saved in db
        em.detach(updatedStackOverFlowApiClient);
        updatedStackOverFlowApiClient
            .users(UPDATED_USERS)
            .firstPeriod(UPDATED_FIRST_PERIOD)
            .lastPeriod(UPDATED_LAST_PERIOD)
            .tags(UPDATED_TAGS)
            .nextSendTime(UPDATED_NEXT_SEND_TIME)
            .active(UPDATED_ACTIVE)
            .secret(UPDATED_SECRET);
        StackOverFlowApiClientDTO stackOverFlowApiClientDTO = stackOverFlowApiClientMapper.toDto(updatedStackOverFlowApiClient);

        restStackOverFlowApiClientMockMvc.perform(put("/api/stack-over-flow-api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stackOverFlowApiClientDTO)))
            .andExpect(status().isOk());

        // Validate the StackOverFlowApiClient in the database
        List<StackOverFlowApiClient> stackOverFlowApiClientList = stackOverFlowApiClientRepository.findAll();
        assertThat(stackOverFlowApiClientList).hasSize(databaseSizeBeforeUpdate);
        StackOverFlowApiClient testStackOverFlowApiClient = stackOverFlowApiClientList.get(stackOverFlowApiClientList.size() - 1);
        assertThat(testStackOverFlowApiClient.getUsers()).isEqualTo(UPDATED_USERS);
        assertThat(testStackOverFlowApiClient.getFirstPeriod()).isEqualTo(UPDATED_FIRST_PERIOD);
        assertThat(testStackOverFlowApiClient.getLastPeriod()).isEqualTo(UPDATED_LAST_PERIOD);
        assertThat(testStackOverFlowApiClient.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testStackOverFlowApiClient.getNextSendTime()).isEqualTo(UPDATED_NEXT_SEND_TIME);
        assertThat(testStackOverFlowApiClient.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testStackOverFlowApiClient.getSecret()).isEqualTo(UPDATED_SECRET);
    }

    @Test
    @Transactional
    public void updateNonExistingStackOverFlowApiClient() throws Exception {
        int databaseSizeBeforeUpdate = stackOverFlowApiClientRepository.findAll().size();

        // Create the StackOverFlowApiClient
        StackOverFlowApiClientDTO stackOverFlowApiClientDTO = stackOverFlowApiClientMapper.toDto(stackOverFlowApiClient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStackOverFlowApiClientMockMvc.perform(put("/api/stack-over-flow-api-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stackOverFlowApiClientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StackOverFlowApiClient in the database
        List<StackOverFlowApiClient> stackOverFlowApiClientList = stackOverFlowApiClientRepository.findAll();
        assertThat(stackOverFlowApiClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStackOverFlowApiClient() throws Exception {
        // Initialize the database
        stackOverFlowApiClientRepository.saveAndFlush(stackOverFlowApiClient);

        int databaseSizeBeforeDelete = stackOverFlowApiClientRepository.findAll().size();

        // Get the stackOverFlowApiClient
        restStackOverFlowApiClientMockMvc.perform(delete("/api/stack-over-flow-api-clients/{id}", stackOverFlowApiClient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StackOverFlowApiClient> stackOverFlowApiClientList = stackOverFlowApiClientRepository.findAll();
        assertThat(stackOverFlowApiClientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StackOverFlowApiClient.class);
        StackOverFlowApiClient stackOverFlowApiClient1 = new StackOverFlowApiClient();
        stackOverFlowApiClient1.setId(1L);
        StackOverFlowApiClient stackOverFlowApiClient2 = new StackOverFlowApiClient();
        stackOverFlowApiClient2.setId(stackOverFlowApiClient1.getId());
        assertThat(stackOverFlowApiClient1).isEqualTo(stackOverFlowApiClient2);
        stackOverFlowApiClient2.setId(2L);
        assertThat(stackOverFlowApiClient1).isNotEqualTo(stackOverFlowApiClient2);
        stackOverFlowApiClient1.setId(null);
        assertThat(stackOverFlowApiClient1).isNotEqualTo(stackOverFlowApiClient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StackOverFlowApiClientDTO.class);
        StackOverFlowApiClientDTO stackOverFlowApiClientDTO1 = new StackOverFlowApiClientDTO();
        stackOverFlowApiClientDTO1.setId(1L);
        StackOverFlowApiClientDTO stackOverFlowApiClientDTO2 = new StackOverFlowApiClientDTO();
        assertThat(stackOverFlowApiClientDTO1).isNotEqualTo(stackOverFlowApiClientDTO2);
        stackOverFlowApiClientDTO2.setId(stackOverFlowApiClientDTO1.getId());
        assertThat(stackOverFlowApiClientDTO1).isEqualTo(stackOverFlowApiClientDTO2);
        stackOverFlowApiClientDTO2.setId(2L);
        assertThat(stackOverFlowApiClientDTO1).isNotEqualTo(stackOverFlowApiClientDTO2);
        stackOverFlowApiClientDTO1.setId(null);
        assertThat(stackOverFlowApiClientDTO1).isNotEqualTo(stackOverFlowApiClientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stackOverFlowApiClientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stackOverFlowApiClientMapper.fromId(null)).isNull();
    }
}
