CREATE DATABASE `met` /*!40100 COLLATE 'utf8_general_ci' */;

CREATE TABLE `ambito` (
	`ID` INT(11) NOT NULL,
	`CODIGO` INT(11) NULL DEFAULT NULL,
	`DESCRIPCION` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci'
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `arquetipo` (
	`ID` INT(11) NOT NULL,
	`NOMBRE` INT(11) NOT NULL,
	`ID_FORMATO` INT(11) NOT NULL,
	PRIMARY KEY (`ID`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `formato` (
	`ID` INT(11) NOT NULL,
	`NOMBRE` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8_general_ci'
)
COMMENT='Tabla de formatos disponibles'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `fuente` (
	`ID` INT(11) NOT NULL,
	`NOMBRE` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8_general_ci',
	`FECHA_ALTA` DATE NOT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `arquetipo` (
	`ID` INT(11) NOT NULL,
	`NOMBRE` INT(11) NOT NULL,
	`ID_FORMATO` INT(11) NOT NULL,
	PRIMARY KEY (`ID`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `metajuego` (
	`ID` INT(11) NOT NULL,
	`NOMBRE` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8_general_ci',
	`ID_FORMATO` INT(11) NOT NULL DEFAULT '0',
	`ID_FUENTE` INT(11) NOT NULL DEFAULT '0',
	`ID_AMBITO` INT(11) NOT NULL DEFAULT '0',
	`FECHA_ALTA` DATE NULL DEFAULT '0000-00-00',
	`FECHA_DESDE` DATE NULL DEFAULT '0000-00-00',
	`FECHA_HASTA` DATE NULL DEFAULT '0000-00-00'
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `arquetipo_metajuego` (
	`ID` INT(11) NOT NULL,
	`ID_ARQUETIPO` INT(11) NOT NULL,
	`ID_METAJUEGO` INT(11) NOT NULL,
	PRIMARY KEY (`ID`) USING BTREE,
	INDEX `IN_ARQUETIPO` (`ID_ARQUETIPO`) USING BTREE,
	INDEX `IN_METAJUEGO` (`ID_METAJUEGO`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `metajuego` (
	`ID` INT(11) NOT NULL,
	`NOMBRE` VARCHAR(50) NOT NULL DEFAULT '' COLLATE 'utf8_general_ci',
	`ID_FORMATO` INT(11) NOT NULL DEFAULT '0',
	`ID_FUENTE` INT(11) NOT NULL DEFAULT '0',
	`ID_AMBITO` INT(11) NOT NULL DEFAULT '0',
	`FECHA_ALTA` DATE NULL DEFAULT '0000-00-00',
	`FECHA_DESDE` DATE NULL DEFAULT '0000-00-00',
	`FECHA_HASTA` DATE NULL DEFAULT '0000-00-00',
	`CONSOLIDADO` TINYINT(4) NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `proporcion` (
	`ID` INT(11) NOT NULL,
	`ID_METAJUEGO` INT(11) NOT NULL,
	`TANTO_POR_MIL` INT(11) NOT NULL,
	`ID_ARQUETIPO` INT(11) NOT NULL,
	`NUMERO` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
