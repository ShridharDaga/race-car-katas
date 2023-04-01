package tddmicroexercises.telemetrysystem;

import org.junit.Test;
import org.junit.Assert;

public class TelemetryDiagnosticControlsTest {

    static class TelemetryClientDummy extends TelemetryClient {
        int sendCount = 0;
        int receiveCount = 0;

        @Override
        public void send(String message) {
            super.send(message);
            sendCount++;
        }

        @Override
        public String receive() {
            receiveCount++;
            return super.receive();
        }
    }

    static class TelemetryClientStub extends TelemetryClient {
        int count;

        @Override
        public boolean getOnlineStatus() {
            return false;
        }

        @Override
        public void connect(String telemetryServerConnectionString) {
            super.connect(telemetryServerConnectionString);
            count++;
        }
    }

    @Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response() throws Exception {
        TelemetryClientDummy telemetryClientMock = new TelemetryClientDummy();
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls(telemetryClientMock);
        telemetryDiagnosticControls.checkTransmission();

        Assert.assertEquals(1, telemetryClientMock.sendCount);
        Assert.assertEquals(1, telemetryClientMock.receiveCount);
    }

    @Test
    public void Transmission_should_send_a_diagnostic_info() throws Exception {
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls();
        telemetryDiagnosticControls.checkTransmission();

        String expectedResponse =
                "LAST TX rate................ 100 MBPS\r\n"
                        + "HIGHEST TX rate............. 100 MBPS\r\n"
                        + "LAST RX rate................ 100 MBPS\r\n"
                        + "HIGHEST RX rate............. 100 MBPS\r\n"
                        + "BIT RATE.................... 100000000\r\n"
                        + "WORD LEN.................... 16\r\n"
                        + "WORD/FRAME.................. 511\r\n"
                        + "BITS/FRAME.................. 8192\r\n"
                        + "MODULATION TYPE............. PCM/FM\r\n"
                        + "TX Digital Los.............. 0.75\r\n"
                        + "RX Digital Los.............. 0.10\r\n"
                        + "BEP Test.................... -5\r\n"
                        + "Local Rtrn Count............ 00\r\n"
                        + "Remote Rtrn Count........... 00";

        Assert.assertEquals(expectedResponse, telemetryDiagnosticControls.getDiagnosticInfo());
    }

    @Test
    public void Transmission_should_fail(){
        TelemetryClientStub telemetryClientStub = new TelemetryClientStub();
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls(telemetryClientStub);

        try {
            telemetryDiagnosticControls.checkTransmission();
            Assert.fail();
        } catch (Exception exe) {
            Assert.assertEquals(3, telemetryClientStub.count);
        }
    }
}
