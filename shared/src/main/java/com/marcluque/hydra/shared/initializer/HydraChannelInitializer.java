package com.marcluque.hydra.shared.initializer;

import com.marcluque.hydra.shared.handler.impl.HydraSession;
import com.marcluque.hydra.shared.protocol.Protocol;
import com.marcluque.hydra.shared.protocol.packets.serialization.PacketDecoder;
import com.marcluque.hydra.shared.protocol.packets.serialization.PacketEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created with love by marcluque on 29.09.2017.
 */
public class HydraChannelInitializer<C extends Channel> extends ChannelInitializer<C> {

    private Protocol protocol;

    private boolean isServer;

    private boolean useUDP;

    public HydraChannelInitializer(Protocol protocol, boolean isServer, boolean useUDP) {
        this.protocol = protocol;
        this.isServer = isServer;
        this.useUDP = useUDP;
    }

    @Override
    protected void initChannel(C channel) {
        ChannelPipeline pipeline = channel.pipeline();

        // In
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        pipeline.addLast(new PacketDecoder(protocol));

        // Out
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new PacketEncoder(protocol));

        /*
        if (!useUDP) {
            TCPHydraSession session = new TCPHydraSession(channel, protocol);
            pipeline.addLast(session);
        } else {
            UDPHydraSession session = new UDPHydraSession(channel, protocol);
            pipeline.addLast(session);
        }
         */
        HydraSession session = new HydraSession(channel, protocol);
        pipeline.addLast(session);

        // Add sessions to protocol, to keep track of them
        if (isServer) {
            protocol.addSession(session);
        } else {
            protocol.setClientSession(session);
        }

        if (!useUDP) {
            if (protocol.getSessionListener() != null) {
                // Inform SessionListener about new session
                protocol.callSessionListener(true, session);
            } else if (protocol.getSessionConsumer() != null) {
                // Inform SessionConsumer about new session
                protocol.callSessionConsumer(true, session);
            }
        }
    }
}