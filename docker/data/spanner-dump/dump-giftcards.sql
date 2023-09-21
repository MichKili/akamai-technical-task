CREATE TABLE flyway_schema_history (
  installed_rank INT64 NOT NULL,
  version STRING(50),
  description STRING(200) NOT NULL,
  type STRING(20) NOT NULL,
  script STRING(1000) NOT NULL,
  checksum INT64,
  installed_by STRING(100) NOT NULL,
  installed_on TIMESTAMP NOT NULL OPTIONS (
    allow_commit_timestamp = true
  ),
  execution_time INT64 NOT NULL,
  success BOOL NOT NULL,
) PRIMARY KEY(installed_rank DESC);
CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history(success);
CREATE TABLE gift_card (
  id STRING(36) NOT NULL,
  beneficiary_name STRING(255),
  beneficiary_email STRING(255) NOT NULL,
  sender_name STRING(255),
  date_of_delivery TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP()),
  message STRING(512),
  loyalty_account_id STRING(36) NOT NULL,
) PRIMARY KEY(id);
INSERT INTO `flyway_schema_history` (`type`, `installed_on`, `installed_rank`, `script`, `execution_time`, `version`, `checksum`, `success`, `description`, `installed_by`) VALUES ("SQL", TIMESTAMP "2023-09-21T10:06:26.721434Z", 1, "V2023.09.12.22.17.00__INITIAL.sql", 12, "2023.09.12.22.17.00", 1746974188, true, "INITIAL", "");
