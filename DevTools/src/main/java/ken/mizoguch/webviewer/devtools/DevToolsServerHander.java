/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.devtools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import java.util.Locale;
import javafx.application.Platform;

/**
 *
 * @author mizoguch-ken
 */
public class DevToolsServerHander extends ChannelInboundHandlerAdapter {

    private final DevToolsDebugger devToolsDebugger_;
    private ChannelHandlerContext channelHandlerContext_;
    private WebSocketServerHandshaker webSocketServerHandshaker_;
    private final Gson gson_;

    /**
     *
     * @param devToolsDebugger
     */
    public DevToolsServerHander(DevToolsDebugger devToolsDebugger) {
        devToolsDebugger_ = devToolsDebugger;
        channelHandlerContext_ = null;
        webSocketServerHandshaker_ = null;
        gson_ = new Gson();
    }

    /**
     *
     */
    public void close() {
        Platform.runLater(() -> {
            devToolsDebugger_.setEnabled(false);
            devToolsDebugger_.setMessageCallback(null);
        });
        if (channelHandlerContext_ != null) {
            channelHandlerContext_.close();
        }
        channelHandlerContext_ = null;
    }

    /**
     *
     * @param msg
     */
    public void sendMessage(String msg) {
        if (channelHandlerContext_ != null) {
            if (channelHandlerContext_.channel().isWritable()) {
                channelHandlerContext_.channel().writeAndFlush(new TextWebSocketFrame(msg));
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Platform.runLater(() -> {
            if (devToolsDebugger_.getMessageCallback() == null) {
                devToolsDebugger_.setMessageCallback((String param) -> {
                    if (channelHandlerContext_ != null) {
                        if (channelHandlerContext_.channel().isWritable()) {
                            JsonObject response = gson_.fromJson(param, JsonObject.class);
                            if (!response.has("error")) {
                                channelHandlerContext_.channel().writeAndFlush(new TextWebSocketFrame(param), channelHandlerContext_.voidPromise());
                            } else {
                                JsonObject error = response.get("error").getAsJsonObject();
                                if (response.has("id")) {
                                    channelHandlerContext_.channel().writeAndFlush(new TextWebSocketFrame("{\"result\":{},\"id\":" + response.get("id").getAsString() + "}"), channelHandlerContext_.voidPromise());
                                } else {
                                    devToolsDebugger_.writeLog(param, true);
                                }
                            }
                        }
                    }
                    return null;
                });
                devToolsDebugger_.setEnabled(true);
            }
        });
        channelHandlerContext_ = ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
        } else if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            HttpHeaders httpHeaders = httpRequest.headers();
            boolean isUpgrade = false;
            if (httpHeaders.contains("Connection")) {
                if (httpHeaders.get("Connection").toLowerCase(Locale.getDefault()).equals("upgrade")) {
                    isUpgrade = true;
                }
            } else if (httpHeaders.contains("Upgrade")) {
                if (httpHeaders.get("Upgrade").toLowerCase(Locale.getDefault()).equals("websocket")) {
                    isUpgrade = true;
                }
            }
            if (isUpgrade) {
                WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory("ws://" + httpHeaders.get("Host"), null, true);
                webSocketServerHandshaker_ = webSocketServerHandshakerFactory.newHandshaker(httpRequest);
                if (webSocketServerHandshaker_ == null) {
                    WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext_.channel());
                } else {
                    webSocketServerHandshaker_.handshake(channelHandlerContext_.channel(), httpRequest);
                }
            }
        } else if (msg instanceof WebSocketFrame) {
            if (msg instanceof BinaryWebSocketFrame) {
            } else if (msg instanceof TextWebSocketFrame) {
                Platform.runLater(() -> {
                    devToolsDebugger_.sendMessage(((TextWebSocketFrame) msg).text());
                });
            } else if (msg instanceof PingWebSocketFrame) {
            } else if (msg instanceof PongWebSocketFrame) {
            } else if (msg instanceof CloseWebSocketFrame) {
                webSocketServerHandshaker_.close(channelHandlerContext_.channel(), (CloseWebSocketFrame) msg).sync();
                webSocketServerHandshaker_ = null;
            } else {
                devToolsDebugger_.writeLog("Unsupported WebSocketFrame", true);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (channelHandlerContext_ != null) {
            channelHandlerContext_.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        close();
    }

}
