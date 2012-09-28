BEGIN;

-- definition
DROP TABLE IF EXISTS inventario.actuaciones;
CREATE TABLE inventario.actuaciones(
       gid SERIAL,
       codigo_actuacion varchar(4) UNIQUE,
       codigo_carretera varchar(4),
       pk_inicial float,
       pk_final float,
       tipo varchar(24),
       descripcion varchar(64),
       titulo_proyecto varchar(64),
       importe float,
       contratista varchar(24),
       fecha date,
       observaciones text,
       CONSTRAINT pk_actuaciones PRIMARY KEY(gid),
       FOREIGN KEY (codigo_carretera) REFERENCES inventario.carreteras (numero) ON DELETE CASCADE
);

-- linear referencing
SELECT AddGeometryColumn('inventario', 'actuaciones', 'the_geom', '25829', 'MULTILINESTRINGM', 3);
ALTER TABLE inventario.actuaciones DROP CONSTRAINT enforce_geotype_the_geom;
SELECT inventario.update_geom_line_all('inventario', 'actuaciones');

-- triggers
DROP TRIGGER IF EXISTS update_geom_actuaciones ON inventario.actuaciones;
CREATE TRIGGER update_geom_actuaciones
       BEFORE UPDATE OR INSERT
       ON inventario.actuaciones FOR EACH ROW
       EXECUTE PROCEDURE inventario.update_geom_line_on_pk_change();

COMMIT;
