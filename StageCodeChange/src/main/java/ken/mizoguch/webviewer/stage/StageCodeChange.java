/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.stage;

import java.nio.file.Path;
import java.util.List;
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

/**
 *
 * @author mizoguch-ken
 */
public class StageCodeChange implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stageCodeChange";

    private VirtualMachineSettingsPlugin virtualMachineSettings_;

    /**
     *
     */
    public StageCodeChange() {
        virtualMachineSettings_ = null;
    }

    /**
     *
     * @return
     */
    public String getProgramCall() {
        return virtualMachineSettings_.getCodeChangeProgramCallValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setProgramCall(String code) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCodeChangeProgramCallValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getGcode() {
        return virtualMachineSettings_.getCodeChangeGValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setGcode(String code) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCodeChangeGValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getMcode() {
        return virtualMachineSettings_.getCodeChangeMValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setMcode(String code) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCodeChangeMValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getTcode() {
        return virtualMachineSettings_.getCodeChangeTValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setTcode(String code) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCodeChangeTValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getDcode() {
        return virtualMachineSettings_.getCodeChangeDValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setDcode(String code) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCodeChangeDValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getHcode() {
        return virtualMachineSettings_.getCodeChangeHValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setHcode(String code) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCodeChangeHValue(code);
            return true;
        }
        return false;
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = (GcodeFXWebViewerPlugin) webViewer;
        virtualMachineSettings_ = webViewer_.virtualMachineSettings();
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
