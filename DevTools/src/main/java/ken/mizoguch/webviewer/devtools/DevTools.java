/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.devtools;

import java.nio.file.Path;
import java.util.List;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;

/**
 *
 * @author mizoguch-ken
 */
public class DevTools implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "devtools";

    private boolean isConsoleOut_;
    private DevToolsServer devToolsServer_;

    /**
     *
     */
    public DevTools() {
        isConsoleOut_ = false;
        devToolsServer_ = null;
    }

    /**
     *
     */
    public void licenses() {
        new Licenses().show();
    }

    /**
     *
     * @param state
     */
    public void setConsoleOut(boolean state) {
        isConsoleOut_ = state;
    }

    /**
     *
     * @param port
     * @return
     * @throws java.lang.InterruptedException
     */
    public String start(int port) throws InterruptedException {
        if (devToolsServer_ == null) {
            devToolsServer_ = new DevToolsServer(this, port);
            if (devToolsServer_.getState() == Worker.State.READY) {
                devToolsServer_.start();
            } else {
                devToolsServer_.stop();
                devToolsServer_.restart();
            }
        }
        return "chrome-devtools://devtools/bundled/inspector.html?ws=localhost:" + devToolsServer_.getPort() + "/";
    }

    /**
     *
     */
    public void stop() {
        if (devToolsServer_ != null) {
            devToolsServer_.stop();
        }
        devToolsServer_ = null;
    }

    /**
     *
     * @return
     */
    public Boolean isRunning() {
        if (devToolsServer_ != null) {
            return devToolsServer_.isRunning();
        }
        return null;
    }

    /**
     *
     * @param value
     */
    public void log(String value) {
        if (isConsoleOut_) {
            writeLog(value, false);
        }
        if (devToolsServer_ != null) {
            devToolsServer_.consoleAPICalled("log", value);
        }
    }

    /**
     *
     * @param value
     */
    public void error(String value) {
        if (isConsoleOut_) {
            writeLog(value, true);
        }
        if (devToolsServer_ != null) {
            devToolsServer_.consoleAPICalled("error", value);
        }
    }

    /**
     *
     * @param value
     */
    public void warn(String value) {
        if (isConsoleOut_) {
            writeLog(value, false);
        }
        if (devToolsServer_ != null) {
            devToolsServer_.consoleAPICalled("warning", value);
        }
    }

    public void writeLog(String msg, boolean err) {
        write(FUNCTION_NAME, msg, err);
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
    }

    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public void state(Worker.State state) {
    }

    @Override
    public void close() {
        stop();
    }

    @Override
    public Stage stage() {
        return webViewer_.stage();
    }

    @Override
    public List<Image> icons() {
        return webViewer_.icons();
    }

    @Override
    public WebEngine webEngine() {
        return webViewer_.webEngine();
    }

    @Override
    public Path webPath() {
        return webViewer_.webPath();
    }

    @Override
    public void writeStackTrace(String name, Throwable throwable) {
        webViewer_.writeStackTrace(name, throwable);
    }

    @Override
    public void write(String name, String msg, boolean err) {
        webViewer_.write(name, msg, err);
    }
}
