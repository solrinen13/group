
CREATE TABLE animal_report_data (
  id BIGINT NOT NULL generated by default as identity PRIMARY KEY,
  chat_id BIGINT,
  ration_of_animal VARCHAR(255),
  health_of_animal VARCHAR(255),
  habits_of_animal VARCHAR(255),
  days_of_ownership BIGINT,
  file_path VARCHAR(255),
  file_size BIGINT NOT NULL,
  data oid,
  caption VARCHAR(255),
  last_message TIMESTAMP,
  last_message_ms BIGINT
);
