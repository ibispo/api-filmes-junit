package com.bispo.filmes.model;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class FilmeModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String nome;

	public FilmeModel(Long codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
}
