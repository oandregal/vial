--DROP DATABASE IF EXISTS vias_obras;
--CREATE DATABASE vias_obras OWNER postgres TEMPLATE template_postgis;

DROP SCHEMA IF EXISTS inventario CASCADE;
CREATE SCHEMA inventario;

DROP TABLE IF EXISTS inventario.carreteras_concellos;
CREATE TABLE inventario.carreteras_concellos(
       id SERIAL,
       codigo_carretera varchar(4),
       codigo_concello varchar(5),
       pk_inicial float8,
       pk_final float8,
       observaciones text,
       CONSTRAINT pk_carreteras_concellos PRIMARY KEY (codigo_concello, codigo_carretera)
);

DROP TABLE IF EXISTS inventario.actuacions_concellos;
CREATE TABLE inventario.actuacions_concellos(
       id SERIAL,
       codigo_actuacion varchar(4),
       codigo_concello varchar(5),
       CONSTRAINT pk_actuacions_concellos PRIMARY KEY (codigo_concello, codigo_actuacion)
);

DROP TABLE IF EXISTS inventario.municipio_codigo;
CREATE TABLE inventario.municipio_codigo(
       codigo varchar(5),
       nombre varchar (21),
       CONSTRAINT pk_municipio_codigo PRIMARY KEY (codigo)
);
