package com.masivian.prueba.services;

import java.util.List;

import com.masivian.prueba.entity.Bet;
import com.masivian.prueba.entity.Roulette;

public interface RouletteService {
	Long createRoulette(Roulette roulette);
	String openRoulette(Long id);
	String crateBet(Bet bet, Long userId, Long rouletteId);
	String closeBets(Long rouletteId);
	List<Roulette> findAllRoulette();
}
