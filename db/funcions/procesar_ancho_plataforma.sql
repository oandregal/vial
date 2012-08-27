BEGIN;

-- definition
DROP TABLE IF EXISTS inventario.ancho_plataforma;
CREATE TABLE inventario.ancho_plataforma (
       gid serial,
       codigo_carretera varchar(4),
       codigo_municipio varchar(5),
       tramo varchar(1),
       pk_inicial float,
       pk_final float,
       longitud float,
       fecha_actualizacion date,
       valor double precision,
       observaciones text,
       PRIMARY KEY(gid)
);

-- populate it
INSERT INTO inventario.ancho_plataforma(
       SELECT nextval('inventario.ancho_plataforma_gid_seq') AS gid,
              "numero_inv" AS codigo_carretera,
              to_char("cod_munici", 'FM99999') AS codigo_municipio,
              "tramo" AS tramo,
              "pk_ini" AS pk_inicial,
              "pk_fin" AS pk_final,
              "longitud_t" AS longitud,
              NULL AS fecha_actualizacion,
              "ancho_medi" AS valor,
              "observacio" AS observaciones
       FROM inventario.ancho_plataforma_tmp
);

DELETE FROM inventario.ancho_plataforma WHERE
       pk_inicial IS NULL AND pk_final IS NULL;

DROP TABLE IF EXISTS inventario.ancho_plataforma_tmp;

-- linear referencing
SELECT AddGeometryColumn('inventario', 'ancho_plataforma', 'the_geom', '23029', 'MULTILINESTRINGM', 3);
ALTER TABLE inventario.ancho_plataforma DROP CONSTRAINT enforce_geotype_the_geom;
SELECT inventario.update_geom_all('inventario', 'ancho_plataforma');

-- triggers
DROP TRIGGER IF EXISTS update_geom_ancho_plataforma ON inventario.ancho_plataforma;
CREATE TRIGGER update_geom_ancho_plataforma
       BEFORE UPDATE OR INSERT
       ON inventario.ancho_plataforma FOR EACH ROW
       EXECUTE PROCEDURE inventario.update_geom_on_pk_change();

COMMIT;