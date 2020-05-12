# bigdata-streaming

# Dependencias

- docker 19.03+
- docker-compose 1.24 +
- java 8+
- node 10+

# Pasos

(Cada uno de los comandos se ejecutan desde la raíz de este proyecto)


arrancar spark y kafka + cassandra
```
  ./dc up
```

deployar el spark job
```
  cd hub ; ./deploy
```

arrancar los productores
```
  cd producer ; ./start-sensors
```

arrancar el backend que consume un stream consultado cassandra
```
 cd backend ; ./mvnw spring-boot:run
```

arrancar el frontend de ejemplo
```
cd web ; npx npm-install-if-needed; npm start
```


