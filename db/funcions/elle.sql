DROP SCHEMA IF EXISTS elle CASCADE;
CREATE SCHEMA elle;

CREATE TABLE elle._map (
    mapa character varying(255) NOT NULL,
    nombre_capa character varying(255) NOT NULL,
    nombre_tabla character varying(255),
    posicion integer DEFAULT 0 NOT NULL,
    visible boolean,
    max_escala character varying(50),
    min_escala character varying(50),
    grupo character varying,
    schema character varying,
    localizador boolean,
    CONSTRAINT _map_pkey PRIMARY KEY (mapa, nombre_capa)
);

CREATE TABLE elle._map_style (
    nombre_capa character varying NOT NULL,
    nombre_estilo character varying NOT NULL,
    type character varying(3),
    definicion xml,
    CONSTRAINT _map_style_pkey PRIMARY KEY (nombre_capa, nombre_estilo)
);

CREATE TABLE elle._map_overview (
    mapa character varying NOT NULL,
    nombre_capa character varying NOT NULL,
    schema character varying,
    posicion integer,
    nombre_tabla character varying,
    CONSTRAINT _map_overview_pkey PRIMARY KEY (mapa, nombre_capa)
);

CREATE TABLE elle._map_overview_style (
    nombre_capa character varying NOT NULL,
    nombre_estilo character varying NOT NULL,
    tipo character varying(3),
    definicion xml,
    CONSTRAINT _map_overview_style_pkey PRIMARY KEY (nombre_capa, nombre_estilo)
);

COPY elle._map (mapa, nombre_capa, nombre_tabla, posicion, visible, max_escala, min_escala, grupo, schema, localizador) FROM stdin;
Vías Obras Lugo	Ancho de plataforma	ancho_plataforma	8	t	\N	\N		inventario	\N
Vías Obras Lugo	Carreteras	carreteras	6	t	\N	\N		inventario	\N
Vías Obras Lugo	Concellos	concellos	5	t	\N	\N		info_base	\N
Vías Obras Lugo	Océano	oceano	1	t	\N	\N		info_base	\N
Vías Obras Lugo	Portugal	portugal	2	t	\N	\N		info_base	\N
Vías Obras Lugo	Provincias España	provincias_limitrofes	4	t	\N	\N		info_base	\N
Vías Obras Lugo	Provincias Galicia	provincias_galicia	3	t	\N	\N		info_base	\N
Vías Obras Lugo	Tipo de pavimento	tipo_pavimento	7	t	\N	\N		inventario	\N
\.

COPY elle._map_style (nombre_capa, nombre_estilo, type, definicion) FROM stdin;
Ancho de plataforma	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.VectorialIntervalLegend"/>\n    <property key="useDefaultSymbolB" value="false"/>\n    <property key="useDefaultSymbol" value="1"/>\n    <property key="fieldName" value="ancho_plataforma"/>\n    <property key="index" value="0"/>\n    <property key="intervalType" value="1"/>\n    <property key="numKeys" value="3"/>\n    <property key="tipoValueKeys" value="com.iver.cit.gvsig.fmap.rendering.FInterval"/>\n    <property key="keys" value="0.0-5.0 ,5.0-8.0 ,8.0-16.0"/>\n    <property key="shapeType" value="1026"/>\n    <property key="startColor" value="255,0,0,255"/>\n    <property key="endColor" value="0,0,255,255"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="128,128,128,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="0 - 5"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="153,204,255,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="5 - 8"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="51,102,255,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="&gt; 8"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="0,0,204,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Tipo de pavimento	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.VectorialUniqueValueLegend"/>\n    <property key="fieldNames" value="tipopavime"/>\n    <property key="fieldTypes" value="12"/>\n    <property key="ownOrder" value="false"/>\n    <property key="orders" value=""/>\n    <property key="colorScheme" value="255\\,0\\,127\\,255 ,254\\,0\\,127\\,255 ,253\\,0\\,126\\,255 ,252\\,0\\,126\\,255 ,251\\,0\\,125\\,255 ,250\\,0\\,125\\,255 ,249\\,0\\,124\\,255 ,248\\,0\\,124\\,255 ,247\\,0\\,123\\,255 ,246\\,0\\,123\\,255 ,245\\,0\\,122\\,255 ,244\\,0\\,122\\,255 ,243\\,0\\,121\\,255 ,242\\,0\\,121\\,255 ,241\\,0\\,120\\,255 ,240\\,0\\,120\\,255 ,239\\,0\\,119\\,255 ,238\\,0\\,119\\,255 ,237\\,0\\,118\\,255 ,236\\,0\\,118\\,255 ,235\\,0\\,117\\,255 ,234\\,0\\,117\\,255 ,233\\,0\\,116\\,255 ,232\\,0\\,116\\,255 ,231\\,0\\,115\\,255 ,230\\,0\\,115\\,255 ,229\\,0\\,114\\,255 ,228\\,0\\,114\\,255 ,227\\,0\\,113\\,255 ,226\\,0\\,113\\,255 ,225\\,0\\,112\\,255 ,224\\,0\\,112\\,255 ,223\\,0\\,111\\,255 ,222\\,0\\,111\\,255 ,221\\,0\\,110\\,255 ,220\\,0\\,110\\,255 ,219\\,0\\,109\\,255 ,218\\,0\\,109\\,255 ,217\\,0\\,108\\,255 ,216\\,0\\,108\\,255 ,215\\,0\\,107\\,255 ,214\\,0\\,107\\,255 ,213\\,0\\,106\\,255 ,212\\,0\\,106\\,255 ,211\\,0\\,105\\,255 ,210\\,0\\,105\\,255 ,209\\,0\\,104\\,255 ,208\\,0\\,104\\,255 ,207\\,0\\,103\\,255 ,206\\,0\\,103\\,255 ,205\\,0\\,102\\,255 ,204\\,0\\,102\\,255 ,203\\,0\\,101\\,255 ,202\\,0\\,101\\,255 ,201\\,0\\,100\\,255 ,200\\,0\\,100\\,255 ,199\\,0\\,99\\,255 ,198\\,0\\,99\\,255 ,197\\,0\\,98\\,255 ,196\\,0\\,98\\,255 ,195\\,0\\,97\\,255 ,194\\,0\\,97\\,255 ,193\\,0\\,96\\,255 ,192\\,0\\,96\\,255 ,191\\,0\\,95\\,255 ,190\\,0\\,95\\,255 ,189\\,0\\,94\\,255 ,188\\,0\\,94\\,255 ,187\\,0\\,93\\,255 ,186\\,0\\,93\\,255 ,185\\,0\\,92\\,255 ,184\\,0\\,92\\,255 ,183\\,0\\,91\\,255 ,182\\,0\\,91\\,255 ,181\\,0\\,90\\,255 ,180\\,0\\,90\\,255 ,179\\,0\\,89\\,255 ,178\\,0\\,89\\,255 ,177\\,0\\,88\\,255 ,176\\,0\\,88\\,255 ,175\\,0\\,87\\,255 ,174\\,0\\,87\\,255 ,173\\,0\\,86\\,255 ,172\\,0\\,86\\,255 ,171\\,0\\,85\\,255 ,170\\,0\\,85\\,255 ,169\\,0\\,84\\,255 ,168\\,0\\,84\\,255 ,167\\,0\\,83\\,255 ,166\\,0\\,83\\,255 ,165\\,0\\,82\\,255 ,164\\,0\\,82\\,255 ,163\\,0\\,81\\,255 ,162\\,0\\,81\\,255 ,161\\,0\\,80\\,255 ,160\\,0\\,80\\,255 ,159\\,0\\,79\\,255 ,158\\,0\\,79\\,255 ,157\\,0\\,78\\,255 ,156\\,0\\,78\\,255 ,155\\,0\\,77\\,255 ,154\\,0\\,77\\,255 ,153\\,0\\,76\\,255 ,152\\,0\\,76\\,255 ,151\\,0\\,75\\,255 ,150\\,0\\,75\\,255 ,149\\,0\\,74\\,255 ,148\\,0\\,74\\,255 ,147\\,0\\,73\\,255 ,146\\,0\\,73\\,255 ,145\\,0\\,72\\,255 ,144\\,0\\,72\\,255 ,143\\,0\\,71\\,255 ,142\\,0\\,71\\,255 ,141\\,0\\,70\\,255 ,140\\,0\\,70\\,255 ,139\\,0\\,69\\,255 ,138\\,0\\,69\\,255 ,137\\,0\\,68\\,255 ,136\\,0\\,68\\,255 ,135\\,0\\,67\\,255 ,134\\,0\\,67\\,255 ,133\\,0\\,66\\,255 ,132\\,0\\,66\\,255 ,131\\,0\\,65\\,255 ,130\\,0\\,65\\,255 ,129\\,0\\,64\\,255 ,128\\,0\\,64\\,255 ,127\\,0\\,63\\,255 ,126\\,0\\,63\\,255 ,125\\,0\\,62\\,255 ,124\\,0\\,62\\,255 ,123\\,0\\,61\\,255 ,122\\,0\\,61\\,255 ,121\\,0\\,60\\,255 ,120\\,0\\,60\\,255 ,119\\,0\\,59\\,255 ,118\\,0\\,59\\,255 ,117\\,0\\,58\\,255 ,116\\,0\\,58\\,255 ,115\\,0\\,57\\,255 ,114\\,0\\,57\\,255 ,113\\,0\\,56\\,255 ,112\\,0\\,56\\,255 ,111\\,0\\,55\\,255 ,110\\,0\\,55\\,255 ,109\\,0\\,54\\,255 ,108\\,0\\,54\\,255 ,107\\,0\\,53\\,255 ,106\\,0\\,53\\,255 ,105\\,0\\,52\\,255 ,104\\,0\\,52\\,255 ,103\\,0\\,51\\,255 ,102\\,0\\,51\\,255 ,101\\,0\\,50\\,255 ,100\\,0\\,50\\,255 ,99\\,0\\,49\\,255 ,98\\,0\\,49\\,255 ,97\\,0\\,48\\,255 ,96\\,0\\,48\\,255 ,95\\,0\\,47\\,255 ,94\\,0\\,47\\,255 ,93\\,0\\,46\\,255 ,92\\,0\\,46\\,255 ,91\\,0\\,45\\,255 ,90\\,0\\,45\\,255 ,89\\,0\\,44\\,255 ,88\\,0\\,44\\,255 ,87\\,0\\,43\\,255 ,86\\,0\\,43\\,255 ,85\\,0\\,42\\,255 ,84\\,0\\,42\\,255 ,83\\,0\\,41\\,255 ,82\\,0\\,41\\,255 ,81\\,0\\,40\\,255 ,80\\,0\\,40\\,255 ,79\\,0\\,39\\,255 ,78\\,0\\,39\\,255 ,77\\,0\\,38\\,255 ,76\\,0\\,38\\,255 ,75\\,0\\,37\\,255 ,74\\,0\\,37\\,255 ,73\\,0\\,36\\,255 ,72\\,0\\,36\\,255 ,71\\,0\\,35\\,255 ,70\\,0\\,35\\,255 ,69\\,0\\,34\\,255 ,68\\,0\\,34\\,255 ,67\\,0\\,33\\,255 ,66\\,0\\,33\\,255 ,65\\,0\\,32\\,255 ,64\\,0\\,32\\,255 ,63\\,0\\,31\\,255 ,62\\,0\\,31\\,255 ,61\\,0\\,30\\,255 ,60\\,0\\,30\\,255 ,59\\,0\\,29\\,255 ,58\\,0\\,29\\,255 ,57\\,0\\,28\\,255 ,56\\,0\\,28\\,255 ,55\\,0\\,27\\,255 ,54\\,0\\,27\\,255 ,53\\,0\\,26\\,255 ,52\\,0\\,26\\,255 ,51\\,0\\,25\\,255 ,50\\,0\\,25\\,255 ,49\\,0\\,24\\,255 ,48\\,0\\,24\\,255 ,47\\,0\\,23\\,255 ,46\\,0\\,23\\,255 ,45\\,0\\,22\\,255 ,44\\,0\\,22\\,255 ,43\\,0\\,21\\,255 ,42\\,0\\,21\\,255 ,41\\,0\\,20\\,255 ,40\\,0\\,20\\,255 ,39\\,0\\,19\\,255 ,38\\,0\\,19\\,255 ,37\\,0\\,18\\,255 ,36\\,0\\,18\\,255 ,35\\,0\\,17\\,255 ,34\\,0\\,17\\,255 ,33\\,0\\,16\\,255 ,32\\,0\\,16\\,255 ,31\\,0\\,15\\,255 ,30\\,0\\,15\\,255 ,29\\,0\\,14\\,255 ,28\\,0\\,14\\,255 ,27\\,0\\,13\\,255 ,26\\,0\\,13\\,255 ,25\\,0\\,12\\,255 ,24\\,0\\,12\\,255 ,23\\,0\\,11\\,255 ,22\\,0\\,11\\,255 ,21\\,0\\,10\\,255 ,20\\,0\\,10\\,255 ,19\\,0\\,9\\,255 ,18\\,0\\,9\\,255 ,17\\,0\\,8\\,255 ,16\\,0\\,8\\,255 ,15\\,0\\,7\\,255 ,14\\,0\\,7\\,255 ,13\\,0\\,6\\,255 ,12\\,0\\,6\\,255 ,11\\,0\\,5\\,255 ,10\\,0\\,5\\,255 ,9\\,0\\,4\\,255 ,8\\,0\\,4\\,255 ,7\\,0\\,3\\,255 ,6\\,0\\,3\\,255 ,5\\,0\\,2\\,255 ,4\\,0\\,2\\,255 ,3\\,0\\,1\\,255 ,2\\,0\\,1\\,255 ,1\\,0\\,0\\,255 ,0\\,0\\,0\\,255"/>\n    <property key="labelfield"/>\n    <property key="labelFieldHeight"/>\n    <property key="labelFieldRotation"/>\n    <property key="useDefaultSymbol" value="true"/>\n    <property key="numKeys" value="3"/>\n    <property key="tipoValueKeys" value="com.hardcode.gdbms.engine.values.NullValue"/>\n    <property key="keys" value="B ,H ,M"/>\n    <property key="values" value="Default ,B ,H ,M"/>\n    <property key="typeKeys" value="-1 ,-1 ,-1"/>\n    <property key="typeValues" value="1111 ,-1 ,-1 ,-1"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="Por defecto"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="128,128,128,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="B"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="0,153,0,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="H"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="0,51,255,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc" value="M"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="135,0,67,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Carreteras	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.SingleSymbolLegend"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="desc"/>\n        <property key="unit" value="-1"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="color" value="102,102,102,255"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n            <property key="desc"/>\n            <property key="lineWidth" value="1.0"/>\n            <property key="dashPhase" value="0.0"/>\n            <property key="endCap" value="0"/>\n            <property key="lineJoin" value="2"/>\n            <property key="miterLimit" value="10.0"/>\n            <property key="offset" value="0.0"/>\n            <property key="unit" value="0"/>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Concellos	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.SingleSymbolLegend"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleFillSymbol"/>\n        <property key="color" value="255,204,102,255"/>\n        <property key="hasFill" value="true"/>\n        <property key="desc"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="unit" value="-1"/>\n        <property key="hasOutline" value="true"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n            <property key="isShapeVisible" value="true"/>\n            <property key="desc"/>\n            <property key="unit" value="-1"/>\n            <property key="referenceSystem" value="0"/>\n            <property key="color" value="255,255,255,255"/>\n            <xml-tag>\n                <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n                <property key="desc"/>\n                <property key="lineWidth" value="1.0"/>\n                <property key="dashPhase" value="0.0"/>\n                <property key="endCap" value="0"/>\n                <property key="lineJoin" value="2"/>\n                <property key="miterLimit" value="10.0"/>\n                <property key="offset" value="0.0"/>\n                <property key="unit" value="0"/>\n            </xml-tag>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Provincias España	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.SingleSymbolLegend"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleFillSymbol"/>\n        <property key="color" value="230,230,219,255"/>\n        <property key="hasFill" value="true"/>\n        <property key="desc"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="unit" value="-1"/>\n        <property key="hasOutline" value="true"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n            <property key="isShapeVisible" value="true"/>\n            <property key="desc"/>\n            <property key="unit" value="-1"/>\n            <property key="referenceSystem" value="0"/>\n            <property key="color" value="153,153,153,192"/>\n            <xml-tag>\n                <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n                <property key="desc"/>\n                <property key="lineWidth" value="1.0"/>\n                <property key="dashPhase" value="0.0"/>\n                <property key="endCap" value="0"/>\n                <property key="lineJoin" value="2"/>\n                <property key="miterLimit" value="10.0"/>\n                <property key="offset" value="0.0"/>\n                <property key="unit" value="0"/>\n            </xml-tag>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Provincias Galicia	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.SingleSymbolLegend"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleFillSymbol"/>\n        <property key="color" value="204,204,204,255"/>\n        <property key="hasFill" value="true"/>\n        <property key="desc"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="unit" value="-1"/>\n        <property key="hasOutline" value="true"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n            <property key="isShapeVisible" value="true"/>\n            <property key="desc"/>\n            <property key="unit" value="-1"/>\n            <property key="referenceSystem" value="0"/>\n            <property key="color" value="102,102,102,255"/>\n            <xml-tag>\n                <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n                <property key="desc"/>\n                <property key="lineWidth" value="1.0"/>\n                <property key="dashPhase" value="0.0"/>\n                <property key="endCap" value="0"/>\n                <property key="lineJoin" value="2"/>\n                <property key="miterLimit" value="10.0"/>\n                <property key="offset" value="0.0"/>\n                <property key="unit" value="0"/>\n            </xml-tag>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Portugal	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.SingleSymbolLegend"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleFillSymbol"/>\n        <property key="color" value="209,217,211,255"/>\n        <property key="hasFill" value="true"/>\n        <property key="desc"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="unit" value="-1"/>\n        <property key="hasOutline" value="true"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n            <property key="isShapeVisible" value="true"/>\n            <property key="desc"/>\n            <property key="unit" value="-1"/>\n            <property key="referenceSystem" value="0"/>\n            <property key="color" value="128,128,128,255"/>\n            <xml-tag>\n                <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n                <property key="desc"/>\n                <property key="lineWidth" value="1.0"/>\n                <property key="dashPhase" value="0.0"/>\n                <property key="endCap" value="0"/>\n                <property key="lineJoin" value="2"/>\n                <property key="miterLimit" value="10.0"/>\n                <property key="offset" value="0.0"/>\n                <property key="unit" value="0"/>\n            </xml-tag>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
Océano	Vías Obras Lugo	gvl	<xml-tag xmlns="http://www.gvsig.gva.es">\n    <property key="className" value="com.iver.cit.gvsig.fmap.rendering.SingleSymbolLegend"/>\n    <xml-tag>\n        <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleFillSymbol"/>\n        <property key="color" value="153,204,255,91"/>\n        <property key="hasFill" value="true"/>\n        <property key="desc"/>\n        <property key="isShapeVisible" value="true"/>\n        <property key="referenceSystem" value="0"/>\n        <property key="unit" value="-1"/>\n        <property key="hasOutline" value="false"/>\n        <xml-tag>\n            <property key="className" value="com.iver.cit.gvsig.fmap.core.symbols.SimpleLineSymbol"/>\n            <property key="isShapeVisible" value="true"/>\n            <property key="desc"/>\n            <property key="unit" value="-1"/>\n            <property key="referenceSystem" value="0"/>\n            <property key="color" value="128,128,128,255"/>\n            <xml-tag>\n                <property key="className" value="com.iver.cit.gvsig.fmap.core.styles.SimpleLineStyle"/>\n                <property key="desc"/>\n                <property key="lineWidth" value="1.0"/>\n                <property key="dashPhase" value="0.0"/>\n                <property key="endCap" value="0"/>\n                <property key="lineJoin" value="2"/>\n                <property key="miterLimit" value="10.0"/>\n                <property key="offset" value="0.0"/>\n                <property key="unit" value="0"/>\n            </xml-tag>\n        </xml-tag>\n    </xml-tag>\n</xml-tag>\n
\.
