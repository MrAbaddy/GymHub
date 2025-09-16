
-- TABELA USUARIO
CREATE TABLE usuario (
                         id UUID PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         senha VARCHAR(255) NOT NULL
);

-- TABELA ALUNO
CREATE TABLE aluno (
                       id UUID PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       idade INT,
                       sexo VARCHAR(10),
                       email VARCHAR(100),
                       telefone VARCHAR(20)
);

-- TABELA FICHA_FISICA (dependente de ALUNO)
CREATE TABLE ficha_fisica (
                              id UUID PRIMARY KEY,
                              aluno_id UUID NOT NULL,
                              data_avaliacao DATE NOT NULL DEFAULT CURRENT_DATE,
                              altura DECIMAL(4,2),
                              peso DECIMAL(5,2),
                              biceps DECIMAL(4,2),
                              antebraco DECIMAL(4,2),
                              peito DECIMAL(4,2),
                              cintura DECIMAL(4,2),
                              quadril DECIMAL(4,2),
                              perna DECIMAL(4,2),
                              panturrilha DECIMAL(4,2),
                              observacoes TEXT,
                              CONSTRAINT fk_ficha_aluno
                                  FOREIGN KEY (aluno_id)
                                      REFERENCES aluno(id)
                                      ON DELETE CASCADE
);

-- TABELA FICHA_EXERCICIOS (dependente de ALUNO e INSTRUTOR)
CREATE TABLE ficha_exercicios (
                                  id UUID PRIMARY KEY,
                                  aluno_id UUID NOT NULL,
                                  instrutor_id UUID NOT NULL,
                                  nome_exercicio VARCHAR(100) NOT NULL,
                                  num_series INT,
                                  num_repeticoes INT,
                                  CONSTRAINT fk_exercicio_aluno
                                      FOREIGN KEY (aluno_id)
                                          REFERENCES aluno(id)
                                          ON DELETE CASCADE,
                                  CONSTRAINT fk_exercicio_instrutor
                                      FOREIGN KEY (instrutor_id)
                                          REFERENCES instrutor(id)
                                          ON DELETE SET NULL
);

-- TABELA ADMINISTRATIVO (dependente de ALUNO)
CREATE TABLE administrativo (
                                id UUID PRIMARY KEY,
                                aluno_id UUID NOT NULL,
                                modo_pagamento VARCHAR(50),
                                dia_pagamento INT,
                                proximo_pagamento DATE,
                                situacao VARCHAR(20), -- "Regular" ou "Atrasado"
                                CONSTRAINT fk_admin_aluno
                                    FOREIGN KEY (aluno_id)
                                        REFERENCES aluno(id)
                                        ON DELETE CASCADE
);

-- TABELA INSTRUTOR
CREATE TABLE instrutor (
                           id UUID PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           email VARCHAR(100),
                           telefone VARCHAR(20)
);

-- RELAÇÃO ALUNO <-> INSTRUTOR (um instrutor pode ter vários alunos)
CREATE TABLE instrutor_aluno (
                                 instrutor_id UUID NOT NULL,
                                 aluno_id UUID NOT NULL,
                                 PRIMARY KEY (instrutor_id, aluno_id),
                                 CONSTRAINT fk_ia_instrutor
                                     FOREIGN KEY (instrutor_id)
                                         REFERENCES instrutor(id)
                                         ON DELETE CASCADE,
                                 CONSTRAINT fk_ia_aluno
                                     FOREIGN KEY (aluno_id)
                                         REFERENCES aluno(id)
                                         ON DELETE CASCADE
);