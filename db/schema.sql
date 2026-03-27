-- AssessX Database Schema

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    github_id BIGINT UNIQUE NOT NULL,
    github_login VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('STUDENT', 'TEACHER')),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE user_groups (
    user_id BIGINT REFERENCES users(id),
    group_id BIGINT REFERENCES groups(id),
    PRIMARY KEY (user_id, group_id)
);

CREATE TABLE tests (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    questions JSONB NOT NULL,
    points INTEGER NOT NULL,
    time_limit_sec INTEGER NOT NULL,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE code_practices (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    unit_tests TEXT NOT NULL,
    points INTEGER NOT NULL,
    time_limit_sec INTEGER NOT NULL,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE results (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    test_id BIGINT REFERENCES tests(id),
    practice_id BIGINT REFERENCES code_practices(id),
    points INTEGER NOT NULL DEFAULT 0,
    max_points INTEGER NOT NULL,
    submitted_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT one_type CHECK (
        (test_id IS NOT NULL AND practice_id IS NULL) OR
        (test_id IS NULL AND practice_id IS NOT NULL)
    )
);

CREATE TABLE code_submissions (
    id BIGSERIAL PRIMARY KEY,
    result_id BIGINT REFERENCES results(id),
    code TEXT NOT NULL,
    test_output TEXT,
    passed_tests INTEGER DEFAULT 0,
    total_tests INTEGER DEFAULT 0,
    submitted_at TIMESTAMP DEFAULT NOW()
);
