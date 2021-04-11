-- phpMyAdmin SQL Dump
-- version 4.0.4.2
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 11-Abr-2021 às 02:37
-- Versão do servidor: 5.6.13
-- versão do PHP: 5.4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `attom`
--
CREATE DATABASE IF NOT EXISTS `attom` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `attom`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_autor`
--

CREATE TABLE IF NOT EXISTS `tb_autor` (
  `cod_autor` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`cod_autor`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `tb_autor`
--

INSERT INTO `tb_autor` (`cod_autor`, `nome`) VALUES
(1, 'Eiichiro Oda');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_colecao`
--

CREATE TABLE IF NOT EXISTS `tb_colecao` (
  `cod_colecao` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `isCompleto` tinyint(1) DEFAULT NULL,
  `ultimo_lido` int(11) DEFAULT NULL,
  `ultimo_comprado` int(11) DEFAULT NULL,
  `cod_usuario` int(11) NOT NULL,
  `cod_selo` int(11) DEFAULT NULL,
  `cod_editora` int(11) NOT NULL,
  PRIMARY KEY (`cod_colecao`),
  KEY `fk_tb_colecao_tb_usuario_idx` (`cod_usuario`),
  KEY `fk_tb_colecao_tb_selo1_idx` (`cod_selo`),
  KEY `fk_tb_colecao_tb_editora1_idx` (`cod_editora`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Extraindo dados da tabela `tb_colecao`
--

INSERT INTO `tb_colecao` (`cod_colecao`, `titulo`, `isCompleto`, `ultimo_lido`, `ultimo_comprado`, `cod_usuario`, `cod_selo`, `cod_editora`) VALUES
(8, 'One piece', 0, 97, 97, 1, NULL, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_colecao_autor`
--

CREATE TABLE IF NOT EXISTS `tb_colecao_autor` (
  `cod_colecao` int(11) NOT NULL,
  `cod_autor` int(11) NOT NULL,
  PRIMARY KEY (`cod_colecao`,`cod_autor`),
  KEY `fk_tb_colecao_has_tb_autor_tb_autor1_idx` (`cod_autor`),
  KEY `fk_tb_colecao_has_tb_autor_tb_colecao1_idx` (`cod_colecao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_colecao_autor`
--

INSERT INTO `tb_colecao_autor` (`cod_colecao`, `cod_autor`) VALUES
(8, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_colecao_genero`
--

CREATE TABLE IF NOT EXISTS `tb_colecao_genero` (
  `cod_colecao` int(11) NOT NULL,
  `cod_genero` int(11) NOT NULL,
  PRIMARY KEY (`cod_colecao`,`cod_genero`),
  KEY `fk_tb_colecao_has_tb_genero_tb_genero1_idx` (`cod_genero`),
  KEY `fk_tb_colecao_has_tb_genero_tb_colecao1_idx` (`cod_colecao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_colecao_genero`
--

INSERT INTO `tb_colecao_genero` (`cod_colecao`, `cod_genero`) VALUES
(8, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_editora`
--

CREATE TABLE IF NOT EXISTS `tb_editora` (
  `cod_editora` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`cod_editora`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `tb_editora`
--

INSERT INTO `tb_editora` (`cod_editora`, `nome`) VALUES
(1, 'Panini');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_genero`
--

CREATE TABLE IF NOT EXISTS `tb_genero` (
  `cod_genero` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`cod_genero`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `tb_genero`
--

INSERT INTO `tb_genero` (`cod_genero`, `descricao`) VALUES
(1, 'Aventura');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_selo`
--

CREATE TABLE IF NOT EXISTS `tb_selo` (
  `cod_selo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cod_editora` int(11) NOT NULL,
  `cod_genero` int(11) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`cod_selo`),
  KEY `fk_tb_selo_tb_editora1_idx` (`cod_editora`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_usuario`
--

CREATE TABLE IF NOT EXISTS `tb_usuario` (
  `cod_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`cod_usuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Extraindo dados da tabela `tb_usuario`
--

INSERT INTO `tb_usuario` (`cod_usuario`, `login`, `senha`, `nome`, `email`) VALUES
(1, 'teste', 'teste', 'Vou ser Deletado', 'teste@email.com');

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `tb_colecao`
--
ALTER TABLE `tb_colecao`
  ADD CONSTRAINT `fk_tb_colecao_tb_editora1` FOREIGN KEY (`cod_editora`) REFERENCES `tb_editora` (`cod_editora`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tb_colecao_tb_selo1` FOREIGN KEY (`cod_selo`) REFERENCES `tb_selo` (`cod_selo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tb_colecao_tb_usuario` FOREIGN KEY (`cod_usuario`) REFERENCES `tb_usuario` (`cod_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `tb_colecao_autor`
--
ALTER TABLE `tb_colecao_autor`
  ADD CONSTRAINT `fk_tb_colecao_has_tb_autor_tb_colecao1` FOREIGN KEY (`cod_colecao`) REFERENCES `tb_colecao` (`cod_colecao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tb_colecao_has_tb_autor_tb_autor1` FOREIGN KEY (`cod_autor`) REFERENCES `tb_autor` (`cod_autor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `tb_colecao_genero`
--
ALTER TABLE `tb_colecao_genero`
  ADD CONSTRAINT `fk_tb_colecao_has_tb_genero_tb_colecao1` FOREIGN KEY (`cod_colecao`) REFERENCES `tb_colecao` (`cod_colecao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tb_colecao_has_tb_genero_tb_genero1` FOREIGN KEY (`cod_genero`) REFERENCES `tb_genero` (`cod_genero`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `tb_selo`
--
ALTER TABLE `tb_selo`
  ADD CONSTRAINT `fk_tb_selo_tb_editora1` FOREIGN KEY (`cod_editora`) REFERENCES `tb_editora` (`cod_editora`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
