docker-compose -f docker-compose.yml up -d

docker container run --name mysqldb --network employee-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -d mysql:8



docker exec -t kafka kafka-console-consumer.sh --bootstrap-server :9092 --group jacek-japila-pl --topic t1

docker container run --name mysqldb --net bookendnet -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=bookend-book-club -d mysql:8


docker exec -it mysqldb bash
root@a434f87dbed9:/# mysql -uroot -p