# bigdata-streaming


arrancar spark y kafka + cassandra
  ./dc up

deployar el spark job
  cd hub ; ./deploy

arrancar los productores
  cd producer ; ./start-sensors

arrancar el backend que consume un stream consultado cassandra
  cd backend ; ./mvnw spring-boot:run

arrancar el frontend de ejemplo
  cd web ; npm start



