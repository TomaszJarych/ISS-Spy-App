CREATE SCHEMA IF NOT EXISTS iss_spy_app;

USE iss_spy_app;

CREATE  TABLE locations(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  request_time BIGINT,
  latitude DECIMAL(8,5),
  longitude DECIMAL (8,5));