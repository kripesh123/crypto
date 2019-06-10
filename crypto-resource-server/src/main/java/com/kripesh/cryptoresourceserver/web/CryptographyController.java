package com.kripesh.cryptoresourceserver.web;

import com.kripesh.cryptoresourceserver.model.Cryptography;
import com.kripesh.cryptoresourceserver.service.CryptographyService;
import com.kripesh.cryptoresourceserver.web.dto.CryptographyDTO;
import com.kripesh.cryptoresourceserver.web.dto.InputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/cryptography")
public class CryptographyController {

    private final CryptographyService cryptographyService;

    public CryptographyController(final CryptographyService cryptographyService) {
        this.cryptographyService = cryptographyService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CryptographyDTO>> findAll(){
        return ResponseEntity.ok(cryptographyService.findAll().stream().map(cryptographyService::entityToDto).collect(Collectors.toList()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Cryptography> createCryptography(@RequestBody InputDTO inputDTO){
        return ResponseEntity.ok(cryptographyService.createCryptography(inputDTO.getInput()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CryptographyDTO> findById(@PathVariable Long id){
        Optional<Cryptography> cryptography = cryptographyService.findById(id);
        if (cryptography.isPresent()) {
            return ResponseEntity.ok(cryptographyService.entityToDto(cryptography.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Cryptography> update(@PathVariable Long id, @RequestBody InputDTO inputDTO){
        Optional<Cryptography> cryptography =cryptographyService.findById(id);
        if(cryptography.isPresent()){
            return ResponseEntity.ok(cryptographyService.update(cryptography.get(), inputDTO.getInput()));
        }
        return ResponseEntity.notFound().build();
    }




}
