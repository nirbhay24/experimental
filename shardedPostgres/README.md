# Sharded DB java project

This is simple project where we shared data based on Username and have all data of user in that shard based on user.

Started two instances of postgres.

*ddl-auto will only create table for first db so we need to create table manually in second shard*

    docker pull postgres
    docker run -d --name pgsql-dev1 -e POSTGRES_PASSWORD=Welcome4$ -p 5432:5432 postgres
    docker run -d --name pgsql-dev2 -e POSTGRES_PASSWORD=Welcome4$ -p 5433:5432 postgres


