/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.devtools;

import com.sun.javafx.scene.web.Debugger;
import javafx.util.Callback;

/**
 *
 * @author mizoguch-ken
 */
public class DevToolsDebugger {

    private final DevTools devTools_;
    private final Debugger debugger_;

    /**
     *
     * @param devTools
     */
    public DevToolsDebugger(DevTools devTools) {
        // impl_getDebugger is deprecated code...
        devTools_ = devTools;
        debugger_ = devTools.webEngine().impl_getDebugger();
    }

    public boolean isEnabled() {
        return debugger_.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        debugger_.setEnabled(enabled);
    }

    public void sendMessage(String message) {
        debugger_.sendMessage(message);
    }

    public Callback<String, Void> getMessageCallback() {
        return debugger_.getMessageCallback();
    }

    public void setMessageCallback(Callback<String, Void> callback) {
        debugger_.setMessageCallback(callback);
    }

    public void writeLog(String msg, boolean err) {
        devTools_.writeLog(msg, err);
    }
}
