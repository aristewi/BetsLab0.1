package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Pertsona;
import exceptions.UserAlreadyExist;
import utility.TestUtilityFacadeImplementation;

class RegisterBLTest {

	private static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	private static TestUtilityFacadeImplementation betsDAO = new TestUtilityFacadeImplementation();
	
	@Test
	@DisplayName("Test 1: user in bd")
	 void test1() {
		
		assertThrows(UserAlreadyExist.class, () -> sut.register("Tarek", "Chamkhi", "Ermina", "Tarek12301", "aaaaaaaa",
				"123456789", "Tarek12301@gmail.com", UtilDate.newDate(2001, 8, 9), "Bezeroa"));

	}
	
	@Test
	@DisplayName("Test 2: La persona a añadir es un bezeroa")
	 void test2() throws ParseException, UserAlreadyExist {

		try {
			Pertsona b = betsDAO.register("Amiaaa ", "aaaaaa", "bbbb", "adaaasf", "aaaaaaa", "67676777",
					"asdaa@gmail.com", UtilDate.newDate(2021, 11, 2), "bezeroa");
			System.out.print("usuario añadido corrrectamente, bezeroa");

			assertEquals(b, sut.register("Amiaaa ", "aaaaaa", "bbbb", "adaaasf", "aaaaaaa", "67676777",
					"asdaa@gmail.com", UtilDate.newDate(2021, 11, 2), "bezeroa"));

		} catch (UserAlreadyExist e) {
			System.out.print("usuario NO añadido corrrectamente");

		}

	}

}
