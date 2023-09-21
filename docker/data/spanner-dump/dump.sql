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
CREATE TABLE identities (
  id STRING(36) NOT NULL,
  merchant_id STRING(36) NOT NULL,
  external_id STRING(36) NOT NULL,
  identity_type STRING(10) NOT NULL,
  identity_status STRING(8) NOT NULL,
  created_at TIMESTAMP NOT NULL,
) PRIMARY KEY(id);
CREATE TABLE accounts (
  id STRING(36) NOT NULL,
  merchant_id STRING(36) NOT NULL,
  account_type STRING(10) NOT NULL,
  account_status STRING(8) NOT NULL,
  currency STRING(3) NOT NULL,
  balance FLOAT64 DEFAULT (0.0),
  available_amount FLOAT64 DEFAULT (0.0),
  activated_amount FLOAT64 DEFAULT (0.0),
  blocked_amount FLOAT64 DEFAULT (0.0),
  created_at TIMESTAMP NOT NULL,
  linked_account_id STRING(36),
  is_linked BOOL NOT NULL DEFAULT (FALSE),
  FOREIGN KEY(linked_account_id) REFERENCES accounts(id),
) PRIMARY KEY(id);
CREATE TABLE transactions (
  id STRING(36) NOT NULL,
  account_id STRING(36) NOT NULL,
  merchant_id STRING(36) NOT NULL,
  company_id STRING(36),
  store_id STRING(36),
  terminal_id STRING(36),
  transaction_type STRING(16) NOT NULL,
  amount NUMERIC DEFAULT (0.0),
  actual_balance NUMERIC DEFAULT (0.0),
  reference_code STRING(36),
  reference_text STRING(160),
  linked_transaction_id STRING(36),
  created_at TIMESTAMP NOT NULL,
  transaction_status STRING(9) NOT NULL,
  comment STRING(160),
  reference_time TIMESTAMP,
  identity_id STRING(36),
) PRIMARY KEY(id);
INSERT INTO `flyway_schema_history` (`type`, `installed_on`, `description`, `installed_by`, `version`, `checksum`, `success`, `installed_rank`, `script`, `execution_time`) VALUES ("SQL", TIMESTAMP "2023-09-21T11:57:12.514808Z", "add individual account id column to account contribution", "", "2023.08.11.14.30.00", 364768227, true, 6, "V2023.08.11.14.30.00__add_individual_account_id_column_to_account_contribution.sql", 6), ("SQL", TIMESTAMP "2023-09-21T11:57:12.24365Z", "add comment column to account log", "", "2023.08.10.11.00.00", 1313697202, true, 4, "V2023.08.10.11.00.00__add_comment_column_to_account_log.sql", 6), ("SQL", TIMESTAMP "2023-09-21T11:57:11.99471Z", "remove companyId from account", "", "2023.07.31.09.20.00", -86019808, true, 2, "V2023.07.31.09.20.00__remove_companyId_from_account.sql", 10), ("SQL", TIMESTAMP "2023-09-21T11:57:12.680467Z", "add is linked to account", "", "2023.08.14.12.00.00", 1714763650, true, 7, "V2023.08.14.12.00.00__add_is_linked_to_account.sql", 29), ("SQL", TIMESTAMP "2023-09-21T11:57:12.369236Z", "add identityId to transactions", "", "2023.08.10.11.55.00", -1573741430, true, 5, "V2023.08.10.11.55.00__add_identityId_to_transactions.sql", 5), ("SQL", TIMESTAMP "2023-09-21T11:57:12.116787Z", "add reference time column to transactions", "", "2023.08.04.11.00.00", 319668455, true, 3, "V2023.08.04.11.00.00__add_reference_time_column_to_transactions.sql", 6), ("SQL", TIMESTAMP "2023-09-21T11:57:11.856495Z", "INITIAL", "", "2023.06.09.11.25.00", 1204515716, true, 1, "V2023.06.09.11.25.00__INITIAL.sql", 59);
CREATE TABLE account_owners (
  id STRING(36) NOT NULL,
  account_id STRING(36) NOT NULL,
  identity_id STRING(36) NOT NULL,
  access_type STRING(10) NOT NULL,
  account_contribution_id STRING(36),
  FOREIGN KEY(account_id) REFERENCES accounts(id),
  FOREIGN KEY(identity_id) REFERENCES identities(id),
) PRIMARY KEY(id);
CREATE TABLE account_logs (
  id STRING(36) NOT NULL,
  account_id STRING(36) NOT NULL,
  log_type STRING(10) NOT NULL,
  reference_code STRING(36),
  reference_text STRING(160),
  created_at TIMESTAMP NOT NULL,
  comment STRING(160),
) PRIMARY KEY(id);
CREATE TABLE account_contribution (
  id STRING(36) NOT NULL,
  account_id STRING(36) NOT NULL,
  identity_id STRING(36) NOT NULL,
  contribution NUMERIC DEFAULT (0.0),
  joined_at TIMESTAMP NOT NULL,
  leaved_at TIMESTAMP,
  individual_account_id STRING(36),
  FOREIGN KEY(account_id) REFERENCES accounts(id),
  FOREIGN KEY(identity_id) REFERENCES identities(id),
) PRIMARY KEY(id);
