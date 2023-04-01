package tddmicroexercises.telemetrysystem;

import java.util.Random;

public class TelemetryConnectionImplement implements TelemetryConnection{
    private final Random connectionEventsSimulator;
    boolean onlineStatus;

    public TelemetryConnectionImplement(Random random) {
        this.connectionEventsSimulator = random;
    }

    @Override
    public void connect(String telemetryServerConnectionString) {
        if (telemetryServerConnectionString == null || "".equals(telemetryServerConnectionString)) {
            throw new IllegalArgumentException();
        }

        // simulate the operation on a real modem
        boolean success = connectionEventsSimulator.nextInt(10) <= 8;
        onlineStatus = success;
    }

    @Override
    public void disconnect() {
        onlineStatus = false;
    }

    @Override
    public boolean getOnlineStatus() {
        return onlineStatus;
    }
}
