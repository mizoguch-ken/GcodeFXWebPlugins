/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.ladders;

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
public class Ladders implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "ladders";

    private WebEngine webEngine_;
    private Worker.State state_;
    private LaddersPlugin ladders_;

    /**
     *
     */
    public Ladders() {
        webEngine_ = null;
        state_ = Worker.State.READY;
        ladders_ = null;
    }

    /**
     *
     * @return
     */
    public Integer getHistoryGeneration() {
        return ladders_.getHistoryGeneration();
    }

    /**
     *
     * @param historyGeneration
     */
    public void setHistoryGeneration(int historyGeneration) {
        ladders_.setHistoryGeneration(historyGeneration);
    }

    /**
     *
     * @return
     */
    public Long getIdealCycleTime() {
        return ladders_.getIdealCycleTime();
    }

    /**
     *
     * @param idealCycleTime
     */
    public void setIdealCycleTime(long idealCycleTime) {
        ladders_.setIdealCycleTime(idealCycleTime);
    }

    /**
     *
     */
    public void registerWeb() {
        ladders_.register(webEngine_, state_);
    }

    /**
     *
     */
    public void unregisterWeb() {
        ladders_.unregister(webEngine_);
    }

    /**
     *
     * @param address
     * @param slave
     * @param bitsOffset
     * @param bitsMask
     * @return
     */
    public Boolean registerSoemIn(String address, int slave, long bitsOffset, long bitsMask) {
        return ladders_.registerIn(webViewer_.soem(), address, slave, bitsOffset, bitsMask);
    }

    /**
     *
     * @param address
     * @param slave
     * @param bitsOffset
     * @param bitsMask
     * @return
     */
    public Boolean registerSoemOut(String address, int slave, long bitsOffset, long bitsMask) {
        return ladders_.registerOut(webViewer_.soem(), address, slave, bitsOffset, bitsMask);
    }

    /**
     *
     */
    public void unregisterSoem() {
        ladders_.unregister(webViewer_.soem());
    }

    /**
     *
     * @param address
     * @return
     */
    public Double getValue(String address) {
        return ladders_.getValue(address);
    }

    /**
     *
     * @param address
     * @param value
     */
    public void setValue(String address, double value) {
        ladders_.setValue(address, value);
    }

    /**
     *
     * @return
     */
    public Boolean connect() {
        return ladders_.connectLadder();
    }

    /**
     *
     * @return
     */
    public Boolean run() {
        return ladders_.runStartLadder();
    }

    /**
     *
     */
    public void stop() {
        ladders_.stopLadder();
    }

    /**
     *
     * @return
     */
    public Boolean fileNew() {
        return ladders_.fileNew();
    }

    /**
     *
     * @param path
     * @return
     */
    public Boolean fileOpen(String path) {
        return ladders_.fileOpen(Paths.get(path));
    }

    /**
     *
     * @return
     */
    public Boolean fileSave() {
        return ladders_.fileSave();
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return ladders_.getFileName();
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = (GcodeFXWebViewerPlugin) webViewer;
        webEngine_ = webViewer_.webEngine();
        ladders_ = webViewer_.ladders();
    }

    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public void state(Worker.State state) {
        state_ = state;
        if (ladders_ != null) {
            if (ladders_.isRegistered(webEngine_)) {
                ladders_.register(webEngine_, state_);
            }
        }
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
