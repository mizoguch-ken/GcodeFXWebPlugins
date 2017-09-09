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
public class StageToolChange implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stageToolChange";

    private VirtualMachineSettingsPlugin virtualMachineSettings_;

    /**
     *
     */
    public StageToolChange() {
        virtualMachineSettings_ = null;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckChangeProgram() {
        return virtualMachineSettings_.getToolChangeProgramCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckChangeProgram(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setToolChangeProgramCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckChangeXY() {
        return virtualMachineSettings_.getToolChangeXYCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckChangeXY(boolean state) {
        if (virtualMachineSettings_.isRunning() || !virtualMachineSettings_.getToolChangeProgramCheck()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setToolChangeXYCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getChangeProgram() {
        return virtualMachineSettings_.getToolChangeProgramValue();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setChangeProgram(String value) {
        if (virtualMachineSettings_.isRunning() || !virtualMachineSettings_.getToolChangeProgramCheck()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setToolChangeProgramValue(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckChangeMcode() {
        return virtualMachineSettings_.getToolChangeMCodeCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckChangeMcode(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setToolChangeMCodeCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getChangeMcode() {
        return virtualMachineSettings_.getToolChangeMCodeValue();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setChangeMcode(String value) {
        if (virtualMachineSettings_.isRunning() || !virtualMachineSettings_.getToolChangeMCodeCheck()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setToolChangeMCodeValue(value);
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
