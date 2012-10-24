package es.icarto.gvsig.viasobras.queries.tests;

import static org.junit.Assert.assertFalse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.icarto.gvsig.viasobras.domain.catalog.Catalog;
import es.icarto.gvsig.viasobras.domain.catalog.mappers.DBFacade;
import es.icarto.gvsig.viasobras.queries.utils.WhereFactory;

public class C01Tests {

    private static Connection c;
    private String codigoConsulta = "C01";

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
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
	c.close();
    }

    @Test
    public void testBare() throws SQLException {
	String values[] = getValues(codigoConsulta);
	boolean hasWhere = values[0].equals("SI");
	String consulta = values[1];

	boolean thrown = false;
	try {
	    String carreteraCode = Catalog.CARRETERA_ALL;
	    String municipioCode = Catalog.CONCELLO_ALL;
	    String mayorValue = "";
	    String menorValue = "";
	    String textValue = "";
	    String where = WhereFactory.create(hasWhere, codigoConsulta, carreteraCode, municipioCode,
		    mayorValue, menorValue, textValue);
	    executeQuery(consulta, where);
	} catch (SQLException e) {
	    thrown = true;
	}
	assertFalse(thrown);
    }

    @Test
    public void testWithCarretera() throws SQLException {
	String values[] = getValues(codigoConsulta);
	boolean hasWhere = values[0].equals("SI");
	String consulta = values[1];

	boolean thrown = false;
	try {
	    String carreteraCode = "27001";
	    String municipioCode = Catalog.CONCELLO_ALL;
	    String mayorValue = "";
	    String menorValue = "";
	    String textValue = "";
	    String where = WhereFactory.create(hasWhere, codigoConsulta,
		    carreteraCode, municipioCode, mayorValue, menorValue,
		    textValue);
	    executeQuery(consulta, where);
	} catch (SQLException e) {
	    thrown = true;
	}
	assertFalse(thrown);
    }

    @Test
    public void testWithMunicipio() throws SQLException {
	String values[] = getValues(codigoConsulta);
	boolean hasWhere = values[0].equals("SI");
	String consulta = values[1];

	boolean thrown = false;
	try {
	    String carreteraCode = Catalog.CARRETERA_ALL;
	    String municipioCode = "1";
	    String mayorValue = "";
	    String menorValue = "";
	    String textValue = "";
	    String where = WhereFactory.create(hasWhere, codigoConsulta,
		    carreteraCode, municipioCode, mayorValue, menorValue,
		    textValue);
	    executeQuery(consulta, where);
	} catch (SQLException e) {
	    thrown = true;
	}
	assertFalse(thrown);
    }

    @Test
    public void testWithCarreteraAndMunicipio() throws SQLException {
	String values[] = getValues(codigoConsulta);
	boolean hasWhere = values[0].equals("SI");
	String consulta = values[1];

	boolean thrown = false;
	try {
	    String carreteraCode = Catalog.CARRETERA_ALL;
	    String municipioCode = "1";
	    String mayorValue = "";
	    String menorValue = "";
	    String textValue = "";
	    String where = WhereFactory.create(hasWhere, codigoConsulta,
		    carreteraCode, municipioCode, mayorValue, menorValue,
		    textValue);
	    executeQuery(consulta, where);
	} catch (SQLException e) {
	    thrown = true;
	}
	assertFalse(thrown);
    }

    @Test
    public void testWithNumericFilter() throws SQLException {
	String values[] = getValues(codigoConsulta);
	boolean hasWhere = values[0].equals("SI");
	String consulta = values[1];

	boolean thrown = false;
	try {
	    String carreteraCode = Catalog.CARRETERA_ALL;
	    String municipioCode = Catalog.CONCELLO_ALL;
	    String mayorValue = "3";
	    String menorValue = "1";
	    String textValue = "";
	    String where = WhereFactory.create(hasWhere, codigoConsulta,
		    carreteraCode, municipioCode, mayorValue, menorValue,
		    textValue);
	    executeQuery(consulta, where);
	} catch (SQLException e) {
	    thrown = true;
	}
	assertFalse(thrown);
    }

    @Test
    public void testWithTextFilter() throws SQLException {
	String values[] = getValues(codigoConsulta);
	boolean hasWhere = values[0].equals("SI");
	String consulta = values[1];

	boolean thrown = false;
	try {
	    String carreteraCode = Catalog.CARRETERA_ALL;
	    String municipioCode = Catalog.CONCELLO_ALL;
	    String mayorValue = "";
	    String menorValue = "";
	    String textValue = "foo";
	    String where = WhereFactory.create(hasWhere, codigoConsulta,
		    carreteraCode, municipioCode, mayorValue, menorValue,
		    textValue);
	    executeQuery(consulta, where);
	} catch (SQLException e) {
	    thrown = true;
	}
	assertFalse(thrown);
    }

    private void executeQuery(String consulta, String where)
	    throws SQLException {
	consulta = consulta.replaceAll("\\[\\[WHERE\\]\\]", where);
	Statement stmt2 = c.createStatement();
	ResultSet rs2 = stmt2.executeQuery(consulta);
	rs2.next();
	stmt2.close();
	rs2.close();
    }

    private String[] getValues(String codigoConsulta) throws SQLException {
	String sql = "SELECT hasWhere, consulta FROM consultas.consultas WHERE codigo = '"
		+ codigoConsulta + "'";
	Statement stmt = c.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	rs.next();
	String consulta = rs.getString("consulta");
	String hasWhere = rs.getString("hasWhere");
	stmt.close();
	rs.close();
	return new String[] { hasWhere, consulta };
    }

}
