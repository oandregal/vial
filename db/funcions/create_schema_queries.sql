DROP SCHEMA IF EXISTS queries CASCADE;
CREATE SCHEMA queries;

DROP TABLE IF EXISTS queries.queries;
CREATE TABLE queries.queries(
       codigo character varying(5) NOT NULL,
       descripcion character varying(250),
       consulta character varying(1500),
       haswhere character varying(2),
       titulo character varying(250),
       subtitulo character varying(250),
       CONSTRAINT pk_consultas PRIMARY KEY (codigo)
);

INSERT INTO queries.queries (codigo, descripcion, consulta, haswhere, titulo)
       VALUES ('C1',
       'Longitud de tramos, por ancho de plataforma',
       'SELECT CASE WHEN ancho_plataforma BETWEEN 0 AND 5 THEN ''0-5''
                    WHEN ancho_plataforma > 5 AND ancho_plataforma <= 8 THEN ''5-8''
                    WHEN ancho_plataforma > 8 THEN ''>8'' END AS "Ancho de plataforma",
               SUM(finaltramo-origentram) AS "Longitud (km)"
        FROM inventario.ancho_plataforma
        [[WHERE]] GROUP BY 1 ORDER BY 1',
       'NO',
       'Longitud de tramos, por ancho de plataforma');

INSERT INTO queries.queries (codigo, descripcion, consulta, haswhere, titulo)
       VALUES ('C2',
       'Longitud de tramos, por tipo de pavimento',
       'SELECT tipopavime, SUM(finalpavim-origenpavi) AS "Longitud" FROM inventario.tipo_pavimento GROUP BY tipopavime ORDER BY tipopavime',
       'NO',
       'Longitud de tramos, por tipo de pavimento');