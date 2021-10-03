package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Admin;
import domain.Bezeroa;
import domain.Pertsona;
import exceptions.UserAlreadyExist;

public class RegisterBLMockTest {
	private BLFacadeImplementation sut;
	@Mock
	private DataAccess dao;
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sut = new BLFacadeImplementation(dao);
	}
	

	@Test
	@DisplayName("Test 0:User name Not Exist on DataBase ")
	public void test1() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2022");
		try {
			Mockito.doThrow(new UserAlreadyExist()).when(dao).register("Tarek", "Chamkhi", "Ermina", "Tarek12301", "aaaaaaaa", "123456789", "Tarek12301@gmail.com",
				oneDate, "Bezeroa");
		} catch (UserAlreadyExist e) {
			fail("Algo ha ido mal en el metodo");
		}
		assertThrows(UserAlreadyExist.class,()-> sut.register("Tarek", "Chamkhi", "Ermina", "Tarek12301", "aaaaaaaa", "123456789", "Tarek12301@gmail.com",
				oneDate, "Bezeroa"));
	}
	
	
	@Test
	@DisplayName("Test 2: La persona a añadir es un admin")
	public void test2() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2022");
		try {
			Mockito.doReturn(true).when(dao).register("Taek", "Chamhi", "Erina", "Tarek1201", "aaaaaaa", "123406789", "Tarek12301@gmail.com",oneDate, "admin");
			sut.register("Taek", "Chamhi", "Erina", "Tarek1201", "aaaaaaa", "123406789", "Tarek12301@gmail.com",oneDate, "admin");
			Mockito.verify(dao, Mockito.times(1)).register("Taek", "Chamhi", "Erina", "Tarek1201", "aaaaaaa", "123406789", "Tarek12301@gmail.com",oneDate, "admin");
			assertTrue(true);
		} catch (UserAlreadyExist e) {
			e.printStackTrace();
		}
		
	}
	@Test
	@DisplayName("Test 3: La persona a añadir es un bezeroa")
	public void test3() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2022");
		
		try {
			Mockito.doReturn(true).when(dao).register("Taerek", "Chaermhi", "Erierna", "Tak124701", "aaaaaaa", "123406789", "Tarek1222301@gmail.com",oneDate, "Bezeroa");
			
			sut.register("Taerek", "Chaermhi", "Erierna", "Tak124701", "aaaaaaa", "123406789", "Tarek1222301@gmail.com",oneDate, "Bezeroa");
			Mockito.verify(dao, Mockito.times(1)).register("Taerek", "Chaermhi", "Erierna", "Tak124701", "aaaaaaa", "123406789", "Tarek1222301@gmail.com",oneDate, "Bezeroa");
			assertTrue(true);
		} catch (UserAlreadyExist e) {
			e.printStackTrace();
		}
	}
	@Test
	@DisplayName("Test 4: La persona a añadir es un langilea")
	public void test4() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2022");
		
		try {
			Mockito.doReturn(true).when(dao).register("Tafsdaek", "Chamasfdhi", "Erdfina", "Tarek21201", "aaaaasdfaava", "123406789", "Tarek12375801@gmail.com",oneDate, "Langilea");
			sut.register("Tafsdaek", "Chamasfdhi", "Erdfina", "Tarek21201", "aaaaasdfaava", "123406789", "Tarek12375801@gmail.com",oneDate, "Langilea");
			Mockito.verify(dao, Mockito.times(1)).register("Tafsdaek", "Chamasfdhi", "Erdfina", "Tarek21201", "aaaaasdfaava", "123406789", "Tarek12375801@gmail.com",oneDate, "Langilea");
			
			assertTrue(true);
		} catch (UserAlreadyExist e) {
			e.printStackTrace();
		}
	}
	
	
	
}
