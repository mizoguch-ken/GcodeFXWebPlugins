/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.stage;

import java.io.IOException;
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
public class StageBase implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stageBase";

    private BaseSettingsPlugin baseSettings_;
    private VirtualMachineSettingsPlugin virtualMachineSettings_;

    /**
     *
     */
    public StageBase() {
        baseSettings_ = null;
        virtualMachineSettings_ = null;
    }

    /**
     *
     * @return
     */
    public String getVirtualMachineName() {
        return virtualMachineSettings_.getVirtualMachineNameValue();
    }

    /**
     *
     * @param path
     * @return
     */
    public Boolean loadVirtualMachine(String path) {
        if (virtualMachineSettings_.isRunning() && !baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.actionLoadVirtualMachine(Paths.get(path));
            return true;
        }
        return false;
    }

    /**
     *
     * @param path
     * @return
     */
    public Boolean saveVirtualMachine(String path) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.actionSaveVirtualMachine(Paths.get(path));
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckLog() {
        return virtualMachineSettings_.isLogCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckLog(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setLogCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialPort() {
        return baseSettings_.getSerialPortValue();
    }

    /**
     *
     * @param port
     * @return
     */
    public Boolean setSerialPort(String port) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialPortValue(port);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean serialOpen() {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.actionSerialOpen();
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean serialClose() {
        if (!baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.actionSerialClose();
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckSerialCharacter() {
        return baseSettings_.isSerialCharacterCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckSerialCharacter(boolean state) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialCharacterCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckSerialObserveCTS() {
        return baseSettings_.isSerialObserveCTSCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckSerialObserveCTS(boolean state) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialObserveCTSCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckSerialObserveDSR() {
        return baseSettings_.isSerialObserveDSRCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckSerialObserveDSR(boolean state) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialObserveDSRCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckSerialObserveDC2DC4() {
        return baseSettings_.isSerialObserveDC2DC4Check();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckSerialObserveDC2DC4(boolean state) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialObserveDC2DC4Check(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialBaudrate() {
        return baseSettings_.getSerialBaudrateValue();
    }

    /**
     *
     * @param baudrate
     * @return
     */
    public Boolean setSerialBaudrate(String baudrate) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialBaudrateValue(baudrate);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialDatabits() {
        return baseSettings_.getSerialDataBitsValue();
    }

    /**
     *
     * @param databits
     * @return
     */
    public Boolean setSerialDatabits(String databits) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialDataBitsValue(databits);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialStopbits() {
        return baseSettings_.getSerialStopBitsValue();
    }

    /**
     *
     * @param stopbits
     * @return
     */
    public Boolean setSerialStopbits(String stopbits) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialStopBitsValue(stopbits);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialParity() {
        return baseSettings_.getSerialParityValue();
    }

    /**
     *
     * @param parity
     * @return
     */
    public Boolean setSerialParity(String parity) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialParityValue(parity);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialBufferLimit() {
        return baseSettings_.getSerialBufferLimitValue();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setSerialBufferLimit(String value) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialBufferLimitValue(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialDelay() {
        return baseSettings_.getSerialDelayValue();
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setSerialDelay(String value) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialDelayValue(value);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialStartCode() {
        return baseSettings_.getSerialStartCodeValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setSerialStartCode(String code) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialStartCodeValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getSerialEndCode() {
        return baseSettings_.getSerialEndCodeValue();
    }

    /**
     *
     * @param code
     * @return
     */
    public Boolean setSerialEndCode(String code) {
        if (baseSettings_.isSerial()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            baseSettings_.setSerialEndCodeValue(code);
            return true;
        }
        return false;
    }

    /**
     *
     * @param number
     * @return
     */
    public String getBackGroundFile(int number) {
        switch (number) {
            case 1:
            case 2:
            case 3:
                return virtualMachineSettings_.getBackGroundFileValue(number - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param number
     * @param path
     * @return
     * @throws java.io.IOException
     */
    public Boolean setBackGroundFile(int number, String path) throws IOException {
        switch (number) {
            case 1:
            case 2:
            case 3:
                if (virtualMachineSettings_.isRunning()) {
                    webViewer_.write(FUNCTION_NAME, "Disabled state", true);
                } else {
                    virtualMachineSettings_.setBackGroundFileValue(number - 1, Paths.get(path).toRealPath().toString());
                    return true;
                }
                break;
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getExternalSubProgramDirectory() {
        return virtualMachineSettings_.getExternalSubProgramDirectoryValue();
    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    public Boolean setExternalSubProgramDirectory(String path) throws IOException {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setExternalSubProgramDirectoryValue(Paths.get(path).toRealPath().toString());
            return true;
        }
        return false;
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = (GcodeFXWebViewerPlugin) webViewer;
        baseSettings_ = webViewer_.baseSettings();
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
