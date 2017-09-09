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
public class StageOption implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stageOption";

    private VirtualMachineSettingsPlugin virtualMachineSettings_;

    /**
     *
     */
    public StageOption() {
        virtualMachineSettings_ = null;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckOptimization() {
        return virtualMachineSettings_.getOptionOptimization();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckOptimization(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionOptimization(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckExComment() {
        return virtualMachineSettings_.getOptionExComment();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckExComment(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionExComment(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheck3003_3004() {
        return virtualMachineSettings_.getOptionDisable30033004();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheck3003_3004(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionDisable30033004(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheck3006() {
        return virtualMachineSettings_.getOptionReplace3006M0();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheck3006(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionReplace3006M0(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckS() {
        return virtualMachineSettings_.getOptionOnlyS();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckS(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionOnlyS(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckRS274NGC() {
        return virtualMachineSettings_.getOptionRS274NGC();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckRS274NGC(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionRS274NGC(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getMaxFeedrate() {
        return virtualMachineSettings_.getOptionMaxFeedRate();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setMaxFeedrate(String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionMaxFeedRate(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getMaxRevolution() {
        return virtualMachineSettings_.getOptionMaxRevolution();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setMaxRevolution(String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionMaxRevolution(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getStartProgram() {
        return virtualMachineSettings_.getOptionStartProgram();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setStartProgram(String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionStartProgram(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getBlockProgram() {
        return virtualMachineSettings_.getOptionBlockProgram();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setBlockProgram(String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionBlockProgram(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getEndProgram() {
        return virtualMachineSettings_.getOptionEndProgram();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setEndProgram(String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setOptionEndProgram(value);
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
