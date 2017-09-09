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
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;

/**
 *
 * @author mizoguch-ken
 */
public class Stage implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "stage";

    private javafx.stage.Stage stage_;

    /**
     *
     */
    public Stage() {
        stage_ = null;
    }

    /**
     *
     * @return
     */
    public Boolean isShowing() {
        return stage_.isShowing();
    }

    /**
     *
     */
    public void show() {
        stage_.show();
    }

    /**
     *
     */
    public void hide() {
        stage_.hide();
    }

    /**
     *
     * @return
     */
    public Boolean isFocused() {
        return stage_.isFocused();
    }

    /**
     *
     */
    public void requestFocus() {
        stage_.requestFocus();
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return stage_.getTitle();
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        stage_.setTitle(title);
    }

    /**
     *
     * @return
     */
    public Double getWidth() {
        return stage_.getWidth();
    }

    /**
     *
     * @param value
     */
    public void setWidth(double value) {
        stage_.setWidth(value);
    }

    /**
     *
     * @return
     */
    public Double getMinWidth() {
        return stage_.getMinWidth();
    }

    /**
     *
     * @param value
     */
    public void setMinWidth(double value) {
        stage_.setMinWidth(value);
    }

    /**
     *
     * @return
     */
    public Double getMaxWidth() {
        return stage_.getMaxWidth();
    }

    /**
     *
     * @param value
     */
    public void setMaxWidth(double value) {
        stage_.setMaxWidth(value);
    }

    /**
     *
     * @return
     */
    public Double getHeight() {
        return stage_.getHeight();
    }

    /**
     *
     * @param value
     */
    public void setHeight(double value) {
        stage_.setHeight(value);
    }

    /**
     *
     * @return
     */
    public Double getMinHeight() {
        return stage_.getMinHeight();
    }

    /**
     *
     * @param value
     */
    public void setMinHeight(double value) {
        stage_.setMinHeight(value);
    }

    /**
     *
     * @return
     */
    public Double getMaxHeight() {
        return stage_.getMaxHeight();
    }

    /**
     *
     * @param value
     */
    public void setMaxHeight(double value) {
        stage_.setMaxHeight(value);
    }

    /**
     *
     * @return
     */
    public Double getOpacity() {
        return stage_.getOpacity();
    }

    /**
     *
     * @param value
     */
    public void setOpacity(double value) {
        stage_.setOpacity(value);
    }

    /**
     *
     * @return
     */
    public Boolean isAlwaysOnTop() {
        return stage_.isAlwaysOnTop();
    }

    /**
     *
     * @param state
     */
    public void setAlwaysOnTop(boolean state) {
        stage_.setAlwaysOnTop(state);
    }

    /**
     *
     * @return
     */
    public Boolean isMaximized() {
        return stage_.isMaximized();
    }

    /**
     *
     * @param state
     */
    public void setMaximized(boolean state) {
        stage_.setMaximized(state);
    }

    /**
     *
     * @return
     */
    public Double getX() {
        return stage_.getX();
    }

    /**
     *
     * @param value
     */
    public void setX(double value) {
        stage_.setX(value);
    }

    /**
     *
     * @return
     */
    public Double getY() {
        return stage_.getY();
    }

    /**
     *
     * @param value
     */
    public void setY(double value) {
        stage_.setY(value);
    }

    /**
     *
     */
    public void toBack() {
        stage_.toBack();
    }

    /**
     *
     */
    public void toFront() {
        stage_.toFront();
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
        stage_ = webViewer.stage();
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
        stage_.close();
    }

    @Override
    public javafx.stage.Stage stage() {
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
