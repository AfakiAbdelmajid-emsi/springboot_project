package com.commercial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.commercial.entities.TousCommandes;

public interface TousCommandesRepository extends JpaRepository<TousCommandes, Long> {}
