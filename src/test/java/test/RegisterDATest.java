package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Pertsona;
import exceptions.UserAlreadyExist;
import utility.TestUtilityFacadeImplementation;

public class RegisterDATest {
	private static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	private static TestUtilityFacadeImplementation betsDAO = new TestUtilityFacadeImplementation();

	@Test
	@DisplayName("Test 1: user in bd")
	public void test1() {
		
		assertThrows(UserAlreadyExist.class, () -> sut.register("Tarek", "Chamkhi", "Ermina", "Tarek12301", "aaaaaaaa",
				"123456789", "Tarek12301@gmail.com", UtilDate.newDate(2001, 8, 9), "Bezeroa"));

	}

	@Test
	@DisplayName("Test5: usuario con parametros nulos")
	public void test5() {

		try {
			betsDAO.register(null, "324323", null, null, "dsafds", null, null, null, "bezeroa");
			assertThrows(Exception.class, () -> sut.register(null, null, null, null, null, null, null, null, null));
		} catch (UserAlreadyExist e) {
			System.out.print("usuario NO añadido corrrectamente");
		}

	}

	@Test
	@DisplayName("Test 2: La persona a añadir es un bezeroa")
	public void test2() throws ParseException, UserAlreadyExist {

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

	@Test
	@DisplayName("Test 3: La persona a añadir es un admin")
	public void test3() {
		try {
			Pertsona a = betsDAO.register("Amiadfga ", "aadfgaa", "bbsdfbb", "adasf", "aafhsdfghaa", "676736777",
					"asda@gmail.com", UtilDate.newDate(2021, 11, 2), "admin");

			System.out.print("usuario añadido corrrectamente, admin");
			assertEquals(a, sut.register("Amiaaa ", "aaaaaa", "bbbb", "adaaasf", "aaaaaaa", "67676777",
					"asdaa@gmail.com", UtilDate.newDate(2021, 11, 2), "bezeroa"));

		} catch (UserAlreadyExist e) {
			System.out.print("usuario NO añadido corrrectamente");

		}

	}

	@Test
	@DisplayName("Test 4: La persona a añadir es un langilea")
	public void test4() {
		try {
			Pertsona l = betsDAO.register("Amisdfgasa ", "aaaaadgsa", "bbbaagfdsb", "adaasagfsf", "aaabsdcvdaa",
					"67676777", "asasda@gmail.com", UtilDate.newDate(2021, 11, 2), "langilea");

			System.out.print("usuario añadido corrrectamente, langilea");
			assertEquals(l, sut.register("Amiaaa ", "aaaaaa", "bbbb", "adaaasf", "aaaaaaa", "67676777",
					"asdaa@gmail.com", UtilDate.newDate(2021, 11, 2), "bezeroa"));

		} catch (UserAlreadyExist e) {
			System.out.print("usuario NO añadido corrrectamente");

		}

	}

}
