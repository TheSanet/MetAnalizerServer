package com.jsanz.metanalizer.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Fuente;

@Repository
public interface FuenteRepository extends JpaRepository<Fuente, Integer> {

}
