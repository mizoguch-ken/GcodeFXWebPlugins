/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.soem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
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
import ken.mizoguch.webviewer.plugin.gcodefx.SoemPluginListener;
import ken.mizoguch.webviewer.plugin.gcodefx.StageSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.VirtualMachineSettingsPlugin;
import ken.mizoguch.webviewer.plugin.gcodefx.WebEditorPlugin;
import netscape.javascript.JSException;

/**
 *
 * @author mizoguch_ken
 */
public class Soem implements GcodeFXWebViewerPlugin, SoemPluginListener {

    private GcodeFXWebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "soem";

    private WebEngine webEngine_;
    private Worker.State state_;

    private SoemPlugin soem_;

    private final Map<Long, String> funcNotify_;
    private final BlockingQueue<String> notify_;
    private String stringOnChangeSoemEcatThread_;
    private boolean isOnChangeSoemEcatThread_;

    /**
     *
     */
    public Soem() {
        webEngine_ = null;
        state_ = Worker.State.READY;
        soem_ = null;
        funcNotify_ = new HashMap<>();
        notify_ = new LinkedBlockingQueue<>();
        stringOnChangeSoemEcatThread_ = null;
        isOnChangeSoemEcatThread_ = false;
    }

    /**
     *
     * @param state
     */
    public void setState(Worker.State state) {
        state_ = state;
    }

    /**
     *
     * @return
     */
    public SoemPlugin getSoem() {
        return soem_;
    }

    /**
     *
     * @param libraryPath
     * @return
     */
    public Boolean load(String libraryPath) {
        return soem_.load(Paths.get(libraryPath));
    }

    /**
     *
     * @param size
     * @return
     */
    public Boolean init(int size) {
        Boolean ret = soem_.init(size);
        if (ret != null) {
            return ret;
        }
        return null;
    }

    /**
     *
     * @param slave
     * @param eep_man
     * @param eep_id
     * @param func
     * @return
     */
    public Boolean po2so(int slave, long eep_man, long eep_id, String func) {
        Callable<Integer> fnc = () -> {
            if (func != null) {
                if (state_ == Worker.State.SUCCEEDED) {
                    try {
                        return (Integer) webEngine_.executeScript(func + "(" + slave + ");");
                    } catch (JSException ex) {
                        webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                    }
                }
            }
            return 0;
        };
        return soem_.po2so(slave, eep_man, eep_id, fnc);
    }

    /**
     *
     * @param ifname
     * @param cycletime
     * @return
     */
    public Boolean start(String ifname, long cycletime) {
        return soem_.start(ifname, cycletime, this);
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setNotifyCheck(boolean state) {
        return soem_.setNotifyCheck(state);
    }

    /**
     *
     * @param slave
     * @param bitsOffset
     * @param bitsMask
     * @return
     */
    public Integer in(int slave, long bitsOffset, int bitsMask) {
        return soem_.in(slave, bitsOffset, bitsMask);
    }

    /**
     *
     * @param slave
     * @param bitsOffset
     * @param bitsMask
     * @param func
     * @return
     */
    public String notify(int slave, long bitsOffset, int bitsMask, String func) {
        if (func == null) {
            soem_.notify(slave, bitsOffset, bitsMask, false);
            return null;
        }
        Long ret = soem_.notify(slave, bitsOffset, bitsMask, true);
        if (ret != null) {
            return funcNotify_.put(ret, func);
        }
        return null;
    }

    /**
     *
     * @param slave
     * @param bitsOffset
     * @param bitsMask
     * @param value
     * @return
     */
    public Integer out(int slave, long bitsOffset, int bitsMask, int value) {
        return soem_.out(slave, bitsOffset, bitsMask, value);
    }

    /**
     *
     * @return
     */
    public String find_adapters() {
        return soem_.find_adapters();
    }

    /**
     *
     * @param ifname
     * @param printSDO
     * @param printMAP
     * @return
     */
    public String slaveinfo(String ifname, boolean printSDO, boolean printMAP) {
        return soem_.slaveinfo(ifname, printSDO, printMAP);
    }

    @Override
    public void onChangeSoemEcatThread(long address, int value) {
        if (funcNotify_.containsKey(address)) {
            notify_.offer(funcNotify_.get(address) + "('" + value + "');");
            if (!isOnChangeSoemEcatThread_) {
                isOnChangeSoemEcatThread_ = true;
                Platform.runLater(() -> {
                    while ((stringOnChangeSoemEcatThread_ = notify_.poll()) != null) {
                        if (state_ == Worker.State.SUCCEEDED) {
                            try {
                                webEngine_.executeScript(stringOnChangeSoemEcatThread_);
                            } catch (JSException | NullPointerException ex) {
                                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                            }
                        }
                    }
                    isOnChangeSoemEcatThread_ = false;
                });
            }
        }
    }

    @Override
    public void errorSafeOpErrorSoemEcatCheck(int slave) {
        webViewer_.write(FUNCTION_NAME, "ERROR : slave " + slave + " is in SAFE_OP + ERROR, attempting ack.", true);
    }

    @Override
    public void errorLostSoemEcatCheck(int slave) {
        webViewer_.write(FUNCTION_NAME, "ERROR : slave " + slave + " lost", true);
    }

    @Override
    public void warningSafeOpSoemEcatCheck(int slave) {
        webViewer_.write(FUNCTION_NAME, "WARNING : slave " + slave + " is in SAFE_OP, change to OPERATIONAL.", false);
    }

    @Override
    public void messageReconfiguredSoemEcatCheck(int slave) {
        webViewer_.write(FUNCTION_NAME, "MESSAGE : slave " + slave + " reconfigured", false);
    }

    @Override
    public void messageRecoveredSoemEcatCheck(int slave) {
        webViewer_.write(FUNCTION_NAME, "MESSAGE : slave " + slave + " recovered", false);
    }

    @Override
    public void messageFoundSoemEcatCheck(int slave) {
        webViewer_.write(FUNCTION_NAME, "MESSAGE : slave " + slave + " found", false);
    }

    @Override
    public void messageAllSlavesResumedOperationalSoemEcatCheck() {
        webViewer_.write(FUNCTION_NAME, "MESSAGE : all slaves resumed OPERATIONAL.", false);
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = (GcodeFXWebViewerPlugin) webViewer;
        webEngine_ = webViewer_.webEngine();
        soem_ = webViewer_.soem();
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
        soem_.close(this);
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
