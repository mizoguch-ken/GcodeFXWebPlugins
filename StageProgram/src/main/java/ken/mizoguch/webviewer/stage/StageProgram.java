/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.stage;

import java.nio.file.Path;
import java.nio.file.Paths;
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
public class StageProgram implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stageProgram";

    private StageSettingsPlugin stageSettings_;
    private VirtualMachineSettingsPlugin virtualMachineSettings_;

    /**
     *
     */
    public StageProgram() {
        stageSettings_ = null;
        virtualMachineSettings_ = null;
    }

    /**
     *
     * @return
     */
    public String getProgramNumber() {
        return virtualMachineSettings_.getProgramNumberValue();
    }

    /**
     *
     * @param number
     * @return
     */
    public Boolean setProgramNumber(String number) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setProgramNumberValue(number);
            return true;
        }
        return false;
    }

    /**
     *
     * @param number
     * @return
     */
    public Boolean getCheckOptionalBlockSkip(int number) {
        switch (number) {
            case 1:
                number = 0;
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getOptionalSkipCheck(number);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param number
     * @param state
     * @return
     */
    public Boolean setCheckOptionalBlockSkip(int number, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (number) {
                case 1:
                    number = 0;
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setOptionalSkipCheck(number, state);
                    return true;
                default:
                    webViewer_.write(FUNCTION_NAME, "There are no references", true);
                    break;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean isControlPanelView() {
        return stageSettings_.isViewControlPanel();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setControlPanelView(boolean state) {
        stageSettings_.setViewControlPanel(state);
        return true;
    }

    /**
     *
     * @return
     */
    public Boolean isConsoleView() {
        return stageSettings_.isViewConsole();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setConsoleView(boolean state) {
        stageSettings_.setViewConsole(state);
        return true;
    }

    /**
     *
     * @return
     */
    public String getConsole() {
        return stageSettings_.getConsole();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setConsole(String value) {
        stageSettings_.setConsole(value);
        return true;
    }

    /**
     *
     * @param path
     */
    public void addEditor(String path) {
        Path startFile = null;
        if (path != null) {
            if (!path.trim().isEmpty()) {
                startFile = Paths.get(path);
            }
        }
        stageSettings_.addEditor(startFile);
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = (GcodeFXWebViewerPlugin) webViewer;
        stageSettings_ = webViewer_.stageSettings();
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
