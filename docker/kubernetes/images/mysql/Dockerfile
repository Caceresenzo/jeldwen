FROM mysql:5.7.32

RUN sed -i 's/#bind-address\t= 127.0.0.1/bind-address= 0.0.0.0/g' /etc/mysql/mysql.conf.d/mysqld.cnf