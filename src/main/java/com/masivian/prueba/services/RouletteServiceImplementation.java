package com.masivian.prueba.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masivian.prueba.complements.States;
import com.masivian.prueba.entity.Bet;
import com.masivian.prueba.entity.Roulette;
import com.masivian.prueba.entity.User;
import com.masivian.prueba.repository.BetRepository;
import com.masivian.prueba.repository.RouletteRepository;
import com.masivian.prueba.repository.UserRepository;

@Service
public class RouletteServiceImplementation implements RouletteService {
	private static final String RESULT = "/////////////// RESULTADO ///////////////\n" +
	                "//////// EL NÚMERO GANADOR ES: %s ////////\n\n" +
	                "/////////// LOS GANADORES SON ///////////\n\n%s";
	private static final String NUMBER_WINNER = "  - El usuario %s apostó %s al número %s que es el GANADOR y ganó %s\n";
	private static final String COLOR_WINNER = "  - El usuario %s apostó %s al color %s que es el GANADOR y ganó %s\n";
	
	@Autowired
	private RouletteRepository rouletteRepository;	
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private BetRepository betRepository;

	@Override
	public Long createRoulette(Roulette roulette) {
		rouletteRepository.save(roulette);
		return roulette.getId();
	}

	@Override
	public String openRoulette(Long id) {
		String state = null;
		Optional<Roulette> optional = rouletteRepository.findById(id);
		if (optional.isPresent()) {
			Roulette roulette = optional.get();
			roulette.setState(States.OPENROULETTE.toString());
			rouletteRepository.save(roulette);
			state = roulette.getState();
		}
		return state;
	}

	@Override
	public String crateBet(Bet bet, Long userId, Long rouletteId) {
		String result = null;
		Optional<User> userOptional = userRepository.findById(userId);
		Optional<Roulette> rouletteOptional = rouletteRepository.findById(rouletteId);
		if (userOptional.isPresent() && rouletteOptional.isPresent()) {
			User userFound = userOptional.get();
			Roulette rouletteFound = rouletteOptional.get();
			if (rouletteFound.getState().equals(States.OPENROULETTE.toString())) {
				if (isANumber(bet.getStakeNumber()) && validateStakeValue(bet.getStakeValue()) 
						&& validateBetNumber(bet.getStakeNumber())
						|| (!bet.getColor().isEmpty() && validateColorBet(bet.getColor()))) {
					bet.setRoulette(rouletteFound);
					bet.setUser(userFound);
					betRepository.save(bet);
					result = States.SUCESSFUL.toString();
				} else {
					result = States.RULESNOTCUMPLY.toString();
				}
			} else {
				result = States.NOTPOSIBLETOCREATE.toString();
			}
		}
		return result;
	}

	@Override
	public String closeBets(Long rouletteId) {
		Optional <Roulette> optionalRoulette = rouletteRepository.findById(rouletteId);
		if(optionalRoulette.isPresent()) {
			Roulette rouletteFound = optionalRoulette.get();
			rouletteFound.setState(States.CLOSEROULETTE.toString());
			rouletteRepository.save(rouletteFound);
			int randomNumber = (int)(Math.random()*37);
			List<Bet> betFound = betRepository.findByRouletteId(rouletteId);
			return betWinners(betFound, 2);
		}
		return null;
	}
		
	@Override
	public List<Roulette> findAllRoulette() {
		return rouletteRepository.findAll();
	}
	
	public boolean validateBetNumber(int number) {
		boolean result = false;
		if (number >= 0 && number <= 36)
			result = true;
		return result;
	}

	public boolean validateColorBet(String color) {
		boolean result = false;
		if (color.equalsIgnoreCase("negro") || color.equalsIgnoreCase("rojo"))
			result = true;
		return result;
	}

	public boolean validateStakeValue(double value) {
		boolean result = false;
		if (value > 0 && value <= 10000)
			result = true;
		return result;
	}

	public boolean isANumber(Integer number) {
		boolean result = false;
		if(number!=null)
			result = true;
		return result;
	}
	
	public String betWinners(List<Bet> listAllBets, int winnerNumber) {
		String result = "";
		String colorWinner = colorBet(winnerNumber);
		List<String> winnerNumbers = listAllBets.stream().filter(bet -> (null != bet.getStakeNumber()
				&& bet.getStakeNumber() == winnerNumber))
				.map(bet -> registerWinnerByNumber(bet, winnerNumber)).collect(Collectors.toList());
		
		List<String> winnerColors = listAllBets.stream().filter(bet -> (null == bet.getStakeNumber()
				&& bet.getColor().equalsIgnoreCase(colorWinner)))
				.map(bet -> registerWinnerByColor(bet, colorWinner)).collect(Collectors.toList());
		winnerNumbers.addAll(winnerColors);
		for(String bet : winnerNumbers) {
			result += bet;
		}
		return String.format(RESULT, winnerNumber, result);
	}
	
	public String colorBet(int stakeNumber) {
		String color = "negro";
		if(stakeNumber % 2 == 0)
			color = "rojo";
		return color;
	}
	
	private String registerWinnerByNumber(Bet bet, int winnerNumber) {
		return String.format(NUMBER_WINNER, bet.getUser().getName(), bet.getStakeValue(), winnerNumber, bet.getStakeValue() * 5);
	}
	
	private String registerWinnerByColor(Bet bet, String winnerColor) {
		return String.format(COLOR_WINNER, bet.getUser().getName(), bet.getStakeValue(), winnerColor, bet.getStakeValue() * 1.8);
	}
}
