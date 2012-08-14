package es.icarto.gvsig.viasobras.forms;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;
import com.jeta.forms.components.panel.FormPanel;

import es.icarto.gvsig.navtableforms.AbstractForm;
import es.icarto.gvsig.navtableforms.gui.tables.JTableContextualMenu;
import es.icarto.gvsig.navtableforms.gui.tables.TableModelAlphanumeric;
import es.icarto.gvsig.navtableforms.gui.tables.TableModelFactory;
import es.icarto.gvsig.navtableforms.ormlite.domain.KeyValue;
import es.udc.cartolab.gvsig.navtable.listeners.PositionEvent;

public class FormCarreteras extends AbstractForm implements IWindow {

    private FormPanel form;
    private JTable ayuntamientos;
    private JComboBox codigo;
    private FormCarreterasConcellos carreterasConcellos;
    private JTableContextualMenu contextualMenu;

    public FormCarreteras(FLyrVect layer) {
	super(layer);
	initWindow();
    }

    private void initWindow() {
	viewInfo.setHeight(500);
	viewInfo.setWidth(500);
	viewInfo.setTitle("V�as Obras: carreteras");
    }

    @Override
    protected void fillSpecificValues() {
	updateJTables();
    }

    private void updateJTables() {
	ArrayList<String> colNames = new ArrayList<String>();
	colNames.add("codigo_concello");
	colNames.add("pk_inicial");
	colNames.add("pk_final");
	colNames.add("observaciones");
	ArrayList<String> colAliases = new ArrayList<String>();
	colAliases.add("Ayuntamiento");
	colAliases.add("PK Inicial");
	colAliases.add("PK Final");
	colAliases.add("Observaciones");
	TableModelAlphanumeric model;
	try {
	    model = TableModelFactory.createFromTable("carreteras_concellos",
		    "codigo_carretera",
		    ((KeyValue) codigo.getSelectedItem()).getKey(), colNames,
		    colAliases);
	    ayuntamientos.setModel(model);
	    carreterasConcellos.setModel(model);
	    carreterasConcellos.setCarretera(((KeyValue) codigo
		    .getSelectedItem()).getKey());
	    this.repaint(); // will force embedded tables to refresh
	} catch (ReadDriverException e) {
	    e.printStackTrace();
	}

    }

    @Override
    public void setListeners() {
	super.setListeners();

	// LauncherParams lp = new LauncherParams(this, "carreteras_concellos",
	// "Ayuntamientos", "Editar ayuntamientos");
	// antl = new AlphanumericNavTableLauncher(
	// this, lp);
	codigo = (JComboBox) this.getWidgetComponents().get("codigo");
	ayuntamientos = (JTable) this.getWidgetComponents()
		.get("ayuntamientos");
	carreterasConcellos = new FormCarreterasConcellos();
	contextualMenu = new JTableContextualMenu(carreterasConcellos);
	ayuntamientos.addMouseListener(contextualMenu);
    }

    @Override
    public void removeListeners() {
	super.removeListeners();

	ayuntamientos.removeMouseListener(contextualMenu);
    }

    @Override
    public FormPanel getFormBody() {
	if (form == null) {
	    form = new FormPanel("form-carreteras.xml");
	}
	return form;
    }

    @Override
    public Logger getLoggerName() {
	return Logger.getLogger("CarreterasForm");
    }

    @Override
    public String getXMLPath() {
	return PluginServices.getPluginServices("es.icarto.gvsig.viasobras")
		.getClassLoader().getResource("viasobras.xml").getPath();
    }

    @Override
    public void onPositionChange(PositionEvent e) {
	super.onPositionChange(e);
	updateJTables();
    }

}
