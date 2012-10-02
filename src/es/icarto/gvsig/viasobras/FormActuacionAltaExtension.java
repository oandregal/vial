package es.icarto.gvsig.viasobras;

import com.iver.andami.PluginServices;
import com.iver.andami.messages.NotificationManager;
import com.iver.andami.plugins.Extension;
import com.iver.cit.gvsig.fmap.layers.FLyrVect;

import es.icarto.gvsig.navtableforms.ormlite.ORMLite;
import es.icarto.gvsig.navtableforms.utils.TOCLayerManager;
import es.icarto.gvsig.viasobras.forms.FormActuacionesAlta;
import es.udc.cartolab.gvsig.users.utils.DBSession;

public class FormActuacionAltaExtension extends Extension {

    private String actuacionesLayerName = "Actuaciones";

    public void initialize() {
	registerIcons();
    }

    private void registerIcons() {
	PluginServices.getIconTheme().registerDefault(
		"viasobras-actuacion-alta",
		this.getClass().getClassLoader()
		.getResource("images/actuacion_alta.png"));
    }

    public void execute(String actionCommand) {
	TOCLayerManager toc = new TOCLayerManager();
	FLyrVect l = toc.getLayerByName(actuacionesLayerName);
	try {
	    if (l != null) {
		ORMLite ormlite = new ORMLite();
		String xmlFile = PluginServices
			.getPluginServices("es.icarto.gvsig.viasobras")
			.getClassLoader().getResource("viasobras-metadata.xml")
			.getPath();
		ormlite.reloadCache(xmlFile);
		FormActuacionesAlta dialog = new FormActuacionesAlta(l);
		PluginServices.getMDIManager().addCentredWindow(dialog);
	    }
	} catch (Exception e) {
	    NotificationManager.addError(e);
	}
    }

    public boolean isEnabled() {
	TOCLayerManager toc = new TOCLayerManager();
	DBSession dbs = DBSession.getCurrentSession();
	return (dbs != null) && (dbs.getJavaConnection() != null)
		&& (toc.getLayerByName(actuacionesLayerName) != null);
    }

    public boolean isVisible() {
	return true;
    }

}
