BEGIN TRANSACTION;
INSERT INTO `CPUs` (id_cpu,name,frequency) VALUES (1,'i5',2000),
 (2,'i3',1500),
 (3,'i7',3000);
COMMIT;
