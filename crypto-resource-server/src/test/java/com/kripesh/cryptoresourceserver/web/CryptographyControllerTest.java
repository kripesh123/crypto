package com.kripesh.cryptoresourceserver.web;

import com.kripesh.cryptoresourceserver.model.Cryptography;
import com.kripesh.cryptoresourceserver.service.CryptographyService;
import com.kripesh.cryptoresourceserver.web.dto.CryptographyDTO;
import com.kripesh.cryptoresourceserver.web.dto.InputDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CryptographyControllerTest extends BaseControllerTest{

    public static final String CRYPTOGRAPHY_PATH = "/cryptography";
    public static final String ID_PATH = "/{id}";
    public static final long ID = 1;

    private MockMvc mockMvc;

    @Mock
    private CryptographyService cryptographyService;

    @InjectMocks
    private CryptographyController cryptographyController;

    Cryptography cryptography;
    CryptographyDTO cryptographyDTO;
    InputDTO inputDTO;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(cryptographyController).build();
        cryptography =  new Cryptography("test","test-encoded-raw");
        cryptographyDTO = new CryptographyDTO(1,"test","test-encoded-raw");
        inputDTO = new InputDTO();
        inputDTO.setInput("Hello Estonia");
    }

    @Test
    public void testFindAll() throws Exception {
        when(cryptographyService.findAll()).thenReturn(Arrays.asList(cryptography));
        when(cryptographyService.entityToDto(cryptography)).thenReturn(cryptographyDTO);

        RequestBuilder requestBuilder  = MockMvcRequestBuilders
                .get(CRYPTOGRAPHY_PATH).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response =  mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);
        Assert.assertNotNull(response.getContentAsString());
        Assert.assertTrue(mapFromJson(response.getContentAsString(), Cryptography[].class).length > 0);
    }

    @Test
    public void testCreateCryptography() throws Exception {
        when(cryptographyService.createCryptography(any(String.class))).thenReturn(cryptography);

        RequestBuilder requestBuilder  = MockMvcRequestBuilders
                .post(CRYPTOGRAPHY_PATH).accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(inputDTO)).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =  mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);
        Assert.assertNotNull(response.getContentAsString());
        Assert.assertEquals("test", mapFromJson(response.getContentAsString(), Cryptography.class).getInputText());
        Assert.assertNotEquals("test", mapFromJson(response.getContentAsString(), Cryptography.class).getEncodedValue());
    }

    @Test
    public void testFindById() throws Exception {
        when(cryptographyService.findById(ID)).thenReturn(Optional.of(cryptography));
        when(cryptographyService.entityToDto(cryptography)).thenReturn(cryptographyDTO);

        RequestBuilder requestBuilder  = MockMvcRequestBuilders
                .get(CRYPTOGRAPHY_PATH+ID_PATH, ID).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =  mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);
        Assert.assertNotNull(response.getContentAsString());
        Assert.assertEquals("test", mapFromJson(response.getContentAsString(), Cryptography.class).getInputText());
    }

    @Test
    public void testFindByIdReturnsNotFound() throws Exception {
        when(cryptographyService.findById(ID)).thenReturn(Optional.empty());
        RequestBuilder requestBuilder  = MockMvcRequestBuilders
                .get(CRYPTOGRAPHY_PATH+ID_PATH, ID).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =  mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    public void testUpdate() throws Exception {
        Cryptography newCryptography = new Cryptography("test", "1234567890");

        Optional<Cryptography> optCryptography = Optional.of(cryptography);
        when(cryptographyService.findById(ID)).thenReturn(optCryptography);
        when(cryptographyService.update(optCryptography.get(), inputDTO.getInput())).thenReturn(newCryptography);
        RequestBuilder requestBuilder  = MockMvcRequestBuilders
                .put(CRYPTOGRAPHY_PATH+ID_PATH, ID).accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(inputDTO)).contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =  mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(200, status);
        Assert.assertNotNull(response.getContentAsString());
        Assert.assertNotEquals("test-encoded-raw", mapFromJson(response.getContentAsString(), Cryptography.class).getEncodedValue());
    }

    @Test
    public void testUpdateFail() throws Exception {
        Cryptography newCryptography = new Cryptography("test", "1234567890");
        Optional<Cryptography> optCryptography = Optional.empty();
        when(cryptographyService.findById(ID)).thenReturn(optCryptography);
        InputDTO inputDTO = new InputDTO();
        inputDTO.setInput("Hello Estonia");
        Mockito.lenient().when(cryptographyService.update(optCryptography.isPresent() ? optCryptography.get(): null, inputDTO.getInput())).thenReturn(newCryptography);
        RequestBuilder requestBuilder  = MockMvcRequestBuilders
                .put(CRYPTOGRAPHY_PATH+ID_PATH, ID).accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(inputDTO)).contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult =  mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =  mvcResult.getResponse();
        int status = response.getStatus();
        Assert.assertEquals(404, status);
    }


}
