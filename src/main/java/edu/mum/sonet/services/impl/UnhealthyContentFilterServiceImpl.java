package edu.mum.sonet.services.impl;

import edu.mum.sonet.models.UnhealthyWord;
import edu.mum.sonet.repositories.UnhealthyWordRepository;
import edu.mum.sonet.services.UnhealthyContentFilterService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Jonathan on 12/12/2019.
 */

@Service
public class UnhealthyContentFilterServiceImpl implements UnhealthyContentFilterService {

	private final UnhealthyWordRepository unhealthyWordRepository;

	private List<String> words;

	public UnhealthyContentFilterServiceImpl(UnhealthyWordRepository unhealthyWordRepository) {
		this.unhealthyWordRepository = unhealthyWordRepository;
		this.words = unhealthyWordRepository.findAll()
				.stream()
				.map(UnhealthyWord::getWord).collect(Collectors.toList());
	}

	@Override
	public boolean hasUnhealthyContent(String input) {
		if(input == null) {
			return false;
		}
		///> Loop over the list of unhealthy words we have in db and check if the input has any of them
		return words.parallelStream().anyMatch(input::contains);

	}

	public String filterText(String input, String username) {
		boolean badWords = hasUnhealthyContent(input);
		if(badWords) {
			return "This message was blocked because a bad word was found. If you believe this word should not be blocked, please message support.";
		}
		return input;
	}
}
