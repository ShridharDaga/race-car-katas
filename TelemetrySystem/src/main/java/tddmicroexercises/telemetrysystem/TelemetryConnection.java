package tddmicroexercises.telemetrysystem;

public interface TelemetryConnection {
    void connect(String telemetryServerConnectionString);
    void disconnect();
    boolean getOnlineStatus();

}
