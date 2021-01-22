package com.masivian.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masivian.prueba.entity.Bet;
import com.masivian.prueba.entity.Roulette;
import com.masivian.prueba.services.RouletteService;

@RestController
@RequestMapping("/")
public class RouletteController {
	@Autowired
	private RouletteService rouletteService;

	@PostMapping(value = "create")
	public ResponseEntity<Long> create(@RequestBody Roulette roulette) {
		Long idRouletteCreated = rouletteService.createRoulette(roulette);
		return ResponseEntity.ok(idRouletteCreated);
	}

	@PostMapping("openRoulette/{id}")
	public ResponseEntity<String> openRoulette(@PathVariable("id") Long id) {
		String stateRouleteInicied = rouletteService.openRoulette(id);
		return ResponseEntity.ok(stateRouleteInicied);
	}

	@PostMapping(value = "/createBet/{rouletteId}/{userId}")
	public ResponseEntity<String> createBet(@PathVariable("rouletteId") Long rouleteId,
			@PathVariable("userId") Long userId, @RequestBody Bet bet) {
		String state = "";
		state = rouletteService.crateBet(bet, userId, rouleteId);
		return ResponseEntity.ok(state);
	}

	@GetMapping(value = "/closeBet/{rouletteId}")
	public ResponseEntity<String> closeBet(@PathVariable("rouletteId") Long rouletteId) {
		String results = rouletteService.closeBets(rouletteId);
		return ResponseEntity.ok(results);
	}
	
	@GetMapping(value = "/findAllRoulette")
	public ResponseEntity<List <Roulette>> findAllRoulette(){
		List <Roulette> roulette = rouletteService.findAllRoulette();
		return ResponseEntity.ok(roulette);
	}
}
