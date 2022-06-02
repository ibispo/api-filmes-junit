package com.bispo.filmes.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bispo.filmes.model.FilmeModel;

@Service
public class FilmeService {

	public Optional<FilmeModel> obterFilme(Long cod) {

		if ( cod > 100 ) {
			return null;
		}
		
		FilmeModel filmeNovo = new FilmeModel(cod, "A volta dos que não foram");
		return Optional.of(filmeNovo);
		
	}

}
