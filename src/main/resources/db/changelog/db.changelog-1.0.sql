DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'question') THEN
        CREATE TABLE question (
            id SERIAL PRIMARY KEY,
            question_title VARCHAR(255) NOT NULL,
            option1 VARCHAR(255) NOT NULL,
            option2 VARCHAR(255) NOT NULL,
            option3 VARCHAR(255) NOT NULL,
            option4 VARCHAR(255) NOT NULL,
            difficulty_level VARCHAR(255) NOT NULL,
            right_answer VARCHAR(255) NOT NULL,
            category VARCHAR(255) NOT NULL
        );
    END IF;
END
$$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'quiz') THEN
        CREATE TABLE quiz (
            id SERIAL PRIMARY KEY,
            title VARCHAR(255) NOT NULL
        );
    END IF;
END
$$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'quiz_questions') THEN
        CREATE TABLE quiz_questions (
            quiz_id INTEGER NOT NULL,
            question_id INTEGER NOT NULL,
            PRIMARY KEY (quiz_id, question_id),
            FOREIGN KEY (quiz_id) REFERENCES quiz(id),
            FOREIGN KEY (question_id) REFERENCES question(id)
        );
    END IF;
END
$$;
