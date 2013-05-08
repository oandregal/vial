package es.icarto.gvsig.viasobras.tests.catalog;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import es.icarto.gvsig.viasobras.domain.catalog.Catalog;
import es.icarto.gvsig.viasobras.domain.catalog.Evento;
import es.icarto.gvsig.viasobras.domain.catalog.Eventos;
import es.icarto.gvsig.viasobras.domain.catalog.Tramo;
import es.icarto.gvsig.viasobras.domain.catalog.Tramos;
import es.icarto.gvsig.viasobras.domain.catalog.mappers.DBFacade;

public class TestsCatalogEdit {

    private static Connection c;
    private static String carreteraPlataforma;
    private static String concelloPlataforma;
    private static String carreteraPavimento;
    private static String concelloPavimento;
    private static String carreteraCota;
    private static String concelloCota;
    private static String carreteraAforo;
    private static String concelloAforo;
    private static String carreteraAccidente;
    private static String concelloAccidente;

    @BeforeClass
    public static void connectToDatabase() throws SQLException,
    ClassNotFoundException {
	String url = "jdbc:postgresql://localhost:5432/vias_obras";
	String user = "viasobras";
	String passwd = "viasobras";
	Properties p = new Properties();
	p.setProperty(DBFacade.URL, url);
	p.setProperty(DBFacade.USERNAME, user);
	p.setProperty(DBFacade.PASSWORD, passwd);
	// postgresql-8.4.-jdbc4.jar needs to be in the classpasth before the
	// other gvSIG jars related to pgsql (which are jdbc3 are may not work).
	// Configure that in your classpath tab if you use eclipse
	Class.forName("org.postgresql.Driver");
	c = DriverManager.getConnection(p.getProperty(DBFacade.URL),
		p.getProperty(DBFacade.USERNAME),
		p.getProperty(DBFacade.PASSWORD));
	DBFacade.setConnection(c, p);
	// useful methods to know municipio/carretera with most tramos
	setCarreteraAndConcelloForAccidente();
	setCarreteraAndConcelloForAforo();
	setCarreteraAndConcelloForCota();
	setCarreteraAndConcelloForPavimento();
	setCarreteraAndConcelloForPlataforma();
    }

    private static void setCarreteraAndConcelloForAccidente()
	    throws SQLException {
	// use the area (carretera, concello) with more tramos
	String sql = "SELECT codigo_carretera, codigo_municipio, COUNT(gid) AS tramos_count FROM inventario.accidentes GROUP BY codigo_carretera, codigo_municipio ORDER BY tramos_count DESC LIMIT 1";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	carreteraAccidente = rs.getString("codigo_carretera");
	concelloAccidente = rs.getString("codigo_municipio");
    }

    private static void setCarreteraAndConcelloForPlataforma()
	    throws SQLException {
	// use the area (carretera, concello) with more tramos
	String sql = "SELECT codigo_carretera, codigo_municipio, COUNT(gid) AS tramos_count FROM inventario.ancho_plataforma GROUP BY codigo_carretera, codigo_municipio ORDER BY tramos_count DESC LIMIT 1";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	carreteraPlataforma = rs.getString("codigo_carretera");
	concelloPlataforma = rs.getString("codigo_municipio");
    }

    private static void setCarreteraAndConcelloForPavimento()
	    throws SQLException {
	// use the area (carretera, concello) with more tramos
	String sql = "SELECT codigo_carretera, codigo_municipio, COUNT(gid) AS tramos_count FROM inventario.tipo_pavimento GROUP BY codigo_carretera, codigo_municipio ORDER BY tramos_count DESC LIMIT 1";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	carreteraPavimento = rs.getString("codigo_carretera");
	concelloPavimento = rs.getString("codigo_municipio");
    }

    private static void setCarreteraAndConcelloForCota() throws SQLException {
	// use the area (carretera, concello) with more tramos
	String sql = "SELECT codigo_carretera, codigo_municipio, COUNT(gid) AS tramos_count FROM inventario.cotas GROUP BY codigo_carretera, codigo_municipio ORDER BY tramos_count DESC LIMIT 1";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	carreteraCota = rs.getString("codigo_carretera");
	concelloCota = rs.getString("codigo_municipio");
    }

    private static void setCarreteraAndConcelloForAforo() throws SQLException {
	// use the area (carretera, concello) with more tramos
	String sql = "SELECT codigo_carretera, codigo_municipio, COUNT(gid) AS tramos_count FROM inventario.aforos GROUP BY codigo_carretera, codigo_municipio ORDER BY tramos_count DESC LIMIT 1";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	carreteraAforo = rs.getString("codigo_carretera");
	concelloAforo = rs.getString("codigo_municipio");
    }

    @Test
    public void testDeletePavimento() throws SQLException {
	String carretera = carreteraPavimento;
	String concello = concelloPavimento;
	String lastID = getLastId("inventario.tipo_pavimento", carretera,
		concello);
	String gid = deletePavimento(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.tipo_pavimento WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testDeletePlataforma() throws SQLException {
	String carretera = carreteraPlataforma;
	String concello = concelloPlataforma;
	String lastID = getLastId("inventario.ancho_plataforma", carretera,
		concello);
	String gid = deletePlataforma(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.ancho_plataforma WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testDeleteCota() throws SQLException {
	String carretera = carreteraCota;
	String concello = concelloCota;
	String lastID = getLastId("inventario.cotas", carretera, concello);
	String gid = deleteCota(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.cotas WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testDeleteAforo() throws SQLException {
	String carretera = carreteraAforo;
	String concello = concelloAforo;
	String lastID = getLastIdAforos(carretera, concello);
	String gid = deleteAforo(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.aforos WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testDeleteAccidente() throws SQLException {
	String carretera = carreteraAccidente;
	String concello = concelloAccidente;
	String lastID = getLastId("inventario.accidentes", carretera, concello);
	String gid = deleteAccidente(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.accidentes WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testDeleteLastTwoPavimento() {
	Catalog.clear();
	Tramos tramosPavi;
	boolean thrown = false;
	try {
	    tramosPavi = Catalog.getTramosTipoPavimento();
	    int lastPosition = 2;
	    String lastID = "", lastCarretera = "", lastConcello = "";
	    int beforeLastPosition = 1;
	    String beforeLastID = "", beforeLastCarretera = "", beforeLastConcello = "";
	    for (Tramo t : tramosPavi) {
		if (t.getPosition() > lastPosition) {
		    lastPosition = t.getPosition();
		    lastID = t.getId();
		    lastCarretera = t.getCarretera();
		    lastConcello = t.getConcello();
		    beforeLastPosition = lastPosition - 1;
		}
	    }
	    for (Tramo t : tramosPavi) {
		if (t.getPosition() == beforeLastPosition) {
		    beforeLastID = t.getId();
		    beforeLastCarretera = t.getCarretera();
		    beforeLastConcello = t.getConcello();
		}
	    }
	    System.out.println("[beforeLast pavimento] Carretera: " + beforeLastCarretera
		    + " - Concello: " + beforeLastConcello
		    + " - ID: " + beforeLastID
		    + " - Position: "+ beforeLastPosition);
	    System.out.println("[last pavimento] Carretera: " + lastCarretera
		    + " - Concello: " + lastConcello
		    + " - ID: " + lastID
		    + " - Position: "+ lastPosition);
	    tramosPavi.removeTramo(beforeLastID);
	    tramosPavi.removeTramo(lastID);
	    tramosPavi.save();
	} catch (SQLException e) {
	    thrown = true;
	}
	assertEquals(false, thrown);
    }

    @Test
    public void testDeleteLastTwoAncho() {
	Catalog.clear();
	Tramos ancho;
	boolean thrown = false;
	try {
	    ancho = Catalog.getTramosAnchoPlataforma();
	    int lastPosition = 2;
	    String lastID = "", lastCarretera = "", lastConcello = "";
	    int beforeLastPosition = 1;
	    String beforeLastID = "", beforeLastCarretera = "", beforeLastConcello = "";
	    for (Tramo t : ancho) {
		if (t.getPosition() > lastPosition) {
		    lastPosition = t.getPosition();
		    lastID = t.getId();
		    lastCarretera = t.getCarretera();
		    lastConcello = t.getConcello();
		    beforeLastPosition = lastPosition - 1;
		}
	    }
	    for (Tramo t : ancho) {
		if (t.getPosition() == beforeLastPosition) {
		    beforeLastID = t.getId();
		    beforeLastCarretera = t.getCarretera();
		    beforeLastConcello = t.getConcello();
		}
	    }
	    System.out.println("[beforeLast ancho] Carretera: " + beforeLastCarretera
		    + " - Concello: " + beforeLastConcello + " - ID: "
		    + beforeLastID + " - Position: " + beforeLastPosition);
	    System.out.println("[last ancho] Carretera: " + lastCarretera
		    + " - Concello: " + lastConcello + " - ID: " + lastID
		    + " - Position: " + lastPosition);
	    ancho.removeTramo(beforeLastID);
	    ancho.removeTramo(lastID);
	    ancho.save();
	} catch (SQLException e) {
	    thrown = true;
	}
	assertEquals(false, thrown);
    }

    @Test
    public void testDeleteLastTwoCotas() {
	Catalog.clear();
	Tramos cotas;
	boolean thrown = false;
	try {
	    cotas = Catalog.getTramosCotas();
	    int lastPosition = 2;
	    String lastID = "", lastCarretera = "", lastConcello = "";
	    int beforeLastPosition = 1;
	    String beforeLastID = "", beforeLastCarretera = "", beforeLastConcello = "";
	    for (Tramo t : cotas) {
		if (t.getPosition() > lastPosition) {
		    lastPosition = t.getPosition();
		    lastID = t.getId();
		    lastCarretera = t.getCarretera();
		    lastConcello = t.getConcello();
		    beforeLastPosition = lastPosition - 1;
		}
	    }
	    for (Tramo t : cotas) {
		if (t.getPosition() == beforeLastPosition) {
		    beforeLastID = t.getId();
		    beforeLastCarretera = t.getCarretera();
		    beforeLastConcello = t.getConcello();
		}
	    }
	    System.out.println("[beforeLast cotas] Carretera: " + beforeLastCarretera
		    + " - Concello: " + beforeLastConcello + " - ID: "
		    + beforeLastID + " - Position: " + beforeLastPosition);
	    System.out.println("[last cotas] Carretera: " + lastCarretera
		    + " - Concello: " + lastConcello + " - ID: " + lastID
		    + " - Position: " + lastPosition);
	    cotas.removeTramo(beforeLastID);
	    cotas.removeTramo(lastID);
	    cotas.save();
	} catch (SQLException e) {
	    thrown = true;
	}
	assertEquals(false, thrown);
    }

    @Test
    public void testDeleteLastTwoAccidentes() {
	Catalog.clear();
	Eventos accidentes;
	boolean thrown = false;
	try {
	    accidentes = Catalog.getEventosAccidentes();
	    int lastPosition = 2;
	    String lastID = "", lastCarretera = "", lastConcello = "";
	    int beforeLastPosition = 1;
	    String beforeLastID = "", beforeLastCarretera = "", beforeLastConcello = "";
	    for (Evento e : accidentes) {
		if (e.getPosition() > lastPosition) {
		    lastPosition = e.getPosition();
		    lastID = e.getId();
		    lastCarretera = e.getCarretera();
		    lastConcello = e.getConcello();
		    beforeLastPosition = lastPosition - 1;
		}
	    }
	    for (Evento e : accidentes) {
		if (e.getPosition() == beforeLastPosition) {
		    beforeLastID = e.getId();
		    beforeLastCarretera = e.getCarretera();
		    beforeLastConcello = e.getConcello();
		}
	    }
	    System.out.println("[beforeLast accidente] Carretera: " + beforeLastCarretera
		    + " - Concello: " + beforeLastConcello
		    + " - ID: " + beforeLastID
		    + " - Position: "+ beforeLastPosition);
	    System.out.println("[last accidente] Carretera: " + lastCarretera
		    + " - Concello: " + lastConcello
		    + " - ID: " + lastID
		    + " - Position: "+ lastPosition);
	    accidentes.removeEvento(beforeLastID);
	    accidentes.removeEvento(lastID);
	    accidentes.save();
	} catch (SQLException e) {
	    thrown = true;
	}
	assertEquals(false, thrown);
    }

    @Test
    public void testDeleteLastTwoAforos() {
	Catalog.clear();
	Eventos aforos;
	boolean thrown = false;
	try {
	    aforos = Catalog.getEventosAforos();
	    int lastPosition = 2;
	    String lastID = "", lastCarretera = "", lastConcello = "";
	    int beforeLastPosition = 1;
	    String beforeLastID = "", beforeLastCarretera = "", beforeLastConcello = "";
	    for (Evento e : aforos) {
		if (e.getPosition() > lastPosition) {
		    lastPosition = e.getPosition();
		    lastID = e.getId();
		    lastCarretera = e.getCarretera();
		    lastConcello = e.getConcello();
		    beforeLastPosition = lastPosition - 1;
		}
	    }
	    for (Evento e : aforos) {
		if (e.getPosition() == beforeLastPosition) {
		    beforeLastID = e.getId();
		    beforeLastCarretera = e.getCarretera();
		    beforeLastConcello = e.getConcello();
		}
	    }
	    System.out.println("[beforeLast aforo] Carretera: " + beforeLastCarretera
		    + " - Concello: " + beforeLastConcello
		    + " - ID: " + beforeLastID
		    + " - Position: "+ beforeLastPosition);
	    System.out.println("[last aforo] Carretera: " + lastCarretera
		    + " - Concello: " + lastConcello
		    + " - ID: " + lastID
		    + " - Position: "+ lastPosition);
	    aforos.removeEvento(beforeLastID);
	    aforos.removeEvento(lastID);
	    aforos.save();
	} catch (SQLException e) {
	    thrown = true;
	}
	assertEquals(false, thrown);
    }

    @Test
    public void testDeleteTwoTramos() throws SQLException {
	Catalog.clear();
	Tramos tramosPavi = Catalog.getTramosTipoPavimento();
	int positionA = 2;
	int positionB = 25;
	String idPositionA = "", idPositionB = "";
	String carreteraPositionA = "", concelloPositionA = "";
	String carreteraPositionB = "", concelloPositionB = "";
	for (Tramo t : tramosPavi) {
	    if (t.getPosition() == positionA) {
		idPositionA = t.getId();
		carreteraPositionA = t.getCarretera();
		concelloPositionA = t.getConcello();
	    } else if (t.getPosition() == positionB) {
		idPositionB = t.getId();
		carreteraPositionB = t.getCarretera();
		concelloPositionB = t.getConcello();
	    }
	}
	tramosPavi.removeTramo(idPositionA);
	tramosPavi.removeTramo(idPositionB);
	tramosPavi.save();

	// check if the tramo A was deleted
	Statement stmtA = c.createStatement();
	ResultSet rsA = stmtA
		.executeQuery("SELECT gid FROM inventario.tipo_pavimento WHERE codigo_carretera = '"
			+ carreteraPositionA
			+ "' AND codigo_municipio = '"
			+ concelloPositionA + "'");
	boolean updatedA = true;
	while (rsA.next()) {
	    if (Integer.toString(rsA.getInt("gid")).equals(idPositionA)) {
		updatedA = false;
		break;
	    }
	}
	assertEquals(true, updatedA);

	// check if the tramo B was deleted
	Statement stmtB = c.createStatement();
	ResultSet rsB = stmtB
		.executeQuery("SELECT gid FROM inventario.tipo_pavimento WHERE codigo_carretera = '"
			+ carreteraPositionB
			+ "' AND codigo_municipio = '"
			+ concelloPositionB + "'");
	boolean updatedB = true;
	while (rsB.next()) {
	    if (Integer.toString(rsB.getInt("gid")).equals(idPositionB)) {
		updatedB = false;
		break;
	    }
	}
	assertEquals(true, updatedB);
    }

    @Test
    public void testUpdatePavimento() throws SQLException {
	String carretera = carreteraPavimento;
	String concello = concelloPavimento;
	String myValue = "B";
	updatePavimento(carretera, concello, myValue);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT valor FROM inventario.tipo_pavimento WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (!rs.getString("valor").equals(myValue)) {
		updated = false;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testUpdatePlataforma() throws SQLException {
	String carretera = carreteraPlataforma;
	String concello = concelloPlataforma;
	double value = 666;
	updatePlataforma(carretera, concello, value);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT valor FROM inventario.ancho_plataforma WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (rs.getDouble("valor") != value) {
		updated = false;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testUpdateCota() throws SQLException {
	String carretera = carreteraCota;
	String concello = concelloCota;
	double value = 666;
	updateCota(carretera, concello, value);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT valor FROM inventario.cotas WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (rs.getDouble("valor") != value) {
		updated = false;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testUpdateAforo() throws SQLException {
	String carretera = carreteraAforo;
	String concello = concelloAforo;
	int value = 123456789;
	updateAforos(carretera, concello, value);

	// check if the later made effect
	Statement stmt = c.createStatement();
	String sqlQuery = "WITH p AS ("
		+ "SELECT codigo_carretera, codigo_municipio, tramo, MAX(fecha) AS fecha_ultimo_aforo "
		+ " FROM inventario.aforos "
		+ " GROUP BY codigo_carretera, codigo_municipio, tramo "
		+ "ORDER BY codigo_carretera, codigo_municipio, tramo) "
		+ "SELECT i.valor "
		+ " FROM inventario.aforos AS i, p "
		+ " WHERE i.codigo_carretera = p.codigo_carretera AND i.codigo_municipio = p.codigo_municipio "
		+ "	  AND i.tramo = p.tramo AND i.fecha = p.fecha_ultimo_aforo "
		+ "	  AND i.codigo_carretera = '" + carretera + "'"
		+ "	  AND i.codigo_municipio = '" + concello + "'";
	ResultSet rs = stmt.executeQuery(sqlQuery);
	boolean updated = true;
	while (rs.next()) {
	    if (rs.getDouble("valor") != value) {
		updated = false;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testUpdateAccidente() throws SQLException {
	String carretera = carreteraAccidente;
	String concello = concelloAccidente;
	String value = "2012090809876";
	updateAccidente(carretera, concello, value);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT valor FROM inventario.accidentes WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (!rs.getString("valor").equals(value)) {
		updated = false;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testInsertPavimento() throws SQLException {
	String carretera = carreteraPavimento;
	String concello = concelloPavimento;
	int tramosNumber = insertPavimento(carretera, concello);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT COUNT(*) AS rowNumber FROM inventario.tipo_pavimento WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	rs.next();
	assertEquals(tramosNumber, rs.getInt("rowNumber"));
    }

    @Test
    public void testInsertPlataforma() throws SQLException {
	String carretera = carreteraPlataforma;
	String concello = concelloPlataforma;
	int tramosNumber = insertPlataforma(carretera, concello);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT COUNT(*) AS rowNumber FROM inventario.ancho_plataforma WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	rs.next();
	assertEquals(tramosNumber, rs.getInt("rowNumber"));
    }

    @Test
    public void testInsertCota() throws SQLException {
	String carretera = carreteraCota;
	String concello = concelloCota;
	int tramosNumber = insertCota(carretera, concello);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT COUNT(*) AS rowNumber FROM inventario.cotas WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	rs.next();
	assertEquals(tramosNumber, rs.getInt("rowNumber"));
    }

    @Test
    public void testInsertAforo() throws SQLException, ParseException {
	String carretera = carreteraAforo;
	String concello = concelloAforo;
	int myValue = insertAforo(carretera, concello);

	// check if the later made effect
	String sqlQuery = "WITH p AS ("
		+ "SELECT codigo_carretera, codigo_municipio, tramo, MAX(fecha) AS fecha_ultimo_aforo "
		+ " FROM inventario.aforos "
		+ " GROUP BY codigo_carretera, codigo_municipio, tramo "
		+ "ORDER BY codigo_carretera, codigo_municipio, tramo) "
		+ "SELECT i.valor "
		+ " FROM inventario.aforos AS i, p "
		+ " WHERE i.codigo_carretera = p.codigo_carretera AND i.codigo_municipio = p.codigo_municipio "
		+ "	  AND i.tramo = p.tramo AND i.fecha = p.fecha_ultimo_aforo "
		+ "	  AND i.codigo_carretera = '" + carretera + "'"
		+ "	  AND i.codigo_municipio = '" + concello + "'"
		+ " ORDER BY gid DESC LIMIT 1";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sqlQuery);
	rs.next();
	assertEquals(myValue, rs.getInt("valor"));
    }

    @Test
    public void testInsertAccidente() throws SQLException {
	String carretera = carreteraAccidente;
	String concello = concelloAccidente;
	int tramosNumber = insertAccidente(carretera, concello);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT COUNT(*) AS rowNumber FROM inventario.accidentes WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	rs.next();
	assertEquals(tramosNumber, rs.getInt("rowNumber"));
    }

    @Test
    public void testSinchronizedAfterCRUDOperationsPavimento()
	    throws SQLException {
	String carretera = carreteraPavimento;
	String concello = concelloPavimento;
	insertPavimento(carretera, concello);
	String lastID = getLastId("inventario.tipo_pavimento", carretera,
		concello);
	// should delete the recently created tramo
	String gid = deletePavimento(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.tipo_pavimento WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testSinchronizedAfterCRUDOperationsPlataforma()
	    throws SQLException {
	String carretera = carreteraPlataforma;
	String concello = concelloPlataforma;
	insertPlataforma(carretera, concello);
	// should delete the recently created tramo
	String lastID = getLastId("inventario.ancho_plataforma", carretera,
		concello);
	String gid = deletePlataforma(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.ancho_plataforma WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testSinchronizedAfterCRUDOperationsCota() throws SQLException {
	String carretera = carreteraPlataforma;
	String concello = concelloPlataforma;
	insertCota(carretera, concello);
	// should delete the recently created tramo
	String lastID = getLastId("inventario.cotas", carretera, concello);
	String gid = deleteCota(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.cotas WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    @Test
    public void testSinchronizedAfterCRUDOperationsAforo() throws SQLException,
    ParseException {
	String carretera = carreteraAforo;
	String concello = concelloAforo;
	insertAforo(carretera, concello);
	// should delete the recently created tramo
	String lastID = getLastId("inventario.aforos", carretera, concello);
	String gid = deleteAforo(carretera, concello, lastID);

	// check if the later made effect
	Statement stmt = c.createStatement();
	ResultSet rs = stmt
		.executeQuery("SELECT gid FROM inventario.aforos WHERE codigo_carretera = '"
			+ carretera
			+ "' AND codigo_municipio = '"
			+ concello
			+ "'");
	boolean updated = true;
	while (rs.next()) {
	    if (Integer.toString(rs.getInt("gid")).equals(gid)) {
		updated = false;
		break;
	    }
	}
	assertEquals(true, updated);
    }

    private String deletePlataforma(String carretera, String concello,
	    String gid)
		    throws SQLException {

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos tramos = Catalog.getTramosAnchoPlataforma();
	tramos.removeTramo(gid);
	tramos.save();
	return gid;
    }

    private String deletePavimento(String carretera, String concello,
	    String gid)
		    throws SQLException {

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos tramos = Catalog.getTramosTipoPavimento();
	tramos.removeTramo(gid);
	tramos.save();
	return gid;
    }

    private String deleteCota(String carretera, String concello,
	    String gid) throws SQLException {

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos tramos = Catalog.getTramosCotas();
	tramos.removeTramo(gid);
	tramos.save();
	return gid;
    }

    private String deleteAccidente(String carretera, String concello,
	    String gid)
		    throws SQLException {
	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Eventos eventos = Catalog.getEventosAccidentes();
	eventos.removeEvento(gid);
	eventos.save();
	return gid;
    }

    private String deleteAforo(String carretera, String concello,
	    String gid) throws SQLException {
	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Eventos eventos = Catalog.getEventosAforos();
	eventos.removeEvento(gid);
	eventos.save();
	return gid;
    }

    private void updatePlataforma(String carretera, String concello,
	    double value) throws SQLException {

	// modify and save tramos
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos anchoPlataforma = Catalog.getTramosAnchoPlataforma();
	for (Tramo t : anchoPlataforma) {
	    t.setValue(value);
	    t.setStatus(Tramo.STATUS_UPDATE);
	}
	anchoPlataforma.save();
    }

    private void updatePavimento(String carretera, String concello,
	    String value)
		    throws SQLException {

	// modify and save tramos
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos tipoPavimento = Catalog.getTramosTipoPavimento();
	for (Tramo t : tipoPavimento) {
	    t.setValue(value);
	    t.setStatus(Tramo.STATUS_UPDATE);
	}
	tipoPavimento.save();
    }

    private void updateCota(String carretera, String concello, Double value)
	    throws SQLException {

	// modify and save tramos
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos cotas = Catalog.getTramosCotas();
	for (Tramo t : cotas) {
	    t.setValue(value);
	    t.setStatus(Tramo.STATUS_UPDATE);
	}
	cotas.save();
    }

    private void updateAccidente(String carretera, String concello, String value)
	    throws SQLException {
	// modify and save tramos
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Eventos eventos = Catalog.getEventosAccidentes();
	for (Evento e : eventos) {
	    e.setValue(value);
	    e.setStatus(Tramo.STATUS_UPDATE);
	}
	eventos.save();
    }

    private void updateAforos(String carretera, String concello,
	    double value) throws SQLException {

	// modify and save tramos
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Eventos eventos = Catalog.getEventosAforos();
	for (Evento e : eventos) {
	    e.setValue(value);
	    e.setStatus(Tramo.STATUS_UPDATE);
	}
	eventos.save();
    }

    private int insertPavimento(String carretera, String concello)
	    throws SQLException {
	String ordenTramo = "A";
	double pkStart = 10.2;
	double pkEnd = 10.5;
	String myValue = "AAA";

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos tipoPavimento = Catalog.getTramosTipoPavimento();
	Tramo tramo = new Tramo();
	tramo.setCarretera(carretera);
	tramo.setOrdenTramo(ordenTramo);
	tramo.setConcello(concello);
	tramo.setPkStart(pkStart);
	tramo.setPkEnd(pkEnd);
	tramo.setValue(myValue);
	tipoPavimento.addTramo(tramo);
	tipoPavimento.save();
	int tramosNumber = tipoPavimento.size();
	return tramosNumber;
    }

    private int insertPlataforma(String carretera, String concello)
	    throws SQLException {
	String ordenTramo = "A";
	double pkStart = 10.2;
	double pkEnd = 10.4;
	double myValue = 666;

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos anchoPlataforma = Catalog.getTramosAnchoPlataforma();
	Tramo tramo = new Tramo();
	tramo.setCarretera(carretera);
	tramo.setOrdenTramo(ordenTramo);
	tramo.setConcello(concello);
	tramo.setPkStart(pkStart);
	tramo.setPkEnd(pkEnd);
	tramo.setValue(myValue);
	anchoPlataforma.addTramo(tramo);
	anchoPlataforma.save();
	int tramosNumber = anchoPlataforma.size();
	return tramosNumber;
    }

    private int insertCota(String carretera, String concello)
	    throws SQLException {
	String ordenTramo = "A";
	double pkStart = 10.2;
	double pkEnd = 10.4;
	double myValue = 666;

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Tramos tramos = Catalog.getTramosCotas();
	Tramo tramo = new Tramo();
	tramo.setCarretera(carretera);
	tramo.setOrdenTramo(ordenTramo);
	tramo.setConcello(concello);
	tramo.setPkStart(pkStart);
	tramo.setPkEnd(pkEnd);
	tramo.setValue(myValue);
	tramos.addTramo(tramo);
	tramos.save();
	int tramosNumber = tramos.size();
	return tramosNumber;
    }

    private int insertAforo(String carretera, String concello)
	    throws SQLException, ParseException {
	String ordenTramo = "A";
	double pk = 10.2;
	int myValue = 987654321;
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	java.sql.Date myDate = new java.sql.Date(formatter.parse("30/12/2013")
		.getTime());
	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Eventos eventos = Catalog.getEventosAforos();
	Evento evento = new Evento();
	evento.setCarretera(carretera);
	evento.setOrden(ordenTramo);
	evento.setConcello(concello);
	evento.setPk(pk);
	evento.setValue(myValue);
	evento.setUpdatingDate(myDate);
	eventos.addEvento(evento);
	eventos.save();
	return myValue;
    }

    private int insertAccidente(String carretera, String concello)
	    throws SQLException {
	String ordenTramo = "A";
	double pk = 10.2;
	String myValue = "201209010987654321";

	// add new tramo
	Catalog.clear();
	Catalog.setCarretera(carretera);
	Catalog.setConcello(concello);
	Eventos eventos = Catalog.getEventosAccidentes();
	Evento evento = new Evento();
	evento.setCarretera(carretera);
	evento.setOrden(ordenTramo);
	evento.setConcello(concello);
	evento.setPk(pk);
	evento.setValue(myValue);
	eventos.addEvento(evento);
	eventos.save();
	int eventosNumber = eventos.size();
	return eventosNumber;
    }

    private String getLastIdAforos(String carretera, String concello)
	    throws SQLException {
	Statement stmt = c.createStatement();
	String sqlQuery = "WITH p AS ("
		+ "SELECT codigo_carretera, codigo_municipio, tramo, MAX(fecha) AS fecha_ultimo_aforo "
		+ " FROM inventario.aforos "
		+ " GROUP BY codigo_carretera, codigo_municipio, tramo "
		+ "ORDER BY codigo_carretera, codigo_municipio, tramo) "
		+ "SELECT i.gid "
		+ " FROM inventario.aforos AS i, p "
		+ " WHERE i.codigo_carretera = p.codigo_carretera AND i.codigo_municipio = p.codigo_municipio "
		+ "	  AND i.tramo = p.tramo AND i.fecha = p.fecha_ultimo_aforo "
		+ "	  AND i.codigo_carretera = '" + carretera + "'"
		+ "	  AND i.codigo_municipio = '" + concello + "'"
		+ " ORDER BY gid DESC LIMIT 1";
	ResultSet rs = stmt.executeQuery(sqlQuery);
	rs.next();
	return Integer.toString(rs.getInt("gid"));
    }

    private String getLastId(String tableName, String carretera, String concello)
	    throws SQLException {
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT gid FROM " + tableName
		+ " WHERE codigo_carretera = '" + carretera
		+ "' AND codigo_municipio = '" + concello
		+ "' ORDER BY gid DESC LIMIT 1");
	rs.next();
	return Integer.toString(rs.getInt("gid"));
    }

}
