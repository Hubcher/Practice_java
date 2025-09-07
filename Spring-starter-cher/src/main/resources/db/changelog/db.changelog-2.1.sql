--liquibase formatted sql

--changeset andrey:1
ALTER TABLE users
    add COLUMN info varchar(128);
