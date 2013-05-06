SELECT mun_cod.nombre AS "Municipio", \
       actuaciones.codigo_actuacion AS "Código actuación", \
       actuaciones.codigo_carretera AS "Código (LU-P)", \
       carreteras.denominacion as "Denominación", \
       actuaciones.pk_inicial AS "PK inicial", \
       actuaciones.pk_final AS "PK_final", \
       actuaciones.vialidad_codigo AS "Código", \
       actuaciones.vialidad_peticionario AS "Peticionario", \
       actuaciones.vialidad_beneficiario AS "Beneficiario", \
       actuaciones.vialidad_fecha_peticion AS "Fecha", \
       actuaciones.vialidad_observaciones AS "Observaciones" \
FROM inventario.actuaciones actuaciones, \
     inventario.actuacion_municipio act_mun, \
     inventario.municipio_codigo mun_cod, \
     inventario.carreteras carreteras \
WHERE actuaciones.codigo_actuacion = act_mun.codigo_actuacion AND \
      act_mun.codigo_municipio = mun_cod.codigo AND \
      actuaciones.codigo_carretera = carreteras.numero AND \
      actuaciones.tipo = 'Vialidad' \
[[WHERE]];