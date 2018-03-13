/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.devtools;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author mizoguch-ken
 */
public class DevToolsServer extends Service<Void> {

    private final EventLoopGroup bossGroup_;
    private final EventLoopGroup workerGroup_;
    private final DevToolsDebugger devToolsDebugger_;
    private DevToolsServerHander devToolsServerHander_;
    private final int port_;
    private final Gson gson_;

    /**
     *
     * @param devTools
     * @param port
     */
    public DevToolsServer(DevTools devTools, int port) {
        bossGroup_ = new NioEventLoopGroup();
        workerGroup_ = new NioEventLoopGroup();
        devToolsDebugger_ = new DevToolsDebugger(devTools);
        devToolsServerHander_ = null;
        port_ = port;
        gson_ = new Gson();
    }

    /**
     *
     */
    public void stop() {
        if (isRunning()) {
            cancel();
        }
        if (devToolsServerHander_ != null) {
            devToolsServerHander_.close();
        }
        devToolsServerHander_ = null;
        workerGroup_.shutdownGracefully();
        bossGroup_.shutdownGracefully();
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return port_;
    }

    /**
     *
     * @param type
     * @param value
     */
    public void consoleAPICalled(String type, String value) {
        if (devToolsServerHander_ != null) {
            if (isRunning()) {
                JsonObject runtime = new JsonObject();
                JsonObject consoleAPICalled = new JsonObject();
                JsonArray args = new JsonArray();
                JsonObject remoteObject = new JsonObject();

                runtime.addProperty("method", "Runtime.consoleAPICalled");
                runtime.add("params", consoleAPICalled);
                consoleAPICalled.addProperty("type", type);
                consoleAPICalled.add("args", args);
                consoleAPICalled.addProperty("executionContextId", -1);
                consoleAPICalled.addProperty("timestamp", System.currentTimeMillis());
                args.add(remoteObject);
                remoteObject.addProperty("type", "string");
                remoteObject.addProperty("value", value);

                devToolsServerHander_.sendMessage(gson_.toJson(runtime));
            }
        }
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException {
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup_, workerGroup_)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(final SocketChannel ch) throws Exception {
                                    devToolsServerHander_ = new DevToolsServerHander(devToolsDebugger_);
                                    ch.pipeline().addLast(
                                            new HttpServerCodec(),
                                            devToolsServerHander_);
                                }
                            });
                    ChannelFuture f = b.bind(port_).sync();
                    f.channel().closeFuture().sync();
                } finally {
                    stop();
                }
                return null;
            }
        };
    }
}
