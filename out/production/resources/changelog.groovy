databaseChangeLog = {

    changeSet(author: "1200 (generated)", id: "1511656030485-1") {
        createSequence(sequenceName: "hibernate_sequence")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-2") {
        createTable(tableName: "CATEGORIAS") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "descripcion", type: "CLOB")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-3") {
        createTable(tableName: "EMBARQUES") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "archivo_evidencia", type: "VARCHAR(255)")

            column(name: "codigo", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "demora", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "empresa_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "establecimiento_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "estatus", type: "VARCHAR(11)") {
                constraints(nullable: "false")
            }

            column(name: "fecha_hora_entrega", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "id_repartidor_id", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-4") {
        createTable(tableName: "EMPRESAS") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "activo", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "ciudad", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "codigo", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "contacto", type: "VARCHAR(255)")

            column(name: "contacto_cargo", type: "VARCHAR(255)")

            column(name: "contacto_email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "contacto_telefono", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "cp", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "domicilio", type: "CLOB")

            column(name: "establecimiento_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "giro", type: "VARCHAR(18)") {
                constraints(nullable: "false")
            }

            column(name: "hora_entrega_1", type: "VARCHAR(255)")

            column(name: "hora_entrega_2", type: "VARCHAR(255)")

            column(name: "hora_entrega_3", type: "VARCHAR(255)")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "no_empleados", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "razon_social", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "rfc", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "telefono", type: "VARCHAR(255)")

            column(name: "url", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-5") {
        createTable(tableName: "ESTABLECIMIENTOS") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "administrador_id", type: "BIGINT")

            column(name: "ciudad", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "codigo", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "cp", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "domicilio", type: "CLOB")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "telefono", type: "VARCHAR(255)")

            column(name: "total_vendido", type: "FLOAT4")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-6") {
        createTable(tableName: "EXTRAS") {
            column(name: "ID", type: "VARCHAR(255)")

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "categoria_id", type: "VARCHAR(255)")

            column(name: "costo", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "descripcion", type: "CLOB")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "precio", type: "FLOAT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-7") {
        createTable(tableName: "HORARIOS") {
            column(name: "ID", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "fecha_final", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "fecha_inicio", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "matricula", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-8") {
        createTable(tableName: "INGREDIENTES") {
            column(name: "ID", type: "VARCHAR(255)")

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "unidad_medicion", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-9") {
        createTable(tableName: "ORDENES") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "calificacion_comentario", type: "VARCHAR(255)")

            column(name: "calificacion_comida", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "calificacion_servicio", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "empresa_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "establecimiento_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "estatus", type: "VARCHAR(11)") {
                constraints(nullable: "false")
            }

            column(name: "fecha_pago", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "modo_pago", type: "VARCHAR(10)") {
                constraints(nullable: "false")
            }

            column(name: "monto_pagado", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "no_orden", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-10") {
        createTable(tableName: "ORDENESDETALLES") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "cantidad", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "embarque_id", type: "VARCHAR(255)")

            column(name: "es_personalizado", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "establecimiento_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "estatus", type: "VARCHAR(11)") {
                constraints(nullable: "false")
            }

            column(name: "fecha_entrega", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "fecha_pago", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "hora_entrega", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "modo_pago", type: "VARCHAR(10)") {
                constraints(nullable: "false")
            }

            column(name: "monto_pagado", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "orden_id", type: "VARCHAR(255)")

            column(name: "personalizado", type: "VARCHAR(255)")

            column(name: "platillo_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "realmente_pagado", type: "BOOLEAN")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-11") {
        createTable(tableName: "PLATILLOS") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "activo", type: "BOOLEAN")

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "calorias", type: "INT")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "descripcion", type: "CLOB")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "tipo_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-12") {
        createTable(tableName: "PLATILLOSESTABLECIMIENTOS") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "activo", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "costo", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "establecimiento_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "platillo_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "precio", type: "FLOAT4") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-13") {
        createTable(tableName: "ROLELG") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "ROLELGPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-14") {
        createTable(tableName: "SESION") {
            column(name: "ID", type: "VARCHAR(255)")

            column(name: "FECHA_INGRESO", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "ID_USER", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "SESION_NO", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-15") {
        createTable(tableName: "TIPOS") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "descripcion", type: "CLOB")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "super_tipo", type: "VARCHAR(7)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-16") {
        createTable(tableName: "TRANSACCIONES") {
            column(name: "ID", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "estatus", type: "VARCHAR(9)") {
                constraints(nullable: "false")
            }

            column(name: "fecha_operacion", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "id_autor", type: "BIGINT")

            column(name: "id_orden", type: "VARCHAR(255)")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "monto", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "referencia", type: "VARCHAR(255)")

            column(name: "tipo_medio_pago", type: "VARCHAR(16)") {
                constraints(nullable: "false")
            }

            column(name: "tipo_transaccion", type: "VARCHAR(9)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-17") {
        createTable(tableName: "USERLG") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "USERLGPK")
            }

            column(name: "account_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "acepta_terminos", type: "BOOLEAN")

            column(name: "archivo_foto", type: "VARCHAR(255)")

            column(name: "ciudad", type: "VARCHAR(255)")

            column(name: "consume_alcohol", type: "BOOLEAN")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "empresa_id", type: "VARCHAR(255)")

            column(name: "enabled", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "enfermedades_cronicas", type: "CLOB")

            column(name: "establecimiento_id", type: "VARCHAR(255)")

            column(name: "estatura", type: "FLOAT4")

            column(name: "fecha_nacimiento", type: "timestamp")

            column(name: "id_autor", type: "BIGINT")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "monto_adeudo", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "monto_limite", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "monto_saldo", type: "FLOAT4") {
                constraints(nullable: "false")
            }

            column(name: "nombre", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "pago_nomina", type: "BOOLEAN")

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "peso", type: "INT")

            column(name: "realiza_deportes", type: "BOOLEAN")

            column(name: "rfc", type: "VARCHAR(255)")

            column(name: "sesion_activa", type: "BOOLEAN")

            column(name: "session_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "sexo", type: "VARCHAR(9)")

            column(name: "solicitar_pago_nomina", type: "BOOL")

            column(name: "telefono_movil", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "tipo_dieta", type: "VARCHAR(255)")

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-18") {
        createTable(tableName: "USERLG_ROLELG") {
            column(name: "userlg_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "rolelg_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-19") {
        createTable(tableName: "embarques_ordenesdetalles") {
            column(name: "ws_embarque_ordenes_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "orden_detalle_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-20") {
        createTable(tableName: "ordenes_ordenesdetalles") {
            column(name: "ws_orden_detalles_id", type: "VARCHAR(255)")

            column(name: "orden_detalle_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-21") {
        createTable(tableName: "platillos_categorias") {
            column(name: "platillo_categorias_id", type: "VARCHAR(255)")

            column(name: "categoria_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-22") {
        createTable(tableName: "platillos_ingredientes") {
            column(name: "platillo_ingredientes_id", type: "VARCHAR(255)")

            column(name: "ingrediente_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-23") {
        createTable(tableName: "userlg_ingredientes") {
            column(name: "id_ingrediente", type: "BIGINT")

            column(name: "ingrediente_id", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-24") {
        addPrimaryKey(columnNames: "ID", constraintName: "CATEGORIASPK", tableName: "CATEGORIAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-25") {
        addPrimaryKey(columnNames: "ID", constraintName: "EMBARQUESPK", tableName: "EMBARQUES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-26") {
        addPrimaryKey(columnNames: "ID", constraintName: "EMPRESASPK", tableName: "EMPRESAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-27") {
        addPrimaryKey(columnNames: "ID", constraintName: "ESTABLECIMIENTOSPK", tableName: "ESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-28") {
        addPrimaryKey(columnNames: "ID", constraintName: "EXTRASPK", tableName: "EXTRAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-29") {
        addPrimaryKey(columnNames: "ID", constraintName: "HORARIOSPK", tableName: "HORARIOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-30") {
        addPrimaryKey(columnNames: "ID", constraintName: "INGREDIENTESPK", tableName: "INGREDIENTES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-31") {
        addPrimaryKey(columnNames: "ID", constraintName: "ORDENESDETALLESPK", tableName: "ORDENESDETALLES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-32") {
        addPrimaryKey(columnNames: "ID", constraintName: "ORDENESPK", tableName: "ORDENES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-33") {
        addPrimaryKey(columnNames: "ID", constraintName: "PLATILLOSESTABLECIMIENTOSPK", tableName: "PLATILLOSESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-34") {
        addPrimaryKey(columnNames: "ID", constraintName: "PLATILLOSPK", tableName: "PLATILLOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-35") {
        addPrimaryKey(columnNames: "ID", constraintName: "SESIONPK", tableName: "SESION")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-36") {
        addPrimaryKey(columnNames: "ID", constraintName: "TIPOSPK", tableName: "TIPOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-37") {
        addPrimaryKey(columnNames: "ID", constraintName: "TRANSACCIONESPK", tableName: "TRANSACCIONES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-38") {
        addPrimaryKey(columnNames: "userlg_id, rolelg_id", constraintName: "USERLG_ROLELGPK", tableName: "USERLG_ROLELG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-39") {
        addUniqueConstraint(columnNames: "codigo", constraintName: "UC_EMBARQUESCODIGO_COL", tableName: "EMBARQUES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-40") {
        addUniqueConstraint(columnNames: "codigo", constraintName: "UC_EMPRESASCODIGO_COL", tableName: "EMPRESAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-41") {
        addUniqueConstraint(columnNames: "nombre", constraintName: "UC_EMPRESASNOMBRE_COL", tableName: "EMPRESAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-42") {
        addUniqueConstraint(columnNames: "nombre", constraintName: "UC_EXTRASNOMBRE_COL", tableName: "EXTRAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-43") {
        addUniqueConstraint(columnNames: "nombre", constraintName: "UC_INGREDIENTESNOMBRE_COL", tableName: "INGREDIENTES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-44") {
        addUniqueConstraint(columnNames: "nombre", constraintName: "UC_PLATILLOSNOMBRE_COL", tableName: "PLATILLOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-45") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_ROLELGAUTHORITY_COL", tableName: "ROLELG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-46") {
        addUniqueConstraint(columnNames: "nombre", constraintName: "UC_TIPOSNOMBRE_COL", tableName: "TIPOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-47") {
        addUniqueConstraint(columnNames: "username", constraintName: "UC_USERLGUSERNAME_COL", tableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-48") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "EMBARQUES", constraintName: "FK_102n9jgpvxtgdyn6cnrehs2bn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-49") {
        addForeignKeyConstraint(baseColumnNames: "empresa_id", baseTableName: "ORDENES", constraintName: "FK_1fyabj9t5vyoiwk5xrvp3sx99", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "EMPRESAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-50") {
        addForeignKeyConstraint(baseColumnNames: "empresa_id", baseTableName: "USERLG", constraintName: "FK_1vcujtcietl3s4ql0qhhrb7dt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "EMPRESAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-51") {
        addForeignKeyConstraint(baseColumnNames: "categoria_id", baseTableName: "EXTRAS", constraintName: "FK_20i9f6uqc0swiiicw1c9r2c06", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "CATEGORIAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-52") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "USERLG", constraintName: "FK_24k98xh51vbpg1yr9195wlfvo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-53") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "ESTABLECIMIENTOS", constraintName: "FK_2at4ccv5j9vcwsxll4yu0a7vg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-54") {
        addForeignKeyConstraint(baseColumnNames: "establecimiento_id", baseTableName: "EMBARQUES", constraintName: "FK_2cpju9ted9vbjhldivhraes8m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-55") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "EXTRAS", constraintName: "FK_2lslgap6yx4ful5blqtw7p0jn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-56") {
        addForeignKeyConstraint(baseColumnNames: "embarque_id", baseTableName: "ORDENESDETALLES", constraintName: "FK_2s9kyf61aadtn1bwvvmxi00wo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "EMBARQUES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-57") {
        addForeignKeyConstraint(baseColumnNames: "empresa_id", baseTableName: "EMBARQUES", constraintName: "FK_2yxf7ooasm685csuxk9g081cg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "EMPRESAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-58") {
        addForeignKeyConstraint(baseColumnNames: "orden_detalle_id", baseTableName: "embarques_ordenesdetalles", constraintName: "FK_4uthlmeusbsuc2vyk6m6dr7im", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ORDENESDETALLES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-59") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "HORARIOS", constraintName: "FK_5a62gb4a1mdbsxdlb1yk3yp8m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-60") {
        addForeignKeyConstraint(baseColumnNames: "establecimiento_id", baseTableName: "ORDENESDETALLES", constraintName: "FK_68ya2d8pisov03ibhcepjgd93", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-61") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "ROLELG", constraintName: "FK_7cqfvyadp3fwwcayglbipxk6i", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-62") {
        addForeignKeyConstraint(baseColumnNames: "platillo_categorias_id", baseTableName: "platillos_categorias", constraintName: "FK_89btww7nrjk9yiaw9urdorbdg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "PLATILLOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-63") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "PLATILLOS", constraintName: "FK_aof9nwnju1c0m5h0u6hur0cq1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-64") {
        addForeignKeyConstraint(baseColumnNames: "id_repartidor_id", baseTableName: "EMBARQUES", constraintName: "FK_auaasegoc04uefgs6ir63f6lb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-65") {
        addForeignKeyConstraint(baseColumnNames: "ws_orden_detalles_id", baseTableName: "ordenes_ordenesdetalles", constraintName: "FK_b6eb5a3s9kj9uay4s2nl5tpoa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ORDENES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-66") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "TIPOS", constraintName: "FK_c6k3jqdy9kc3suxfawl1wo5hk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-67") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "ORDENESDETALLES", constraintName: "FK_dinelkjqhhymnuhrc39xyn5vm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-68") {
        addForeignKeyConstraint(baseColumnNames: "establecimiento_id", baseTableName: "USERLG", constraintName: "FK_dun2w7lkxwihs01pbpoi0lvl1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-69") {
        addForeignKeyConstraint(baseColumnNames: "orden_detalle_id", baseTableName: "ordenes_ordenesdetalles", constraintName: "FK_fsr1i884y9wg7f4f2y7dn4gob", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ORDENESDETALLES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-70") {
        addForeignKeyConstraint(baseColumnNames: "platillo_ingredientes_id", baseTableName: "platillos_ingredientes", constraintName: "FK_g1kutf7bohd6jyxbj4s0xl89w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "PLATILLOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-71") {
        addForeignKeyConstraint(baseColumnNames: "establecimiento_id", baseTableName: "PLATILLOSESTABLECIMIENTOS", constraintName: "FK_gfd39rudm1lai95yavhwrdq6m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-72") {
        addForeignKeyConstraint(baseColumnNames: "tipo_id", baseTableName: "PLATILLOS", constraintName: "FK_ggpw0rv3jm8nt88n8fwampetg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "TIPOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-73") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "ORDENES", constraintName: "FK_h9jclnvjfpgu710g05jdx1fer", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-74") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "INGREDIENTES", constraintName: "FK_ho9ohegv977a7q9nkkpwemheq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-75") {
        addForeignKeyConstraint(baseColumnNames: "ingrediente_id", baseTableName: "platillos_ingredientes", constraintName: "FK_ic8pfsx384oacfyafpq5qo1v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "INGREDIENTES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-76") {
        addForeignKeyConstraint(baseColumnNames: "categoria_id", baseTableName: "platillos_categorias", constraintName: "FK_jc23mqxuqu4cetw2589icgjub", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "CATEGORIAS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-77") {
        addForeignKeyConstraint(baseColumnNames: "orden_id", baseTableName: "ORDENESDETALLES", constraintName: "FK_jflewl5vkko98uqrbdp4yepfp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ORDENES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-78") {
        addForeignKeyConstraint(baseColumnNames: "platillo_id", baseTableName: "ORDENESDETALLES", constraintName: "FK_jye5x5pg734nadbnyyar02wpo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "PLATILLOSESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-79") {
        addForeignKeyConstraint(baseColumnNames: "establecimiento_id", baseTableName: "EMPRESAS", constraintName: "FK_khfc8odgawqyomc69p8g2nonr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ESTABLECIMIENTOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-80") {
        addForeignKeyConstraint(baseColumnNames: "ws_embarque_ordenes_id", baseTableName: "embarques_ordenesdetalles", constraintName: "FK_kmijsjmwjv8w553vrrj3y66bs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "EMBARQUES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-81") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "CATEGORIAS", constraintName: "FK_loptcxa0xiobxxuvfw5y9egwp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-82") {
        addForeignKeyConstraint(baseColumnNames: "ingrediente_id", baseTableName: "userlg_ingredientes", constraintName: "FK_m62d0cgvkj6rijdp0l2cyuec", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "INGREDIENTES")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-83") {
        addForeignKeyConstraint(baseColumnNames: "platillo_id", baseTableName: "PLATILLOSESTABLECIMIENTOS", constraintName: "FK_md5yc2cwq0p22caa43gmerr3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "PLATILLOS")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-84") {
        addForeignKeyConstraint(baseColumnNames: "rolelg_id", baseTableName: "USERLG_ROLELG", constraintName: "FK_njvhvoi6k6hd61s7pl6oshjkk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "ROLELG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-85") {
        addForeignKeyConstraint(baseColumnNames: "userlg_id", baseTableName: "USERLG_ROLELG", constraintName: "FK_odoqthc1493antalg1m5x9754", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-86") {
        addForeignKeyConstraint(baseColumnNames: "administrador_id", baseTableName: "ESTABLECIMIENTOS", constraintName: "FK_pw2rgymih4vgsu2egw5ey13y1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-87") {
        addForeignKeyConstraint(baseColumnNames: "id_ingrediente", baseTableName: "userlg_ingredientes", constraintName: "FK_r7m6p0v1f0lqhlqqn22fi01e6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-88") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "TRANSACCIONES", constraintName: "FK_r9l4dy8vn502hygfu8umusrg4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-89") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "PLATILLOSESTABLECIMIENTOS", constraintName: "FK_rqcb4t6ks6qgj0jk13f38djbj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-90") {
        addForeignKeyConstraint(baseColumnNames: "id_autor", baseTableName: "EMPRESAS", constraintName: "FK_s98l3d1gjlxcpdkgrtg8t9co6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "USERLG")
    }

    changeSet(author: "1200 (generated)", id: "1511656030485-91") {
        addForeignKeyConstraint(baseColumnNames: "establecimiento_id", baseTableName: "ORDENES", constraintName: "FK_utnoixt90uef5mcml8kwyful", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "ID", referencedTableName: "ESTABLECIMIENTOS")
    }
}
