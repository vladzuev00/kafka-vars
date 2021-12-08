package by.aurorasoft.kafka.variables;

public interface KafkaVars {

    String SCHEMA_PROP_NAME = "SCHEMA";

    String NOTIFICATION_BACKLOG_TOPIC_NAME = "notification-backlog";
    String NOTIFICATION_BACKLOG_GROUP_ID = "notification-backlog";

    String RECEIVED_COMMANDS_LOG_TOPIC_NAME = "received-commands-log";
    String RECEIVED_COMMANDS_LOG_GROUP_ID = "received-commands-log";

    String COMMANDS_TO_SEND_LOG_TOPIC_NAME = "commands-to-send-log";
    String COMMANDS_TO_SEND_LOG_GROUP_ID = "commands-to-send-log";

    String UNIT_ACTIONS_LOG_TOPIC_NAME = "unit-actions-log";
    String UNIT_ACTIONS_LOG_GROUP_ID = "unit-actions-log";

    String USER_ACTIONS_LOG_TOPIC_NAME = "user-actions-log";
    String USER_ACTIONS_LOG_GROUP_ID = "user-actions-log";

    String UNIT_RECONNECT_HOOK_TOPIC_NAME = "unit-reconnect-hook-log";
    String UNIT_RECONNECT_HOOK_GROUP_ID = "unit-reconnect-hook-log";

    String TELEGRAM_MESSAGES_TOPIC_NAME = "telegram-messages";
    String TELEGRAM_MESSAGES_GROUP_ID = "telegram-messages";

    String UNIT_LOG_TOPIC_NAME = "unit-log";
    String UNIT_LOG_GROUP_ID = "unit-log";
}
