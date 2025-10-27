CREATE TABLE usuario (
                         id SERIAL NOT NULL PRIMARY KEY,
                         uuid UUID DEFAULT gen_random_uuid(),
                         nome VARCHAR(100) NOT NULL,
                         senha VARCHAR(255) NOT NULL
);
CREATE TABLE aluno (
                       id SERIAL NOT NULL PRIMARY KEY,
                       uuid UUID DEFAULT gen_random_uuid(),
                       nome VARCHAR(100) NOT NULL,
                       idade INT,
                       sexo VARCHAR(10),
                       email VARCHAR(100),
                       telefone VARCHAR(20)
);
CREATE TABLE instrutor (
                           id SERIAL NOT NULL PRIMARY KEY,
                           uuid UUID DEFAULT gen_random_uuid(),
                           nome VARCHAR(100) NOT NULL,
                           email VARCHAR(100),
                           telefone VARCHAR(20)
);
CREATE TABLE ficha_fisica (
                              id SERIAL NOT NULL PRIMARY KEY,
                              uuid UUID DEFAULT gen_random_uuid(),
                              aluno_id INT NOT NULL,
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
CREATE TABLE ficha_exercicios (
                                  id SERIAL NOT NULL PRIMARY KEY,
                                  uuid UUID DEFAULT gen_random_uuid(),
                                  aluno_id INT NOT NULL,
                                  instrutor_id INT,
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
CREATE TABLE administrativo (
                                id SERIAL NOT NULL PRIMARY KEY,
                                uuid UUID DEFAULT gen_random_uuid(),
                                aluno_id INT NOT NULL,
                                modo_pagamento VARCHAR(50),
                                dia_pagamento INT,
                                proximo_pagamento DATE,
                                situacao VARCHAR(20),
                                CONSTRAINT fk_admin_aluno
                                    FOREIGN KEY (aluno_id)
                                        REFERENCES aluno(id)
                                        ON DELETE CASCADE
);
CREATE TABLE instrutor_aluno (
                                 instrutor_id INT NOT NULL,
                                 aluno_id INT NOT NULL,
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