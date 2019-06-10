package com.kripesh.cryptoresourceserver.service;

import com.kripesh.cryptoresourceserver.model.Cryptography;
import com.kripesh.cryptoresourceserver.repository.CryptographyRepository;
import com.kripesh.cryptoresourceserver.web.dto.CryptographyDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CryptographyServiceTest {

    String input, encodedValue;
    Cryptography cryptography;

    @Mock
    private CryptographyRepository cryptographyRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private CryptographyService cryptographyService;

    @Before
    public void setUp(){
        input = "Hello Europe";
        encodedValue = "12345@#QWERTYUIOP";
        cryptography = new Cryptography(input, encodedValue);
    }

    @Test
    public void testCreateCryptography(){
        when(cryptographyRepository.save(any(Cryptography.class))).thenReturn(cryptography);
        Cryptography newCryptography = cryptographyService.createCryptography(input);
        assertEquals("Hello Europe", newCryptography.getInputText());
        assertNotEquals("Hello Europe", newCryptography.getEncodedValue());
    }

    @Test
    public void createCryptographyObjFromInput(){
        Cryptography newCryptography = cryptographyService.createObj(input);
        assertTrue(newCryptography != null);
    }

    @Test
    public void testListOfCryptography(){
        when(cryptographyRepository.findAll()).thenReturn(Arrays.asList(cryptography));
        List<Cryptography> cryptographyList = cryptographyService.findAll();
        assertTrue(cryptographyList.size() > 0);
        assertNotNull(cryptographyList);
    }

    @Test
    public void testFindByCryptographyId(){
        when(cryptographyRepository.findById(1l)).thenReturn(Optional.of(cryptography));
        Cryptography optionalCryptography = cryptographyService.findById(1l).get();
        assertEquals(input, optionalCryptography.getInputText());
    }

    @Test
    public void testUpdateCryptography(){
        when(cryptographyRepository.save(any(Cryptography.class))).thenReturn(cryptography);
        when(passwordEncoder.encode(encodedValue)).thenReturn("qwertyuioplkjhgfdsa");
        Cryptography newCryptography = cryptographyService.update(cryptography, encodedValue);
        assertTrue(!encodedValue.equals(newCryptography.getEncodedValue()));
    }

    @Test
    public void testInputEncoding() {
        when(passwordEncoder.encode(input)).thenReturn(encodedValue);
        String newEncodedValue = cryptographyService.encodeInput(input);
        assertNotNull(newEncodedValue);
    }

    @Test
    public void testSaveCryptography(){
        when(cryptographyRepository.save(cryptography)).thenReturn(cryptography);
        Cryptography newCryptography = cryptographyService.save(cryptography);
        assertEquals("Hello Europe", newCryptography.getInputText());
    }

    @Test
    public void testEntityToDTO(){
        cryptography.setId(1l);
        CryptographyDTO cryptographyDTO = cryptographyService.entityToDto(cryptography);
        assertEquals(cryptographyDTO.getInputText(),cryptography.getInputText());
    }



}
