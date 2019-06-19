# Que me pongo API

###Usuarios Mac

Instalar [homebrew](https://brew.sh/) y [pgAdmin](https://www.pgadmin.org/):

1. ```brew uninstall --force postgresql```
(en caso de tener postgresql instalado)

2. ```rm -rf /usr/local/var/postgres```

3. ```brew install postgres```

### Usuarios Windows

1. Descargar [PostgreSQL](https://www.postgresql.org/)

### Configuracion
1. Abrir pgAdmin y crear base de datos con los siguientes datos: 
    - Nombre: ``que_me_pongo_db``
    - En el tag security agregar privillegios al usuario `postgres` y chequear el que dice `all`
    - En el tag Definition sleccionar como Tablespace `pg_default`

 




