CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,

    action VARCHAR(50) NOT NULL,        -- CREATE / UPDATE / DELETE
    entity_id INT NOT NULL,             -- ID of record

    old_value TEXT,                     -- before change
    new_value TEXT,                     -- after change

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
