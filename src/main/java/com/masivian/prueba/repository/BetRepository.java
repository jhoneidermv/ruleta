package com.masivian.prueba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masivian.prueba.entity.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {	
	public List<Bet> findByRouletteId(Long id);
}