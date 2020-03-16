-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 08, 2020 at 12:44 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sc_sac`
--

-- --------------------------------------------------------

--
-- Table structure for table `artigo`
--

CREATE TABLE `artigo` (
  `artigo_id` int(11) NOT NULL,
  `artigo_resumo` varchar(2000) DEFAULT NULL,
  `artigo_arquivo` varchar(400) NOT NULL,
  `artigo_confirma_submissao` int(11) NOT NULL DEFAULT 0,
  `artigo_qtd_revisores` int(11) NOT NULL DEFAULT 0,
  `artigo_media` float NOT NULL DEFAULT 0,
  `artigo_titulo` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `artigo`
--

INSERT INTO `artigo` (`artigo_id`, `artigo_resumo`, `artigo_arquivo`, `artigo_confirma_submissao`, `artigo_qtd_revisores`, `artigo_media`, `artigo_titulo`) VALUES
(4, ' É claro que a revolu??o dos costumes promove a alavancagem do remanejamento dos quadros funcionais. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a complexidade dos estudos efetuados n?o pode mais se dissociar das diretrizes de desenvolvimento para o futuro. O empenho em analisar o julgamento imparcial das eventualidades assume importantes posi?ões no estabelecimento do sistema de participa??o geral.', 'A evolu??o dos dinossauros no período cretácio', 1, 0, 0, 'A evolu??o dos dinossauros no período cretácio'),
(6, ' É claro que a revolu??o dos costumes promove a alavancagem do remanejamento dos quadros funcionais. Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a complexidade dos estudos efetuados n?o pode mais se dissociar das diretrizes de desenvolvimento para o futuro. O empenho em analisar o julgamento imparcial das eventualidades assume importantes posi?ões no estabelecimento do sistema de participa??o geral.', 'A evolu??o dos dinossauros no período cretácio', 1, 0, 0, 'A evolu??o dos dinossauros no período cretácio');

-- --------------------------------------------------------

--
-- Table structure for table `artigo_autor`
--

CREATE TABLE `artigo_autor` (
  `artigo_autor_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `artigo_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cartao`
--

CREATE TABLE `cartao` (
  `cartao_id` int(11) NOT NULL,
  `cartao_numero` varchar(16) NOT NULL,
  `cartao_data_vencimento` varchar(10) NOT NULL,
  `cartao_marca` varchar(50) NOT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(7),
(7),
(7);

-- --------------------------------------------------------

--
-- Table structure for table `revisao`
--

CREATE TABLE `revisao` (
  `revisao_id` int(11) NOT NULL,
  `revisao_nota` float NOT NULL,
  `revisao_data_envio` date NOT NULL,
  `revisao_comentario` varchar(500) NOT NULL,
  `artigo_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL,
  `usuario_nome` varchar(250) NOT NULL,
  `usuario_endereco` varchar(250) NOT NULL,
  `usuario_telefone` varchar(250) NOT NULL,
  `usuario_email` varchar(250) NOT NULL,
  `usuario_local_trabalho` varchar(250) NOT NULL,
  `usuario_is_revisor` int(11) DEFAULT 0,
  `usuario_is_autor` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`usuario_id`, `usuario_nome`, `usuario_endereco`, `usuario_telefone`, `usuario_email`, `usuario_local_trabalho`, `usuario_is_revisor`, `usuario_is_autor`) VALUES
(1, 'Talita Ramos', 'Rua Maria Cecília, 181-E, Rio Sena- 40715400- Salvador, Bahia', '71988170858', 'talita@gmail.com', 'Horizon CI', 0, 1),
(2, 'Talita Ramos', 'Rua Maria Cecília, 181-E, Rio Sena- 40715400- Salvador, Bahia', '71988170858', 'talita@gmail.com', 'Horizon CI', 0, 1),
(3, 'Talita Ramos', 'Rua Maria Cecília, 181-E, Rio Sena- 40715400- Salvador, Bahia', '71988170858', 'talita@gmail.com', 'Horizon CI', 0, 1),
(5, 'Talita Ramos', 'Rua Maria Cecília, 181-E, Rio Sena- 40715400- Salvador, Bahia', '71988170858', 'talita@gmail.com', 'Horizon CI', 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `artigo`
--
ALTER TABLE `artigo`
  ADD PRIMARY KEY (`artigo_id`);

--
-- Indexes for table `artigo_autor`
--
ALTER TABLE `artigo_autor`
  ADD PRIMARY KEY (`artigo_autor_id`),
  ADD KEY `artigo_id_fk` (`artigo_id`),
  ADD KEY `usuario_id_fk` (`usuario_id`);

--
-- Indexes for table `cartao`
--
ALTER TABLE `cartao`
  ADD PRIMARY KEY (`cartao_id`),
  ADD KEY `usu_id_fk` (`usuario_id`);

--
-- Indexes for table `revisao`
--
ALTER TABLE `revisao`
  ADD PRIMARY KEY (`revisao_id`),
  ADD UNIQUE KEY `UK_gwfhebwk6u1ctvhl4797xc9l4` (`artigo_id`),
  ADD KEY `art_id_fk` (`artigo_id`),
  ADD KEY `usu_if_fk` (`usuario_id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuario_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `artigo`
--
ALTER TABLE `artigo`
  MODIFY `artigo_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `artigo_autor`
--
ALTER TABLE `artigo_autor`
  MODIFY `artigo_autor_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cartao`
--
ALTER TABLE `cartao`
  MODIFY `cartao_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `revisao`
--
ALTER TABLE `revisao`
  MODIFY `revisao_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuario_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `artigo_autor`
--
ALTER TABLE `artigo_autor`
  ADD CONSTRAINT `artigo_id_fk` FOREIGN KEY (`artigo_id`) REFERENCES `artigo` (`artigo_id`),
  ADD CONSTRAINT `usuario_id_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`);

--
-- Constraints for table `cartao`
--
ALTER TABLE `cartao`
  ADD CONSTRAINT `usu_id_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`);

--
-- Constraints for table `revisao`
--
ALTER TABLE `revisao`
  ADD CONSTRAINT `art_id_fk` FOREIGN KEY (`artigo_id`) REFERENCES `artigo` (`artigo_id`),
  ADD CONSTRAINT `usu_if_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`usuario_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
