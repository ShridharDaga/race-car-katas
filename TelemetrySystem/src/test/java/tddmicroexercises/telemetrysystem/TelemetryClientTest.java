package tddmicroexercises.telemetrysystem;

import org.junit.Test;
import org.junit.Assert;

import java.util.Random;

public class TelemetryClientTest {

    public static class RandomStub extends Random{
        @Override
        public int nextInt(int bound) {
            return 10;
        }
    }

    @Test
    public void Connect_successful(){
        TelemetryClient client = new TelemetryClient();
        client.connect("test");
        Assert.assertTrue(client.getOnlineStatus());
    }
    @Test
    public void Connect_unsuccessful(){
        TelemetryClient client = new TelemetryClient(new RandomStub());
        client.connect("test");
        Assert.assertFalse(client.getOnlineStatus());
    }
}
