/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.devtools;

/**
 *
 * @author mizoguch-ken
 */
public enum DevToolsEnums {
    DEV_TOOLS("DevTools");

    private final String text_;

    private DevToolsEnums(final String text) {
        text_ = text;
    }

    @Override
    public String toString() {
        return text_;
    }
}
