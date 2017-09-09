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
public class StageCoordinate implements GcodeFXWebViewerPlugin {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stageCoordinate";

    private VirtualMachineSettingsPlugin virtualMachineSettings_;

    /**
     *
     */
    public StageCoordinate() {
        virtualMachineSettings_ = null;
    }

    /**
     *
     * @param axis
     * @return
     */
    public Boolean getCheckMachineOrigin(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getOriginMachineCheck(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckMachineOrigin(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setOriginMachineCheck(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getMachineOrigin(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getOriginMachineValue(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setMachineOrigin(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setOriginMachineValue(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckExternal(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateExtCheck(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckExternal(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateExtCheck(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getExternal(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateExtValue(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setExternal(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateExtValue(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckG92(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG92Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG92(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG92Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG92(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG92Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG92(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG92Value(axis - 1, value);
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
    public Boolean getCheckTool() {
        return virtualMachineSettings_.getCoordinateToolCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckTool(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCoordinateToolCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean getCheckMirror() {
        return virtualMachineSettings_.getCoordinateMirrorCheck();
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setCheckMirror(boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            virtualMachineSettings_.setCoordinateMirrorCheck(state);
            return true;
        }
        return false;
    }

    /**
     *
     * @param axis
     * @return
     */
    public Boolean getCheckG54(int axis) {

        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG54Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG54(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG54Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG54(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG54Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG54(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG54Value(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckG55(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG55Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG55(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG55Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG55(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG55Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG55(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG55Value(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckG56(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG56Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG56(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG56Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG56(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG56Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG56(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG56Value(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckG57(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG57Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG57(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG57Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG57(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG57Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG57(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG57Value(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckG58(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG58Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG58(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG58Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG58(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG58Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG58(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG58Value(axis - 1, value);
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
     * @param axis
     * @return
     */
    public Boolean getCheckG59(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG59Check(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param state
     * @return
     */
    public Boolean setCheckG59(int axis, boolean state) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG59Check(axis - 1, state);
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
     * @param axis
     * @return
     */
    public String getG59(int axis) {
        switch (axis) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return virtualMachineSettings_.getCoordinateG59Value(axis - 1);
            default:
                webViewer_.write(FUNCTION_NAME, "There are no references", true);
                break;
        }
        return null;
    }

    /**
     *
     * @param axis
     * @param value
     * @return
     */
    public Boolean setG59(int axis, String value) {
        if (virtualMachineSettings_.isRunning()) {
            webViewer_.write(FUNCTION_NAME, "Disabled state", true);
        } else {
            switch (axis) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    virtualMachineSettings_.setCoordinateG59Value(axis - 1, value);
                    return true;
                default:
                    webViewer_.write(FUNCTION_NAME, "There are no references", true);
                    break;
            }
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
