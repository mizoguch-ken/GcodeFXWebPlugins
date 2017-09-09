/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.act;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;

/**
 *
 * @author mizoguch-ken
 */
public class Act implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "act";

    private static OSs OS;
    private Process process_;
    private final StringBuilder out_;
    private final StringBuilder err_;

    private enum OSs {
        nil,
        windows,
        linux,
        mac;
    };

    /**
     *
     */
    public Act() {
        if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).startsWith("windows")) {
            OS = OSs.windows;
        } else if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).startsWith("linux")) {
            OS = OSs.linux;
        } else if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).startsWith("mac")) {
            OS = OSs.mac;
        } else {
            OS = OSs.nil;
        }

        process_ = null;
        out_ = new StringBuilder();
        err_ = new StringBuilder();
    }

    /**
     *
     * @param command
     * @return
     * @throws java.io.IOException
     */
    public Boolean exec(String command) throws IOException {
        switch (OS) {
            case windows:
                execute("cmd", "/c", command);
                return true;
            case linux:
                execute("sh", "-c", command);
                return true;
            case mac:
                write(FUNCTION_NAME, "Mac is not supported", true);
                break;
            default:
                write(FUNCTION_NAME, "OS is not supported", true);
                break;
        }
        return false;
    }

    /**
     *
     * @param command
     * @return
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public Integer execWaitFor(String command) throws InterruptedException, IOException {
        switch (OS) {
            case windows:
                return execute("cmd", "/c", command).waitFor();
            case linux:
                return execute("sh", "-c", command).waitFor();
            case mac:
                write(FUNCTION_NAME, "Mac is not supported", true);
                break;
            default:
                write(FUNCTION_NAME, "OS is not supported", true);
                break;
        }
        return null;
    }

    private Act execute(List<String> command) throws IOException {
        process_ = new ProcessBuilder(command).start();
        startProcessStream();
        return this;
    }

    private Act execute(String... command) throws IOException {
        process_ = new ProcessBuilder(command).start();
        startProcessStream();
        return this;
    }

    private int waitFor() throws InterruptedException {
        if (process_ != null) {
            return process_.waitFor();
        }
        return -1;
    }

    private boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
        if (process_ != null) {
            return process_.waitFor(timeout, unit);
        }
        return false;
    }

    private void startProcessStream() {
        out_.delete(0, out_.length());
        new ProcessStream(process_.getInputStream(), out_).start();
        err_.delete(0, err_.length());
        new ProcessStream(process_.getErrorStream(), err_).start();
    }

    private static class ProcessStream extends Service<Void> {

        private final InputStream inputStream_;
        private final StringBuilder stringBuilder_;

        private ProcessStream(InputStream inputStream, StringBuilder stringBuilder) {
            inputStream_ = inputStream;
            stringBuilder_ = stringBuilder;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream_, "UTF-8"))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder_.append(line);
                        }
                    }
                    return null;
                }
            };
        }
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
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
