package com.kripesh.cryptoresourceserver.repository;

import com.kripesh.cryptoresourceserver.model.Cryptography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptographyRepository extends JpaRepository<Cryptography, Long> {
}
