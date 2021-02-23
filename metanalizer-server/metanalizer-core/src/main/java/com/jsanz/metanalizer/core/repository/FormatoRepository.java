package com.jsanz.metanalizer.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsanz.metanalizer.core.entity.Formato;

@Repository
public interface FormatoRepository extends JpaRepository<Formato, Integer>{

}
