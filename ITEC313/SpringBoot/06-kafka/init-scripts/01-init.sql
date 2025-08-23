-- PostgreSQL initialization script for Kafka Demo Application
-- This script will be executed when the PostgreSQL container starts for the first time

-- Create extensions if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create tables for the Kafka demo application
CREATE TABLE IF NOT EXISTS messages (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content TEXT NOT NULL,
    sender VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    message_type VARCHAR(50) NOT NULL,
    kafka_topic VARCHAR(255),
    kafka_partition INTEGER,
    kafka_offset BIGINT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS email_logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    recipient VARCHAR(255) NOT NULL,
    subject VARCHAR(500) NOT NULL,
    template_name VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    sent_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    error_message TEXT
);

CREATE TABLE IF NOT EXISTS scheduled_tasks (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    task_name VARCHAR(255) NOT NULL,
    execution_time TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL,
    duration_ms INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_messages_timestamp ON messages(timestamp);
CREATE INDEX IF NOT EXISTS idx_messages_sender ON messages(sender);
CREATE INDEX IF NOT EXISTS idx_messages_type ON messages(message_type);
CREATE INDEX IF NOT EXISTS idx_email_logs_recipient ON email_logs(recipient);
CREATE INDEX IF NOT EXISTS idx_email_logs_status ON email_logs(status);
CREATE INDEX IF NOT EXISTS idx_scheduled_tasks_execution_time ON scheduled_tasks(execution_time);
CREATE INDEX IF NOT EXISTS idx_scheduled_tasks_status ON scheduled_tasks(status);

-- Insert some sample data
INSERT INTO messages (content, sender, message_type, kafka_topic) VALUES
    ('Welcome to Kafka Demo Application!', 'system', 'INFO', 'messages-topic'),
    ('Database initialization completed', 'system', 'SUCCESS', 'messages-topic'),
    ('Sample message for testing', 'admin', 'INFO', 'messages-topic')
ON CONFLICT DO NOTHING;

-- Create a view for recent messages
CREATE OR REPLACE VIEW recent_messages AS
SELECT 
    id,
    content,
    sender,
    timestamp,
    message_type,
    kafka_topic
FROM messages 
ORDER BY timestamp DESC 
LIMIT 100;

-- Grant permissions to the application user
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO kafka_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO kafka_user;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO kafka_user;

-- Create a function to get message statistics
CREATE OR REPLACE FUNCTION get_message_stats()
RETURNS TABLE(
    total_messages BIGINT,
    messages_today BIGINT,
    messages_by_type JSON
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        COUNT(*)::BIGINT as total_messages,
        COUNT(CASE WHEN DATE(timestamp) = CURRENT_DATE THEN 1 END)::BIGINT as messages_today,
        json_object_agg(message_type, count) as messages_by_type
    FROM (
        SELECT message_type, COUNT(*) as count
        FROM messages 
        GROUP BY message_type
    ) t;
END;
$$ LANGUAGE plpgsql;

-- Grant execute permission on the function
GRANT EXECUTE ON FUNCTION get_message_stats() TO kafka_user;
