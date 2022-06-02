package com.bispo.filmes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bispo.filmes.model.FilmeModel;
import com.bispo.filmes.service.FilmeService;

@RestController
public class FilmeController {

	@Autowired
	private FilmeService filmeService;

	@GetMapping("/filmes/{cod}")
	public ResponseEntity<FilmeModel> buscarFilme(@PathVariable Long cod) {

		if (cod < 0) {
			return ResponseEntity.badRequest().build();
		}
		
		Optional<FilmeModel> filme = filmeService.obterFilme(cod);

		if (filme == null || !filme.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(filme.get());

	}

	
	
	
	
	
	
	
	
	
}
