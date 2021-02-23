-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.5.8-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando datos para la tabla met.ambito: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `ambito` DISABLE KEYS */;
INSERT INTO `ambito` (`ID`, `DESCRIPCION`) VALUES
	(1, 'MUNDIAL'),
	(2, 'Online @ Portugal');
/*!40000 ALTER TABLE `ambito` ENABLE KEYS */;

-- Volcando datos para la tabla met.arquetipo: ~30 rows (aproximadamente)
/*!40000 ALTER TABLE `arquetipo` DISABLE KEYS */;
INSERT INTO `arquetipo` (`ID`, `NOMBRE`, `ID_FORMATO`) VALUES
	(1, 'Tron', 1),
	(2, 'Boros Monarch', 1),
	(3, 'UR', 1),
	(4, 'Skred Faeries', 1),
	(5, 'URG', 1),
	(6, 'Burn', 1),
	(7, 'Orzhov Pestilence', 1),
	(8, 'Affinity', 1),
	(9, 'Mono-Green Stompy', 1),
	(10, 'Blue-Black Delver', 1),
	(11, 'Elves', 1),
	(12, 'Bogles', 1),
	(13, 'Walls Combo', 1),
	(14, 'BRG', 1),
	(15, 'W', 1),
	(16, 'WUBR', 1),
	(17, 'BR', 1),
	(18, 'Blue-White Flicker', 1),
	(19, 'R', 1),
	(20, 'Mardu Monarch', 1),
	(21, 'WUG', 1),
	(22, 'Delver Faeries', 1),
	(23, 'Infect', 1),
	(24, 'URG', 1),
	(25, 'B', 1),
	(26, 'White Winnie', 1),
	(27, 'B', 1),
	(28, 'URG', 1),
	(29, 'Insde Out Combo', 1),
	(30, 'Other', 1);
/*!40000 ALTER TABLE `arquetipo` ENABLE KEYS */;

-- Volcando datos para la tabla met.arquetipo_metajuego: ~30 rows (aproximadamente)
/*!40000 ALTER TABLE `arquetipo_metajuego` DISABLE KEYS */;
INSERT INTO `arquetipo_metajuego` (`ID`, `ID_ARQUETIPO`, `ID_METAJUEGO`) VALUES
	(1, 1, 1),
	(2, 2, 1),
	(3, 3, 1),
	(4, 4, 1),
	(5, 5, 1),
	(6, 6, 1),
	(7, 7, 1),
	(8, 8, 1),
	(9, 9, 1),
	(10, 10, 1),
	(11, 11, 1),
	(12, 12, 1),
	(13, 13, 1),
	(14, 14, 1),
	(15, 15, 1),
	(16, 16, 1),
	(17, 17, 1),
	(18, 18, 1),
	(19, 19, 1),
	(20, 20, 1),
	(21, 21, 1),
	(22, 22, 1),
	(23, 23, 1),
	(24, 24, 1),
	(25, 25, 1),
	(26, 26, 1),
	(27, 27, 1),
	(28, 28, 1),
	(29, 29, 1),
	(30, 30, 1);
/*!40000 ALTER TABLE `arquetipo_metajuego` ENABLE KEYS */;

-- Volcando datos para la tabla met.formato: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `formato` DISABLE KEYS */;
INSERT INTO `formato` (`ID`, `NOMBRE`) VALUES
	(1, 'PAUPER');
/*!40000 ALTER TABLE `formato` ENABLE KEYS */;

-- Volcando datos para la tabla met.fuente: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `fuente` DISABLE KEYS */;
INSERT INTO `fuente` (`ID`, `NOMBRE`, `FECHA_ALTA`) VALUES
	(1, 'WEBSCRATCHING', '2021-01-24'),
	(2, 'UPDATE', '2021-01-25');
/*!40000 ALTER TABLE `fuente` ENABLE KEYS */;

-- Volcando datos para la tabla met.metajuego: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `metajuego` DISABLE KEYS */;
INSERT INTO `metajuego` (`ID`, `NOMBRE`, `ID_FORMATO`, `ID_FUENTE`, `ID_AMBITO`, `FECHA_ALTA`, `FECHA_DESDE`, `FECHA_HASTA`, `CONSOLIDADO`) VALUES
	(1, 'MtgGolfish', 1, 1, 1, '2021-02-23', '2021-02-01', '2021-03-01', 0);
/*!40000 ALTER TABLE `metajuego` ENABLE KEYS */;

-- Volcando datos para la tabla met.proporcion: ~30 rows (aproximadamente)
/*!40000 ALTER TABLE `proporcion` DISABLE KEYS */;
INSERT INTO `proporcion` (`ID`, `ID_METAJUEGO`, `TANTO_POR_MIL`, `ID_ARQUETIPO`, `NUMERO`) VALUES
	(1, 1, 85, 1, 43),
	(2, 1, 81, 2, 41),
	(3, 1, 77, 3, 39),
	(4, 1, 73, 4, 37),
	(5, 1, 63, 5, 32),
	(6, 1, 63, 6, 32),
	(7, 1, 49, 7, 25),
	(8, 1, 45, 8, 23),
	(9, 1, 43, 9, 22),
	(10, 1, 43, 10, 22),
	(11, 1, 41, 11, 21),
	(12, 1, 34, 12, 17),
	(13, 1, 28, 13, 14),
	(14, 1, 24, 14, 12),
	(15, 1, 24, 15, 12),
	(16, 1, 22, 16, 11),
	(17, 1, 22, 17, 11),
	(18, 1, 20, 18, 10),
	(19, 1, 18, 19, 9),
	(20, 1, 18, 20, 9),
	(21, 1, 12, 21, 6),
	(22, 1, 10, 22, 5),
	(23, 1, 8, 23, 4),
	(24, 1, 8, 24, 4),
	(25, 1, 6, 25, 3),
	(26, 1, 6, 26, 3),
	(27, 1, 4, 27, 2),
	(28, 1, 4, 28, 2),
	(29, 1, 2, 29, 1),
	(30, 1, 71, 30, 36);
/*!40000 ALTER TABLE `proporcion` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
