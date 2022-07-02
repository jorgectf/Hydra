package com.marcluque.hydra.server;

import com.marcluque.hydra.shared.ArrayPacket;
import com.marcluque.hydra.shared.TestPacket;
import com.marcluque.hydra.shared.handler.Session;
import com.marcluque.hydra.shared.protocol.packets.StandardPacket;
import com.marcluque.hydra.shared.protocol.packets.listener.HydraPacketListener;
import com.marcluque.hydra.shared.protocol.packets.listener.PacketHandler;
import org.junit.jupiter.api.Assertions;

/**
 * Created by marcluque on 28.03.2019.
 */
public class TestServerPacketListener implements HydraPacketListener {

    private String[] testArray = new String[1000];

    public TestServerPacketListener() {
        // Create test array
        for (int i = 0; i < 1000; i++) {
            testArray[i] = String.format("test%d", i);
        }
    }

    @PacketHandler
    public void onTestPacket(TestPacket examplePacket, Session session) {
        Assertions.assertTrue(session.isConnected());
        switch (examplePacket.getNumber()) {
            case 0:
                Assertions.assertEquals("Test", examplePacket.getObject().toString());
                break;
        }

        session.send(new StandardPacket("#Received!"));
        session.send(new TestPacket(examplePacket.getNumber(), "test"));
    }

    @PacketHandler
    public void onArrayPacket(ArrayPacket arrayPacket, Session session) {
        Assertions.assertArrayEquals(testArray, arrayPacket.getStrings());
    }

    @PacketHandler
    public void onStandardPacket(StandardPacket standardPacket, Session session) {
        Assertions.assertTrue(session.isConnected());
        session.send(new StandardPacket("#Received!"));
    }
}