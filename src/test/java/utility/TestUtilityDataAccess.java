package utility;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Admin;
import domain.Apustua;
import domain.Bezeroa;
import domain.Errepikapena;
import domain.Event;
import domain.Langilea;
import domain.Pertsona;
import domain.Pronostikoa;
import domain.Question;
import exceptions.UserAlreadyExist;

public class TestUtilityDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestUtilityDataAccess()  {		
		System.out.println("Creating TestDataAccess instance");

		open();		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	public Pertsona isLogin(String erabiltzaileIzena, String pasahitza) {
		TypedQuery<Pertsona> query = db.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzaileIzena=?1 AND p.pasahitza=?2", Pertsona.class);
		query.setParameter(1, erabiltzaileIzena);
		query.setParameter(2, pasahitza);
		List<Pertsona> pertsona = query.getResultList();
		if(pertsona.isEmpty()) {
			return null;
		}else {
			return pertsona.get(0);
		}
	}
		
	public Bezeroa getBezeroa(String ErabiltzaileIzena) {
		Bezeroa erabiltzaile = db.find(Bezeroa.class, ErabiltzaileIzena);
		return erabiltzaile;
	}
	
	public Langilea getLangilea(String ErabiltzaileIzena) {
		Langilea erabiltzaile = db.find(Langilea.class, ErabiltzaileIzena);
		return erabiltzaile;
	}
	
	public Vector<Bezeroa> getBezeroak(String username, Bezeroa bezeroa){
		Bezeroa erabiltzaile = db.find(Bezeroa.class, bezeroa.getErabiltzaileIzena());
		Vector<Bezeroa> res = new Vector<Bezeroa>();
		TypedQuery<Bezeroa> query = db.createQuery("SELECT b FROM Bezeroa b", Bezeroa.class);
		List<Bezeroa> bezeroak = query.getResultList();
		for (Bezeroa b : bezeroak) {
			if(b.isPublikoa() && !b.getErabiltzaileIzena().equals(erabiltzaile.getErabiltzaileIzena()) && b.getErabiltzaileIzena().contains(username) && !erabiltzaile.baduMezua(b) && !erabiltzaile.errepikapenErlazioaDu(b)) {
				res.add(b);
			}
		}
		return res;
	}


		public Pertsona register(String izena, String abizena1, String abizena2, String erabiltzaileIzena, String pasahitza, String telefonoZbkia, String emaila, Date jaiotzeData, String mota) throws UserAlreadyExist{
			TypedQuery<Pertsona> query = db.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzaileIzena=?1", Pertsona.class);
			query.setParameter(1, erabiltzaileIzena);
			List<Pertsona> pertsona = query.getResultList();
			if(!pertsona.isEmpty()) {
				throw new UserAlreadyExist();
			}else {
				Pertsona berria = null;
				if(mota.equals("admin")) {
					berria = new Admin(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoZbkia, emaila, jaiotzeData);
				}else if (mota.equals("langilea")) {
					berria = new Langilea(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoZbkia, emaila, jaiotzeData);
				}else if (mota.equals("bezeroa")) {
					berria = new Bezeroa(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoZbkia, emaila, jaiotzeData);
				}
				db.getTransaction().begin();
				db.persist(berria);
				db.getTransaction().commit();
				return berria;
			}
		}


		public void ezabatuGertaera(Event event) {
			Bezeroa bezeroa;
			int x;
			Event e = db.find(Event.class, event.getEventNumber());
			db.getTransaction().begin();
			for (Question question : e.getQuestions()) {
				for (Pronostikoa pronostic : question.getPronostics()) {
					for (Apustua bet : pronostic.getApustuak()) {
						x = bet.removePronostikoa(pronostic);
						if(x==0) {
							bezeroa=bet.getBezeroa();
							if (bet.getErrepikatua()!=null) {
								Bezeroa erre=bet.getErrepikatua();
								Errepikapena er=bezeroa.getErrepikapena(erre);
								er.eguneratuHilHonetanGeratzenDena(bet.getKopurua());
							}
							bezeroa.addMugimendua("Apustuaren dirua itzuli ("+bet.getIdentifikadorea()+")", bet.getKopurua(),"bueltatu");
							bezeroa.removeApustua(bet);
							db.remove(bet);
						}else if(bet.getAsmatutakoKop()==bet.getPronostikoKop()) {
							double komisioa = 0;
							if (bet.getErrepikatua()!=null) {
								Bezeroa bez = bet.getErrepikatua();
								bezeroa = bet.getBezeroa();
								Errepikapena errepikapen=bezeroa.getErrepikapena(bez);
								komisioa=(bet.getKopurua()*bet.getKuotaTotala()-bet.getKopurua())*errepikapen.getKomisioa();
								bez.addMugimendua("Apustu errepikatuaren komisioa ("+bezeroa+")", komisioa,"irabazi");
							}
							bezeroa=bet.getBezeroa();
							double irabazia=bet.getKopurua()*bet.getKuotaTotala()-komisioa;
							bezeroa.addMugimendua("Apustua irabazi ("+bet.getIdentifikadorea()+")", irabazia, "irabazi");
						}
						System.out.println(bet.getPronostikoak()+"  berrize");
					}
				}
			}
			db.remove(e);
			db.getTransaction().commit();
			Apustua a = db.find(Apustua.class, 53);
			System.out.println(a);
		}

		
public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
	System.out.println(">> DataAccessTest: addEvent");
	Event ev=null;
		db.getTransaction().begin();
		try {
		    ev=new Event(desc,d);
		    ev.addQuestion(question,  qty);
			db.persist(ev);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ev;
}

public Vector<Event> getEvents(Date date) {
	System.out.println(">> DataAccess: getEvents");
	Vector<Event> res = new Vector<Event>();	
	TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
	query.setParameter(1, date);
	List<Event> events = query.getResultList();
 	 for (Event ev:events){
 	   System.out.println(ev.toString());		 
	   res.add(ev);
	  }
 	return res;
}

public boolean existQuestion(Event event, String question) {
	System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
	Event ev = db.find(Event.class, event.getEventNumber());
	return ev.DoesQuestionExists(question);
	
}
}

