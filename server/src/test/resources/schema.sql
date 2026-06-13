CREATE TABLE IF NOT EXISTS runtime_prop
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    key         TEXT NOT NULL,
    description TEXT NOT NULL DEFAULT '',
    value       TEXT NOT NULL DEFAULT '',
    created_at  TEXT NOT NULL,
    updated_at  TEXT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_runtime_prop_key ON runtime_prop (key);
CREATE INDEX IF NOT EXISTS idx_runtime_prop_created_at ON runtime_prop (created_at);
CREATE INDEX IF NOT EXISTS idx_runtime_prop_updated_at ON runtime_prop (updated_at);