/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.onnx;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtLoggingLevel;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.TensorInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;

/**
 *
 * @author mizoguch-ken
 */
public class Onnx implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "onnx";

    private OrtEnvironment env_;
    private OrtSession.SessionOptions opts_;
    private OrtSession session_;
    private ByteBuffer bytesData_;

    private final Gson gson_ = new Gson();

    /**
     *
     * @throws java.lang.ClassNotFoundException
     */
    public Onnx() throws ClassNotFoundException {
        Class.forName("ai.onnxruntime.OrtSession");

        env_ = null;
        opts_ = null;
        session_ = null;
        bytesData_ = null;
    }

    /**
     *
     */
    public void licenses() {
        new Licenses().show();
    }

    /**
     *
     * @param mode
     * @throws ai.onnxruntime.OrtException
     */
    public void setExecutionMode(String mode) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setExecutionMode(OrtSession.SessionOptions.ExecutionMode.valueOf(mode));
    }

    /**
     *
     * @param level
     * @throws ai.onnxruntime.OrtException
     */
    public void setOptimizationLevel(String level) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setOptimizationLevel(OrtSession.SessionOptions.OptLevel.valueOf(level));
    }

    /**
     *
     * @param numThreads
     * @throws ai.onnxruntime.OrtException
     */
    public void setInterOpNumThreads(int numThreads) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setInterOpNumThreads(numThreads);
    }

    /**
     *
     * @param numThreads
     * @throws ai.onnxruntime.OrtException
     */
    public void setIntraOpNumThreads(int numThreads) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setIntraOpNumThreads(numThreads);
    }

    /**
     *
     * @param outputPath
     * @throws ai.onnxruntime.OrtException
     */
    public void setOptimizedModelFilePath(String outputPath) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setOptimizedModelFilePath(outputPath);
    }

    /**
     *
     * @param loggerId
     * @throws ai.onnxruntime.OrtException
     */
    public void setLoggerId(String loggerId) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setLoggerId(loggerId);
    }

    /**
     *
     * @param filePath
     * @throws ai.onnxruntime.OrtException
     */
    public void enableProfiling(String filePath) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.enableProfiling(filePath);
    }

    /**
     *
     * @throws ai.onnxruntime.OrtException
     */
    public void disableProfiling() throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.disableProfiling();
    }

    /**
     *
     * @param memoryPatternOptimization
     * @throws ai.onnxruntime.OrtException
     */
    public void setMemoryPatternOptimization(boolean memoryPatternOptimization) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setMemoryPatternOptimization(memoryPatternOptimization);
    }

    /**
     *
     * @param useArena
     * @throws ai.onnxruntime.OrtException
     */
    public void setCPUArenaAllocator(boolean useArena) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setCPUArenaAllocator(useArena);
    }

    /**
     *
     * @param logLevel
     * @throws ai.onnxruntime.OrtException
     */
    public void setSessionLogLevel(String logLevel) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setSessionLogLevel(OrtLoggingLevel.valueOf(logLevel));
    }

    /**
     *
     * @param logLevel
     * @throws ai.onnxruntime.OrtException
     */
    public void setSessionLogVerbosityLevel(int logLevel) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.setSessionLogVerbosityLevel(logLevel);
    }

    /**
     *
     * @param path
     * @throws ai.onnxruntime.OrtException
     */
    public void registerCustomOpLibrary(String path) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.registerCustomOpLibrary(path);
    }

    /**
     *
     * @param deviceNum
     * @throws ai.onnxruntime.OrtException
     */
    public void addCUDA(int deviceNum) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addCUDA(deviceNum);
    }

    /**
     *
     * @param useArena
     * @throws ai.onnxruntime.OrtException
     */
    public void addCPU(boolean useArena) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addCPU(useArena);
    }

    /**
     *
     * @param useArena
     * @throws ai.onnxruntime.OrtException
     */
    public void addDnnl(boolean useArena) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addDnnl(useArena);
    }

    /**
     *
     * @param ngBackendType
     * @throws ai.onnxruntime.OrtException
     */
    public void addNGraph(String ngBackendType) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addNGraph(ngBackendType);
    }

    /**
     *
     * @param deviceId
     * @throws ai.onnxruntime.OrtException
     */
    public void addOpenVINO(String deviceId) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addOpenVINO(deviceId);
    }

    /**
     *
     * @param deviceNum
     * @throws ai.onnxruntime.OrtException
     */
    public void addTensorrt(int deviceNum) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addTensorrt(deviceNum);
    }

    /**
     *
     * @throws ai.onnxruntime.OrtException
     */
    public void addNnapi() throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addNnapi();
    }

    /**
     *
     * @param allowUnalignedBuffers
     * @param settings
     * @throws ai.onnxruntime.OrtException
     */
    public void addNuphar(boolean allowUnalignedBuffers, String settings) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addNuphar(allowUnalignedBuffers, settings);
    }

    /**
     *
     * @param deviceId
     * @throws ai.onnxruntime.OrtException
     */
    public void addDirectML(int deviceId) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addDirectML(deviceId);
    }

    /**
     *
     * @param useArena
     * @throws ai.onnxruntime.OrtException
     */
    public void addACL(boolean useArena) throws OrtException {
        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }
        opts_.addACL(useArena);
    }

    /**
     *
     * @param onnxPath
     * @return
     */
    public Boolean load(String onnxPath) {
        if (session_ != null) {
            try {
                session_.close();
            } catch (OrtException ex) {
                webViewer_.writeStackTrace("ONNX", ex);
            }
            session_ = null;
        }

        if (opts_ == null) {
            opts_ = new OrtSession.SessionOptions();
        }

        if (env_ != null) {
            if (!env_.isClosed()) {
                try {
                    env_.close();
                } catch (OrtException ex) {
                    webViewer_.writeStackTrace("ONNX", ex);
                }
            }
            env_ = null;
        }

        try {
            env_ = OrtEnvironment.getEnvironment();
            session_ = env_.createSession(onnxPath, opts_);
            return true;
        } catch (OrtException ex) {
            webViewer_.writeStackTrace("ONNX", ex);
        }

        return false;
    }

    /**
     *
     * @return
     */
    public Long getNumInputs() {
        if (session_ != null) {
            return getNumInputs();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Long getNumOutputs() {
        if (session_ != null) {
            return getNumOutputs();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getInputNames() {
        if (session_ != null) {
            return gson_.toJson(session_.getInputNames());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getOutputNames() {
        if (session_ != null) {
            return gson_.toJson(session_.getOutputNames());
        }
        return null;
    }

    /**
     *
     * @return @throws ai.onnxruntime.OrtException
     */
    public String getInputInfo() throws OrtException {
        if (session_ != null) {
            return gson_.toJson(session_.getInputInfo());
        }
        return null;
    }

    /**
     *
     * @return @throws OrtException
     */
    public String getOutputInfo() throws OrtException {
        if (session_ != null) {
            return gson_.toJson(session_.getOutputInfo());
        }
        return null;
    }

    /**
     *
     * @param jsonElement
     * @return
     * @throws ai.onnxruntime.OrtException
     */
    public String run(String jsonElement) throws OrtException {
        if (session_ != null) {
            ConcurrentHashMap<String, OnnxTensor> inputs = new ConcurrentHashMap<>();
            ConcurrentHashMap<String, Object> outputs = new ConcurrentHashMap<>();
            JsonObject jsonObject = gson_.fromJson(jsonElement, JsonObject.class);

            for (String inputName : session_.getInputNames()) {
                if (session_.getInputInfo().get(inputName).getInfo() instanceof TensorInfo) {
                    TensorInfo tensorInfo = (TensorInfo) session_.getInputInfo().get(inputName).getInfo();
                    if (jsonObject.get(inputName).isJsonArray()) {
                        byte[] bytes = gson_.fromJson(jsonObject.getAsJsonArray(inputName), byte[].class);
                        long[] shape = tensorInfo.getShape();

                        for (int i = 0; i < shape.length; i++) {
                            if (shape[i] == -1) {
                                shape[i] = 1;
                            }
                        }

                        if (bytesData_ == null) {
                            bytesData_ = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
                        } else if (bytesData_.limit() <= bytes.length) {
                            bytesData_ = ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder());
                        }
                        bytesData_.put(bytes);
                        bytesData_.clear();

                        switch (tensorInfo.type) {
                            case FLOAT:
                                inputs.put(inputName, OnnxTensor.createTensor(env_, bytesData_.asFloatBuffer(), shape));
                                break;
                            case DOUBLE:
                                inputs.put(inputName, OnnxTensor.createTensor(env_, bytesData_.asDoubleBuffer(), shape));
                                break;
                            case INT8:
                                inputs.put(inputName, OnnxTensor.createTensor(env_, bytesData_, shape));
                                break;
                            case INT16:
                                inputs.put(inputName, OnnxTensor.createTensor(env_, bytesData_.asShortBuffer(), shape));
                                break;
                            case INT32:
                                inputs.put(inputName, OnnxTensor.createTensor(env_, bytesData_.asIntBuffer(), shape));
                                break;
                            case INT64:
                                inputs.put(inputName, OnnxTensor.createTensor(env_, bytesData_.asLongBuffer(), shape));
                                break;
                            case BOOL:
                            case STRING:
                            case UNKNOWN:
                            default:
                                break;
                        }
                    }
                }
            }

            if (!inputs.isEmpty()) {
                try (OrtSession.Result result = session_.run(inputs)) {
                    for (String outputName : session_.getOutputNames()) {
                        outputs.put(outputName, result.get(outputName).get().getValue());
                    }
                }
                return gson_.toJson(outputs);
            }
        }
        return null;
    }

    /**
     *
     * @param webViewer
     */
    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
    }

    /**
     *
     * @return
     */
    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    /**
     *
     * @param state
     */
    @Override
    public void state(Worker.State state) {
    }

    /**
     *
     */
    @Override
    public void close() {
        if (session_ != null) {
            try {
                session_.close();
            } catch (OrtException ex) {
                webViewer_.writeStackTrace("ONNX", ex);
            }
            session_ = null;
        }

        if (opts_ != null) {
            opts_.close();
            opts_ = null;
        }

        if (env_ != null) {
            if (!env_.isClosed()) {
                try {
                    env_.close();
                } catch (OrtException ex) {
                    webViewer_.writeStackTrace("ONNX", ex);
                }
            }
            env_ = null;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Stage stage() {
        return webViewer_.stage();
    }

    /**
     *
     * @return
     */
    @Override
    public List<Image> icons() {
        return webViewer_.icons();
    }

    /**
     *
     * @return
     */
    @Override
    public WebEngine webEngine() {
        return webViewer_.webEngine();
    }

    /**
     *
     * @return
     */
    @Override
    public Path webPath() {
        return webViewer_.webPath();
    }

    /**
     *
     * @param name
     * @param throwable
     */
    @Override
    public void writeStackTrace(String name, Throwable throwable) {
        webViewer_.writeStackTrace(name, throwable);
    }

    /**
     *
     * @param name
     * @param msg
     * @param err
     */
    @Override
    public void write(String name, String msg, boolean err) {
        webViewer_.write(name, msg, err);
    }
}
