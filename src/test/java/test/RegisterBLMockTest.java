package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
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

		assertThrows(UserAlreadyExist.class, () -> sut.register("Tarek", "Chamkhi", "Ermina", "Tarek12301", "aaaaaaaa",
				"123456789", "Tarek12301@gmail.com", oneDate, "Bezeroa"));

	}
	

	
	@Test
	@DisplayName("Test 3: La persona a añadir es un bezeroa")
	public void test3() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2022");
		//user to register
		String izenar = "Taerek";
		String abizena1r="Chaermhi";
		String abizena2r="Erierna";
		String erabiltzaileIzenar="Tak124701";
		String pasahitzar="aaaaaaa";
		String telefonoZbkiar="123406789";
		String emailr= "Tarek1222301@gmail.com";
		String motar="Bezeroa";
		

		try {
			Mockito.doReturn(true).when(dao).register(Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class), Mockito.any(String.class), Mockito.any(Date.class), Mockito.any(String.class));
			
			// invoke System Under Test (sut)
			sut.register(izenar,abizena1r , abizena2r, erabiltzaileIzenar,pasahitzar,telefonoZbkiar, emailr, oneDate, motar );
			
			//verify the results
			ArgumentCaptor<String> izena = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> abizena1 = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> abizena2 = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> erabiltzaileIzena = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> pasahitza = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> telefonoZbkia = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<String> email = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Date> jaiotzeData = ArgumentCaptor.forClass(Date.class);
			ArgumentCaptor<String> mota = ArgumentCaptor.forClass(String.class);
			
			Mockito.verify(dao, Mockito.times(1)).register(izena.capture(),abizena1.capture(),abizena2.capture(),
					erabiltzaileIzena.capture(), pasahitza.capture(), telefonoZbkia.capture(),
					email.capture(), jaiotzeData.capture(), mota.capture());

			//assertions
			assertEquals(erabiltzaileIzenar,erabiltzaileIzena.getValue() );
			assertEquals(izenar, izena.getValue() );
			assertEquals(abizena1r,abizena1.getValue() );
			assertEquals(abizena2r,abizena2.getValue() );
			assertEquals(emailr,email.getValue() );
			assertEquals(telefonoZbkiar,telefonoZbkia.getValue() );
			assertEquals(pasahitzar,pasahitza.getValue() );
			assertEquals(oneDate,jaiotzeData.getValue() );
			
		} catch (UserAlreadyExist e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	@DisplayName("Test 5: Alguno o todos los parametros son nulos")
	public void test5() {
		
			try {
			
				Mockito.doReturn(null).when(dao).register(null, null, null, null, null, null, null, null, null);
				
				Pertsona p= sut.register(null, null, null, null, null, null, null, null, null);

				assertNull(p);
			} catch (UserAlreadyExist e) {
				e.printStackTrace();
			}
		

	}
	
}
