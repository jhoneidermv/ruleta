package com.masivian.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masivian.prueba.entity.Roulette;

@Repository
public interface RouletteRepository extends JpaRepository<Roulette, Long>{

}
