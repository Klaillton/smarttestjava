CREATE TABLE pythagorean (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numa BIGINT(100) NOT NULL,
	numb BIGINT(100) NOT NULL,
	numc BIGINT(100) NOT NULL,
	resultado TINYINT(1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pythagorean (numa, numb, numc, resultado) VALUES ('2', '3', '5', 1);
