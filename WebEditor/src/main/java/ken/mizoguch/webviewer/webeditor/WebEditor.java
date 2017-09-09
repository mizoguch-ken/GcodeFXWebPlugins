/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.webeditor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.BaseSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.GcodeFXWebViewerPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.GcodePlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.LaddersPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.SoemPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.StageSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.VirtualMachineSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.WebEditorPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.WebEditorPluginListener;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 *
 * @author mizoguch-ken
 */
public class WebEditor implements GcodeFXWebViewerPlugin, WebEditorPluginListener {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "webeditor";

    private WebEngine webEngine_;
    private WebEditorPlugin webEditor_;
    private Worker.State state_;

    private String funcFileNew_, funcFileOpen_, funcFileSave_;
    private Path filePath_;

    /**
     *
     */
    public WebEditor() {
        webEngine_ = null;
        webEditor_ = null;
        state_ = Worker.State.READY;
        funcFileNew_ = null;
        funcFileOpen_ = null;
        funcFileSave_ = null;
    }

    /**
     *
     * @param func
     */
    public void setNotifyFileNew(String func) {
        funcFileNew_ = func;
        if ((funcFileNew_ == null) && (funcFileOpen_ == null) && (funcFileSave_ == null)) {
            webEditor_.removeWebEditorListener(this);
        } else {
            webEditor_.addWebEditorListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyFileOpen(String func) {
        funcFileOpen_ = func;
        if ((funcFileNew_ == null) && (funcFileOpen_ == null) && (funcFileSave_ == null)) {
            webEditor_.removeWebEditorListener(this);
        } else {
            webEditor_.addWebEditorListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyFileSave(String func) {
        funcFileSave_ = func;
        if ((funcFileNew_ == null) && (funcFileOpen_ == null) && (funcFileSave_ == null)) {
            webEditor_.removeWebEditorListener(this);
        } else {
            webEditor_.addWebEditorListener(this);
        }
    }

    public JSObject getEditor() {
        return webEditor_.getEditor();
    }

    public void fileNew() {
        webEditor_.fileNew();
    }

    public Boolean fileOpen(String path) {
        if (webEditor_.fileOpen(path)) {
            return true;
        } else {
            webViewer_.write(FUNCTION_NAME, "File could not be opened", true);
        }
        return false;
    }

    public Boolean fileSave(String path) {
        if (webEditor_.fileSave(path)) {
            return true;
        } else {
            webViewer_.write(FUNCTION_NAME, "File could not be saved", true);
        }
        return false;
    }

    public String getFileName() {
        return webEditor_.getFileName();
    }

    @Override
    public void notifyFileNew() {
        if (funcFileNew_ != null) {
            Platform.runLater(() -> {
                if (funcFileNew_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcFileNew_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void notifyFileOpen(String file) {
        filePath_ = Paths.get(file);
        if (funcFileOpen_ != null) {
            Platform.runLater(() -> {
                if (funcFileOpen_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcFileOpen_ + "('" + filePath_.toRealPath().toString() + "');");
                        } catch (JSException | IOException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void notifyFileSave(String file) {
        filePath_ = Paths.get(file);
        if (funcFileSave_ != null) {
            Platform.runLater(() -> {
                if (funcFileSave_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcFileSave_ + "('" + filePath_.toRealPath().toString() + "');");
                        } catch (JSException | IOException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = (GcodeFXWebViewerPlugin) webViewer;
        webEngine_ = webViewer_.webEngine();
        webEditor_ = webViewer_.webEditor();
    }

    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public void state(Worker.State state) {
        state_ = state;
    }

    @Override
    public void close() {
        webEditor_.removeWebEditorListener(this);
    }

    @Override
    public StageSettingsPlugin stageSettings() {
        return webViewer_.stageSettings();
    }

    @Override
    public BaseSettingsPlugin baseSettings() {
        return webViewer_.baseSettings();
    }

    @Override
    public VirtualMachineSettingsPlugin virtualMachineSettings() {
        return webViewer_.virtualMachineSettings();
    }

    @Override
    public WebEditorPlugin webEditor() {
        return webViewer_.webEditor();
    }

    @Override
    public GcodePlugin gcode() {
        return webViewer_.gcode();
    }

    @Override
    public LaddersPlugin ladders() {
        return webViewer_.ladders();
    }

    @Override
    public SoemPlugin soem() {
        return webViewer_.soem();
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
