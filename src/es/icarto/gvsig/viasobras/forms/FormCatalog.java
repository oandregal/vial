package es.icarto.gvsig.viasobras.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.table.TableModel;

import com.iver.andami.messages.NotificationManager;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.SingletonWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.jeta.forms.components.panel.FormPanel;

import es.icarto.gvsig.navtableforms.utils.AbeilleParser;
import es.icarto.gvsig.viasobras.domain.catalog.Carretera;
import es.icarto.gvsig.viasobras.domain.catalog.Catalog;
import es.icarto.gvsig.viasobras.domain.catalog.Concello;
import es.icarto.gvsig.viasobras.domain.catalog.Tramo;
import es.icarto.gvsig.viasobras.domain.catalog.mappers.DBFacade;
import es.icarto.gvsig.viasobras.domain.catalog.utils.tramos.TramosTableModel;
import es.icarto.gvsig.viasobras.maploader.MapLoader;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class FormCatalog extends JPanel implements IWindow, SingletonWindow {

    private FormPanel form;
    protected WindowInfo viewInfo = null;

    private HashMap<String, JComponent> widgets;
    private HashMap<String, JButton> buttons;

    private JComboBox carreteras;
    private JComboBox concellos;

    private JTextField pkStart;
    private JTextField pkEnd;

    private JTable tipoPavimento;
    private TableModel tipoPavimentoModel;
    private JTable anchoPlataforma;
    private TableModel anchoPlataformaModel;
    private JTable cotas;
    private TableModel cotasModel;

    private JButton search;
    private JButton load;
    private JButton save;

    private JComboBox mapLoad;

    private JButton insertTramoPavimento;
    private JButton deleteTramoPavimento;
    private JButton insertTramoPlataforma;
    private JButton deleteTramoPlataforma;
    private JButton insertCota;
    private JButton deleteCota;

    public FormCatalog() {
	form = new FormPanel("catalog.xml");
	initDomainMapper();
	Catalog.clear();
	initForm();
	JScrollPane scrolledForm = new JScrollPane(form);
	this.add(scrolledForm);
    }

    private void initDomainMapper() {
	try {
	    DBSession dbs = DBSession.getCurrentSession();
	    Properties p = new Properties();
	    p.setProperty(DBFacade.URL, dbs.getJavaConnection().getMetaData()
		    .getURL());
	    p.setProperty(DBFacade.USERNAME, dbs.getUserName());
	    p.setProperty(DBFacade.PASSWORD, dbs.getPassword());
	    // Create the connection ourselves, as at this moment, gvSIG has an
	    // old driver (lower than jdbc4) which doesn't implement the methods
	    // we need. So, take care that the jar in
	    // lib/postgresql-8.4-jdbc4.jar is used instead of gvSIG ones.
	    Class.forName("org.postgresql.Driver");
	    Connection c = DriverManager.getConnection(
		    p.getProperty(DBFacade.URL),
		    p.getProperty(DBFacade.USERNAME),
		    p.getProperty(DBFacade.PASSWORD));
	    DBFacade.setConnection(c, p);
	} catch (Exception e) {
	    NotificationManager.addError(e);
	}
    }

    private void initForm() {

	setWidgets();

	fillComboBoxes();
	fillTables();

	initFocus();
	initListeners();
    }

    private void setWidgets() {
	widgets = AbeilleParser.getWidgetsFromContainer(form);
	buttons = AbeilleParser.getButtonsFromContainer(form);

	carreteras = (JComboBox) widgets.get("carretera");
	concellos = (JComboBox) widgets.get("concello");

	pkStart = (JTextField) widgets.get("pk_inicial");
	pkEnd = (JTextField) widgets.get("pk_final");
	pkStart.setEnabled(false);
	pkEnd.setEnabled(false);

	tipoPavimento = (JTable) widgets.get("tabla_tipo_pavimento");
	insertTramoPavimento = (JButton) buttons.get("insertar_pavimento");
	deleteTramoPavimento = (JButton) buttons.get("borrar_pavimento");

	anchoPlataforma = (JTable) widgets.get("tabla_ancho_plataforma");
	insertTramoPlataforma = (JButton) buttons.get("insertar_plataforma");
	deleteTramoPlataforma = (JButton) buttons.get("borrar_plataforma");

	cotas = (JTable) widgets.get("tabla_cotas");
	insertCota = (JButton) buttons.get("insertar_cota");
	deleteCota = (JButton) buttons.get("borrar_cota");

	search = (JButton) buttons.get("buscar");
	load = (JButton) buttons.get("cargar");
	save = (JButton) buttons.get("guardar");

	mapLoad = (JComboBox) widgets.get("mapa");
    }

    private void initFocus() {
	this.setFocusCycleRoot(true);
	this.setFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
	carreteras.requestFocusInWindow();
	tipoPavimento.setFocusable(false);
	anchoPlataforma.setFocusable(false);
	cotas.setFocusable(false);
    }

    private void initListeners() {

	carreteras.addItemListener(new CarreteraListener());
	concellos.addItemListener(new ConcelloListener());

	SearchListener mySearchListener = new SearchListener();
	pkStart.addKeyListener(mySearchListener);
	pkEnd.addKeyListener(mySearchListener);
	search.addActionListener(mySearchListener);

	load.addActionListener(new LoadMapListener());
	save.addActionListener(new SaveChangesListener());

	insertTramoPavimento.addActionListener(
		new InsertTramoPavimentoListener());
	deleteTramoPavimento.addActionListener(
		new DeleteTramoPavimentoListener());

	insertTramoPlataforma.addActionListener(
		new InsertTramoPlataformaListener());
	deleteTramoPlataforma.addActionListener(
		new DeleteTramoPlataformaListener());

	insertCota.addActionListener(
		new InsertTramoCotaListener());
	deleteCota.addActionListener(
		new DeleteTramoCotaListener());
    }

    private void enablePKControls() {
	pkStart.setEnabled(true);
	pkEnd.setEnabled(true);
    }

    private void disablePKControls() {
	pkStart.setEnabled(false);
	pkEnd.setEnabled(false);
    }

    private void setPKOnCatalog() {
	double start;
	try {
	    start = Double.parseDouble(pkStart.getText());
	} catch (NumberFormatException e) {
	    pkStart.setText("");
	    start = Catalog.PK_NONE;
	}
	Catalog.setPKStart(start);
	double end;
	try {
	    end = Double.parseDouble(pkEnd.getText());
	} catch (NumberFormatException e) {
	    pkEnd.setText("");
	    end = Catalog.PK_NONE;
	}
	Catalog.setPKEnd(end);
    }

    private void fillTables() {
	try {
	    tipoPavimentoModel = Catalog.getTramosTipoPavimento()
		    .getTableModel();
	    tipoPavimento.setModel(tipoPavimentoModel);
	    anchoPlataformaModel = Catalog.getTramosAnchoPlataforma()
		    .getTableModel();
	    anchoPlataforma.setModel(anchoPlataformaModel);
	    cotasModel = Catalog.getTramosCotas().getTableModel();
	    cotas.setModel(cotasModel);
	    this.repaint();
	} catch (SQLException e) {
	    NotificationManager.addError(e);
	}
    }

    private void fillComboBoxes() {
	fillConcellos();
	fillCarreteras();
	fillELLEMaps();
    }

    private void fillELLEMaps() {
	mapLoad.removeAllItems();
	List<String> maps = MapLoader.getAllMapNames();
	for (String mapName : maps) {
	    mapLoad.addItem(mapName);
	}
    }

    private void fillCarreteras() {
	carreteras.removeAllItems();
	carreteras.addItem(Catalog.CARRETERA_ALL);
	try {
	    for (Carretera c : Catalog.getCarreteras()) {
		carreteras.addItem(c);
	    }
	} catch (SQLException e) {
	    carreteras.removeAllItems();
	    carreteras.addItem(Catalog.CARRETERA_ALL);
	    System.out.println(e.getMessage());
	    NotificationManager.addError(e);
	}
    }

    private void fillConcellos() {
	concellos.removeAllItems();
	concellos.addItem(Catalog.CONCELLO_ALL);
	try {
	    for (Concello c : Catalog.getConcellos()) {
		concellos.addItem(c);
	    }
	} catch (SQLException e) {
	    concellos.removeAllItems();
	    concellos.addItem(Catalog.CONCELLO_ALL);
	    System.out.println(e.getMessage());
	    NotificationManager.addError(e);
	}
    }

    public WindowInfo getWindowInfo() {
	if (viewInfo == null) {
	    viewInfo = new WindowInfo(WindowInfo.MODELESSDIALOG
		    | WindowInfo.PALETTE);
	    viewInfo.setTitle("Vias Obras");
	    viewInfo.setWidth(840);
	    viewInfo.setHeight(480);
	}
	return viewInfo;
    }

    public Object getWindowProfile() {
	return null;
    }

    public Object getWindowModel() {
	return "catalog-roads";
    }

    private final class DeleteTramoPlataformaListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if ((anchoPlataforma.getRowCount() > 0)
		    && (anchoPlataforma.getSelectedRow() != -1)) {
		((TramosTableModel) anchoPlataformaModel)
		.deleteTramo(anchoPlataforma.getSelectedRow());
	    }
	}
    }

    private final class InsertTramoPlataformaListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    Tramo t = new Tramo();
	    t.setCarretera(Catalog.getCarreteraSelected());
	    t.setConcello(Catalog.getConcelloSelected());
	    t.setPkStart(Catalog.getPKStart());
	    t.setPkEnd(Catalog.getPKEnd());
	    ((TramosTableModel) anchoPlataformaModel).addTramo(t);
	}
    }

    private final class DeleteTramoPavimentoListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if ((tipoPavimento.getRowCount() > 0)
		    && (tipoPavimento.getSelectedRow() != -1)) {
		((TramosTableModel) tipoPavimentoModel)
		.deleteTramo(tipoPavimento.getSelectedRow());
	    }
	}
    }

    private final class InsertTramoPavimentoListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    Tramo t = new Tramo();
	    t.setCarretera(Catalog.getCarreteraSelected());
	    t.setConcello(Catalog.getConcelloSelected());
	    t.setPkStart(Catalog.getPKStart());
	    t.setPkEnd(Catalog.getPKEnd());
	    ((TramosTableModel) cotasModel).addTramo(t);
	}
    }

    private final class DeleteTramoCotaListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if ((cotas.getRowCount() > 0) && (cotas.getSelectedRow() != -1)) {
		((TramosTableModel) cotasModel).deleteTramo(cotas
			.getSelectedRow());
	    }
	}
    }

    private final class InsertTramoCotaListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    Tramo t = new Tramo();
	    t.setCarretera(Catalog.getCarreteraSelected());
	    t.setConcello(Catalog.getConcelloSelected());
	    t.setPkStart(Catalog.getPKStart());
	    t.setPkEnd(Catalog.getPKEnd());
	    ((TramosTableModel) cotasModel).addTramo(t);
	}
    }

    private final class SaveChangesListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    try {
		((TramosTableModel) tipoPavimentoModel).saveChanges();
		((TramosTableModel) anchoPlataformaModel).saveChanges();
		((TramosTableModel) cotasModel).saveChanges();
		doSearch();
	    } catch (SQLException e1) {
		NotificationManager.addError(e1);
	    }
	}
    }

    private final class LoadMapListener implements ActionListener {
	public void actionPerformed(ActionEvent arg0) {
	    try {
		if (mapLoad.getSelectedItem() != null) {
		    MapLoader.loadMap(mapLoad.getSelectedItem().toString());
		} else {
		    MapLoader.loadDefaultMap();
		}
	    } catch (Exception e) {
		NotificationManager.addError(e);
	    }
	}
    }

    private final class SearchListener implements KeyListener, ActionListener {
	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		doSearch();
	    }
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void actionPerformed(ActionEvent arg0) {
	    doSearch();
	}

    }

    private void doSearch() {
	setPKOnCatalog();
	fillTables();
    }

    private final class ConcelloListener implements ItemListener {
	public void itemStateChanged(ItemEvent e) {
	    if (e.getStateChange() == ItemEvent.SELECTED) {
		if (concellos.getSelectedItem().equals(Catalog.CONCELLO_ALL)) {
		    Catalog.setConcello(Catalog.CONCELLO_ALL);
		    if (Catalog.getCarreteraSelected() != Catalog.CARRETERA_ALL) {
			enablePKControls();
		    } else {
			disablePKControls();
		    }
		} else {
		    Concello c = (Concello) concellos.getSelectedItem();
		    Catalog.setConcello(c.getCode());
		    disablePKControls();
		}
	    }
	}
    }

    private final class CarreteraListener implements ItemListener {
	public void itemStateChanged(ItemEvent e) {
	    if (e.getStateChange() == ItemEvent.SELECTED) {
		if (carreteras.getSelectedItem().equals(Catalog.CARRETERA_ALL)) {
		    Catalog.setCarretera(Catalog.CARRETERA_ALL);
		    disablePKControls();
		} else {
		    Carretera c = (Carretera) carreteras.getSelectedItem();
		    Catalog.setCarretera(c.getCode());
		    enablePKControls();
		}
		fillConcellos();
	    }
	}
    }

}