-- Criação e Seleção do Banco de Dados
CREATE DATABASE IF NOT EXISTS AcessibilidadeApp;
USE AcessibilidadeApp;

-- Tabela Pessoa (Base para Usuário e Proprietário)
CREATE TABLE Pessoa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    email VARCHAR(35) UNIQUE NOT NULL,
    telefone VARCHAR(11),
    usuario VARCHAR(20) UNIQUE NOT NULL,
    senha INT NOT NULL
);

-- Tabela Proprietario (Especialização de Pessoa)
CREATE TABLE Proprietario (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Pessoa(id) ON DELETE CASCADE
);

-- Tabela Usuario (Especialização de Pessoa)
CREATE TABLE Usuario (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Pessoa(id) ON DELETE CASCADE
);

-- Tabela TagAcessibilidade
CREATE TABLE TagAcessibilidade (
    tagId BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) UNIQUE NOT NULL
);

-- Tabela Local
CREATE TABLE Local (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    proprietarioId BIGINT NOT NULL,
    nome VARCHAR(30) NOT NULL,
    descricao TEXT,
    endereco VARCHAR(255) NOT NULL,
    latitude DOUBLE,
    longitude DOUBLE,
    FOREIGN KEY (proprietarioId) REFERENCES Proprietario(id)
);

-- Tabela Acessibilidade (1:1 com Local)
CREATE TABLE Acessibilidade (
    localId BIGINT PRIMARY KEY,
    Acessivel BOOLEAN NOT NULL,
    FOREIGN KEY (localId) REFERENCES Local(id) ON DELETE CASCADE
);

-- Tabela Comentario
CREATE TABLE Comentario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuarioId BIGINT NOT NULL,
    localId BIGINT NOT NULL,
    descricao TEXT NOT NULL,
    FOREIGN KEY (usuarioId) REFERENCES Usuario(id),
    FOREIGN KEY (localId) REFERENCES Local(id)
);

-- Tabela LocalTag (Relacionamento N:M)
CREATE TABLE LocalTag (
    localId BIGINT NOT NULL,
    tagId BIGINT NOT NULL,
    confiancaScore DOUBLE NOT NULL,
    PRIMARY KEY (localId, tagId),
    FOREIGN KEY (localId) REFERENCES Local(id) ON DELETE CASCADE,
    FOREIGN KEY (tagId) REFERENCES TagAcessibilidade(tagId) ON DELETE CASCADE
);