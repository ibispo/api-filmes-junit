package com.bispo.filmes.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.bispo.filmes.model.FilmeModel;
import com.bispo.filmes.service.FilmeService;

import io.restassured.http.ContentType;

@WebMvcTest
@DisplayName("Ola___________________FilmeControllerTest")
public class FilmeControllerTest {

	@Autowired
	private FilmeController filmeController;

	/*
	 * Não podemos usar a implementação real, pois essa contém acesso a base de dados anyway...
	 * Se o teste fosse assim, teriamos o cenário de "teste integrado", o que difere de "teste unitário".
	 * --------------
	 * Por isso "MOCK". 
	 */
	@MockBean
	private FilmeService filmeService;
	
	/*
	 * Essa configuração é para evitar o Spring subir todas as instâncias não
	 * desejadas no momento do teste Eu informo ao Spring que para este test, eu
	 * preciso apenas de um determinado controller ou service
	 * 
	 * Não pode ser @BeforeAll ----------------------- Se for @BeforeAll, o método
	 * tem que ser static, pois não pode depender da instância da classe
	 */

	@BeforeEach
	public void setup() {
		standaloneSetup(this.filmeController); // Uso do objeto real
	}

	/*
	 * Convenção: O que deve retornar, quando realizar ação
	 * 
	 * Método original: buscarFilme() Método de teste: deveXXXXXXXX_QuandoYYYYYYY(),
	 * onde
	 * 
	 * XXXXXXXX -> Retornar Sucesso Retornar €100,00 Não retornar nada however...
	 * 
	 * YYYYYYYY -> Nome do método a set testado
	 */
	
	
	@Test
	public void deveRetornarSucesso_QuandoObterFilme() {

		Long idFilme = 1L;
		
		// Mockito
		when(this.filmeService.obterFilme(idFilme))
			.thenReturn( Optional.of( getFilme(idFilme) ) );
		
		// RestAssuredMockMvc
		given().
			accept(ContentType.JSON).
		when().
			get("/filmes/{cod}", idFilme).
		then().
			statusCode(HttpStatus.OK.value());

	}
	
	private FilmeModel getFilme(Long idFilm) {

		FilmeModel filme = new FilmeModel(idFilm, "A volta (teste)");
		
	//	FilmeModel filme = mock(FilmeModel.class);
	//	when(filme.getCodigo()).thenReturn(idFilm);
		
		return filme;
	}

	@Test
	public void deveRetornarNaoEncontrado_QuandoObterFilme() {

		Long idFilme = 5L;
		
		when(this.filmeService.obterFilme(idFilme))
			.thenReturn(null);
		
		given()
			.accept(ContentType.JSON)
		.when()
		 	.get("/filmes/{cod}", idFilme)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
		
	}
	
	
	@Test
	@DisplayName("Deve retornar BAD REQUEST quando obter filme")
	public void deveRetornarBadRequest_QuandoObterFilme() {
		
		Long idFilme = -36L;
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/filmes/{cod}", idFilme)
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
		
		verify(this.filmeService, never()).obterFilme(idFilme);
		
	//	verify(this.filmeService, times(2)).obterFilme(idFilme);
		
		
	}
	
	
	
	
	
	
}
