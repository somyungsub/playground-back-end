-- schema.sql 파일 내용 예시
-- CREATE TABLE IF NOT EXISTS sample_r2dbc (
--                                       id BIGSERIAL PRIMARY KEY,
--                                       name VARCHAR(255) NOT NULL,
--     code VARCHAR(255)
--     );
--
-- CREATE TABLE IF NOT EXISTS sample_input_r2dbc
-- (
--                                             id BIGSERIAL PRIMARY KEY,
--                                             input_name VARCHAR(255) NOT NULL,
--     input_value VARCHAR(255),
--     sample_id BIGINT,
--     CONSTRAINT fk_sample_id
--     FOREIGN KEY (sample_id)
--     REFERENCES sample_r2dbc (id)
--     ON DELETE CASCADE
-- );


CREATE SCHEMA IF NOT EXISTS sample;


CREATE TABLE sample.sample_r2dbc
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(255)
);

CREATE TABLE sample.sample_input_r2dbc
(
    id          BIGSERIAL PRIMARY KEY,
    INPUT_NAME  VARCHAR(255),
    INPUT_VALUE VARCHAR(255),
    SAMPLE_ID   BIGINT,
    FOREIGN KEY (SAMPLE_ID) REFERENCES sample.sample_r2dbc (id)
);