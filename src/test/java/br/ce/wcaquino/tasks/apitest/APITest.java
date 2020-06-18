package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8080/tasks-backend";
	}
	
	@Test
	public void DeveRetornarTarefas() {
		RestAssured.given()
		.when()
		    .get("/todo")
		.then()
		    .statusCode(200)
		;
	}
	
	@Test
	public void DeveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("	{\"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void NaoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("	{\"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		//inserir
		Integer id = RestAssured.given()
			.body("	{\"task\": \"Tarefa para remo��o\", \"dueDate\": \"2020-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
	//		.log().all()
			.statusCode(201)
			.extract().path("id")
		;
		System.out.println("id");
		
	
		//remover
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204)
			;
	}
}

	

