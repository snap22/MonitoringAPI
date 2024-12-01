FROM mysql:9.1.0

ENV MYSQL_ROOT_PASSWORD=admin

COPY setup.sql /docker-entrypoint-initdb.d/

EXPOSE 3306