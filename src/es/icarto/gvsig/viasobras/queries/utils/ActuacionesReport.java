package es.icarto.gvsig.viasobras.queries.utils;

import java.util.ArrayList;

import com.lowagie.text.pdf.PdfPTable;

public class ActuacionesReport extends Report {
    private final static float MUNICIPIO = 60f;
    private final static float CODIGO_AND_PK_FIELDS = 40f;
    private final static float CODIGO_ACTUACION = 50f;
    private final static float OBSERVACIONES = 120f;

    public ActuacionesReport(int reportType, String fileName,
	    ArrayList<TableModelResults> resultMap, String[] filters) {
	super(reportType, fileName, resultMap, filters);
	// TODO Auto-generated constructor stub
    }
    
    @Override
    protected float[] getColumnsWidth(PdfPTable table, int columnCount) {
	float[] columnsWidth = new float[columnCount];
	for (int i = 0; i < columnCount; i++) {
	    if (i == 0) {
		columnsWidth[i] = MUNICIPIO;
	    }else if (i == 1 || i == 2 || i == 3) {
		columnsWidth[i] = CODIGO_AND_PK_FIELDS;
	    }else if (i == 4) {
		columnsWidth[i] = CODIGO_ACTUACION;
	    }else if (i == columnCount-1) {
		columnsWidth[i] = OBSERVACIONES;
	    }else {
		columnsWidth[i] = ((table.getTotalWidth()
			-MUNICIPIO-(CODIGO_AND_PK_FIELDS*3)-CODIGO_ACTUACION-OBSERVACIONES)
			/(columnCount-6));
	    }
	}
	return columnsWidth;
    }

}
