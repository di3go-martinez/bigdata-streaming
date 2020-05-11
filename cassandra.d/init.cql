CREATE KEYSPACE sensors WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

use sensors;

CREATE TABLE data(
   id text primary key,
   label text,
   value varint,
   created_at timestamp
   );


