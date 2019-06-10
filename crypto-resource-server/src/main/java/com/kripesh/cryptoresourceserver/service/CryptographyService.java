package com.kripesh.cryptoresourceserver.service;

import com.kripesh.cryptoresourceserver.model.Cryptography;
import com.kripesh.cryptoresourceserver.repository.CryptographyRepository;
import com.kripesh.cryptoresourceserver.web.dto.CryptographyDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CryptographyService {

    private final CryptographyRepository cryptographyRepository;
    private final PasswordEncoder passwordEncoder;

    public CryptographyService(final CryptographyRepository cryptographyRepository, final PasswordEncoder passwordEncoder){
        this.cryptographyRepository = cryptographyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Cryptography createCryptography(String input){
        return this.save(createObj(input));
    }

    public Cryptography update(Cryptography cryptography, String input){
        String encodedValue = encodeInput(input);
        cryptography.setEncodedValue(encodedValue);
        return this.save(cryptography);
    }

    public Optional<Cryptography> findById(Long id){
        return cryptographyRepository.findById(id);
    }

    public List<Cryptography> findAll(){
        return cryptographyRepository.findAll();
    }

    public Cryptography save(Cryptography cryptography)
    {
        return cryptographyRepository.save(cryptography);
    }

    public String encodeInput(String input){
        return passwordEncoder.encode(input);
    }

    public Cryptography createObj(String input){
        return new Cryptography(input,encodeInput(input));
    }

    public CryptographyDTO entityToDto(Cryptography cryptography) {
        return new CryptographyDTO(cryptography.getId(),cryptography.getInputText(),cryptography.getEncodedValue());
    }


}
