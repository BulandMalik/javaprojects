INSERT INTO parententity (id, parentProperty) VALUES (1, 'p1');
INSERT INTO parententity (id, parentProperty) VALUES (2, 'p2');

INSERT INTO childentity (id, childProperty1, childProperty2, parent) VALUES (1, 'cp1.1', 'cp1.2', p1');
INSERT INTO childentity (id, childProperty1, childProperty2, parent) VALUES (2, 'cp2.1', 'cp2', p2.2');