ALTER TABLE usuarios ADD COLUMN nome VARCHAR(255);
UPDATE usuarios SET nome = 'Usuário Sem Nome' WHERE nome IS NULL;
ALTER TABLE usuarios ALTER COLUMN nome SET NOT NULL;