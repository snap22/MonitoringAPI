FROM mysql:9.1.0

ENV MYSQL_DATABASE=monitoring \
    MYSQL_USER=admin \
    MYSQL_PASSWORD=admin \
    MYSQL_ROOT_PASSWORD=admin

EXPOSE 3306