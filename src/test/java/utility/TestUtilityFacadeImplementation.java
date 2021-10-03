package utility;

import java.util.Date;
import java.util.Vector;

import configuration.ConfigXML;
import domain.Bezeroa;
import domain.Event;
import domain.Langilea;
import domain.Pertsona;
import exceptions.UserAlreadyExist;

/**
 * Utilities to access Data Base
 * 
 * @author IS2
 *
 */
public class TestUtilityFacadeImplementation {
	private TestUtilityDataAccess dbManagerTest;

	public TestUtilityFacadeImplementation() {
		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML.getInstance();
		dbManagerTest = new TestUtilityDataAccess();
		dbManagerTest.close();
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
		dbManagerTest.open();
		Event o = dbManagerTest.addEventWithQuestion(desc, d, question, qty);
		dbManagerTest.close();
		return o;

	}

	public Vector<Event> getEvents(Date date) {
		dbManagerTest.open();
		Vector<Event> events = dbManagerTest.getEvents(date);
		dbManagerTest.close();
		return events;
	}

	public Bezeroa getBezeroa(String ErabiltzaileIzena) {
		dbManagerTest.open();
		Bezeroa b = dbManagerTest.getBezeroa(ErabiltzaileIzena);
		dbManagerTest.close();
		return b;
	}

	public Langilea getLangilea(String ErabiltzaileIzena) {
		dbManagerTest.open();
		Langilea l = dbManagerTest.getLangilea(ErabiltzaileIzena);
		dbManagerTest.close();
		return l;
	}

	public Vector<Bezeroa> getBezeroak(String username, Bezeroa bezeroa) {
		dbManagerTest.open();
		Vector<Bezeroa> bezeroak = dbManagerTest.getBezeroak(username, bezeroa);
		dbManagerTest.close();
		return bezeroak;
	}

	public Pertsona register(String izena, String abizena1, String abizena2, String erabiltzaileIzena, String pasahitza,
			String telefonoZbkia, String email, Date jaiotzeData, String mota) throws UserAlreadyExist {
		dbManagerTest.open();
		Pertsona emaitza = dbManagerTest.register(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza,
				telefonoZbkia, email, jaiotzeData, mota);
		dbManagerTest.close();
		return emaitza;
	}

	public void ezabatuGertaera(Event event) {
		dbManagerTest.open();
		dbManagerTest.ezabatuGertaera(event);
		dbManagerTest.close();
	}
}
