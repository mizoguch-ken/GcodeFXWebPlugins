/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.gcode;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.BaseSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.GcodeFXWebViewerPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.GcodeInterpreterPluginListener;
import ken.mizoguch.webviewer.plugin.gcodefx.GcodePlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.GcodeVirtualMachinePluginListener;
import ken.mizoguch.webviewer.plugin.gcodefx.LaddersPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.SoemPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.StageSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.VirtualMachineSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.WebEditorPlugin;
import netscape.javascript.JSException;

/**
 *
 * @author mizoguch-ken
 */
public class Gcode implements GcodeFXWebViewerPlugin, GcodeVirtualMachinePluginListener, GcodeInterpreterPluginListener {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "gcode";

    private WebEngine webEngine_;
    private Worker.State state_;
    private GcodePlugin gcode_;

    private String funcStart_, funcAbnormalEnd_, funcErrorEnd_, funcNormalEnd_, funcVariables_, funcExComment_;
    private final BlockingQueue<String> variables_, excomment_;
    private final Gson gson_ = new Gson();

    /**
     *
     */
    public Gcode() {
        webEngine_ = null;
        state_ = Worker.State.READY;
        gcode_ = null;
        funcStart_ = null;
        funcAbnormalEnd_ = null;
        funcErrorEnd_ = null;
        funcNormalEnd_ = null;
        funcVariables_ = null;
        funcExComment_ = null;
        variables_ = new LinkedBlockingQueue<>();
        excomment_ = new LinkedBlockingQueue<>();
    }

    /**
     *
     * @param func
     */
    public void setNotifyStart(String func) {
        funcStart_ = func;
        if ((funcStart_ == null) && (funcAbnormalEnd_ == null) && (funcErrorEnd_ == null) && (funcNormalEnd_ == null)) {
            gcode_.removeGcodeVirtualMachineListener(this);
        } else {
            gcode_.addGcodeVirtualMachineListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyAbnormalEnd(String func) {
        funcAbnormalEnd_ = func;
        if ((funcStart_ == null) && (funcAbnormalEnd_ == null) && (funcErrorEnd_ == null) && (funcNormalEnd_ == null)) {
            gcode_.removeGcodeVirtualMachineListener(this);
        } else {
            gcode_.addGcodeVirtualMachineListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyErrorEnd(String func) {
        funcErrorEnd_ = func;
        if ((funcStart_ == null) && (funcAbnormalEnd_ == null) && (funcErrorEnd_ == null) && (funcNormalEnd_ == null)) {
            gcode_.removeGcodeVirtualMachineListener(this);
        } else {
            gcode_.addGcodeVirtualMachineListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyNormalEnd(String func) {
        funcNormalEnd_ = func;
        if ((funcStart_ == null) && (funcAbnormalEnd_ == null) && (funcErrorEnd_ == null) && (funcNormalEnd_ == null)) {
            gcode_.removeGcodeVirtualMachineListener(this);
        } else {
            gcode_.addGcodeVirtualMachineListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyVariables(String func) {
        funcVariables_ = func;
        if ((funcVariables_ == null) && (funcExComment_ == null)) {
            gcode_.removeGcodeInterpreterListener(this);
        } else {
            gcode_.addGcodeInterpreterListener(this);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyExComment(String func) {
        funcExComment_ = func;
        if ((funcVariables_ == null) && (funcExComment_ == null)) {
            gcode_.removeGcodeInterpreterListener(this);
        } else {
            gcode_.addGcodeInterpreterListener(this);
        }
    }

    /**
     *
     * @param codes
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public Boolean script(String codes) throws UnsupportedEncodingException, IOException {
        return gcode_.script(codes);
    }

    /**
     *
     * @param address
     * @return
     */
    public Double getVariable(int address) {
        return gcode_.getVariable(address);
    }

    /**
     *
     * @param address
     * @param value
     */
    public void setVariable(int address, Double value) {
        gcode_.setVariable(address, value);
    }

    /**
     *
     */
    @Override
    public void startGcodeVirtualMachine() {
        if (funcStart_ != null) {
            Platform.runLater(() -> {
                if (funcStart_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcStart_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     *
     */
    @Override
    public void abnormalEndGcodeVirtualMachine() {
        if (funcAbnormalEnd_ != null) {
            Platform.runLater(() -> {
                if (funcAbnormalEnd_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcAbnormalEnd_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     *
     */
    @Override
    public void errorEndGcodeVirtualMachine() {
        if (funcErrorEnd_ != null) {
            Platform.runLater(() -> {
                if (funcErrorEnd_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcErrorEnd_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     *
     */
    @Override
    public void normalEndGcodeVirtualMachine() {
        if (funcNormalEnd_ != null) {
            Platform.runLater(() -> {
                if (funcNormalEnd_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcNormalEnd_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void variablesGcodeInterpreter(Map<Integer, Map<String, String>> variables) {
        if (funcVariables_ != null) {
            variables_.offer("'" + gson_.toJson(variables) + "'");
            Platform.runLater(() -> {
                if ((funcVariables_ != null) && !variables_.isEmpty()) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcVariables_ + "(" + variables_.poll() + ");");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void exCommentGcodeInterpreter(String excomment) {
        if (funcExComment_ != null) {
            excomment_.offer("'" + excomment + "'");
            Platform.runLater(() -> {
                if ((funcExComment_ != null) && !excomment_.isEmpty()) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcExComment_ + "(" + excomment_.poll() + ");");
                        } catch (JSException ex) {
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
        gcode_ = webViewer_.gcode();
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
        if (gcode_ != null) {
            gcode_.removeGcodeVirtualMachineListener(this);
            gcode_.removeGcodeInterpreterListener(this);
        }
        variables_.clear();
        excomment_.clear();
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
