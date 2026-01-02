package application.persistence;

public record DatabaseEvent(DatabaseEventType type, Object data) {
}