/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.webcam;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;
import netscape.javascript.JSException;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import static org.opencv.videoio.Videoio.CAP_PROP_FPS;

/**
 *
 * @author mizoguch_ken
 */
public class WebCam extends Service<Void> implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "webcam";

    private WebEngine webEngine_;
    private Worker.State state_;

    private OpenCVFrameGrabber webcam_;
    private final Stage webcamStage_;
    private final BorderPane webcamPane_;
    private final ImageView webcamImageView_;
    private String funcResult_;
    private Image webcamImage_;
    private String webcamImageType_;
    private BufferedImage webcamBufferedImage_;
    private String webcamResultImage_;
    private boolean isPlay_, isUpdateView_;

    /**
     *
     */
    public WebCam() {
        webEngine_ = null;
        state_ = Worker.State.READY;

        webcam_ = null;
        webcamStage_ = new Stage(StageStyle.DECORATED);
        webcamPane_ = new BorderPane();
        webcamImageView_ = new ImageView();
        webcamStage_.setTitle("WebCam");
        webcamStage_.setScene(new Scene(webcamPane_));
        webcamStage_.setResizable(false);
        funcResult_ = null;
        webcamImage_ = null;
        webcamImageType_ = "jpeg";
        webcamBufferedImage_ = null;
        webcamResultImage_ = null;
        isPlay_ = false;
        isUpdateView_ = false;
    }

    /**
     *
     */
    public void licenses() {
        new Licenses().show();
    }

    /**
     *
     * @param func
     */
    public void setNotifyResult(String func) {
        funcResult_ = func;
    }

    /**
     *
     * @return
     */
    public String getImageType() {
        return webcamImageType_;
    }

    /**
     *
     * @param type
     * @return
     */
    public Boolean setImageType(String type) {
        if (type != null) {
            type = type.trim().toLowerCase(Locale.getDefault());
            String[] suffixes = ImageIO.getWriterFileSuffixes();

            for (String suffixe : suffixes) {
                if (suffixe.equals(type)) {
                    webcamImageType_ = suffixe;
                    return true;
                }
            }
            return false;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Boolean isOpened() {
        return (webcam_ != null);
    }

    /**
     *
     * @param webcamIndex
     * @return
     */
    public Boolean open(int webcamIndex) {
        if (webcam_ == null) {
            webcam_ = new OpenCVFrameGrabber(0);
            try {
                webcam_.start();
                webcamImageView_.setFitWidth(webcam_.getImageWidth());
                webcamImageView_.setFitHeight(webcam_.getImageHeight());
                webcamPane_.setCenter(webcamImageView_);
                isPlay_ = false;
                return true;
            } catch (FrameGrabber.Exception ex) {
                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                try {
                    webcam_.close();
                } catch (FrameGrabber.Exception e) {
                }
                webcam_ = null;
            }
        } else {
            webViewer_.write(FUNCTION_NAME, "Camera device is already open", true);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean isPlaying() {
        if (webcam_ != null) {
            return isPlay_;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean play() {
        if (webcam_ != null) {
            isPlay_ = true;
            isUpdateView_ = false;
            if (!this.isRunning()) {
                this.reset();
                this.start();
            }
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean stop() {
        isPlay_ = false;
        return this.cancel();
    }

    /**
     *
     * @return
     */
    public Boolean isShowing() {
        if (webcam_ != null) {
            return webcamStage_.isShowing();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean show() {
        if (webcam_ != null) {
            webcamStage_.show();
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean hide() {
        if (webcam_ != null) {
            webcamStage_.hide();
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Double getWidth() {
        if (webcam_ != null) {
            return webcamStage_.getWidth();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return null;
    }

    /**
     *
     * @return
     */
    public Double getHeight() {
        if (webcam_ != null) {
            return webcamStage_.getHeight();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return null;
    }

    /**
     *
     * @return
     */
    public Boolean isFocused() {
        if (webcam_ != null) {
            webcamStage_.isFocused();
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean requestFocus() {
        if (webcam_ != null) {
            webcamStage_.requestFocus();
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Double getOpacity() {
        if (webcam_ != null) {
            return webcamStage_.getOpacity();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return null;
    }

    /**
     *
     * @param value
     * @return
     */
    public Boolean setOpacity(double value) {
        if (webcam_ != null) {
            webcamStage_.setOpacity(value);
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean isAlwaysOnTop() {
        if (webcam_ != null) {
            return webcamStage_.isAlwaysOnTop();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @param state
     * @return
     */
    public Boolean setAlwaysOnTop(boolean state) {
        if (webcam_ != null) {
            webcamStage_.setAlwaysOnTop(state);
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Double getX() {
        if (webcam_ != null) {
            return webcamStage_.getX();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return null;
    }

    /**
     *
     * @param x
     * @return
     */
    public Boolean setX(double x) {
        if (webcam_ != null) {
            webcamStage_.setX(x);
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    /**
     *
     * @return
     */
    public Double getY() {
        if (webcamStage_ != null) {
            return webcamStage_.getY();
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return null;
    }

    /**
     *
     * @param y
     * @return
     */
    public Boolean setY(double y) {
        if (webcam_ != null) {
            webcamStage_.setY(y);
            return true;
        }
        webViewer_.write(FUNCTION_NAME, "Camera device is not open", true);
        return false;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();
                Frame frame;
                long frameDelay = 0;

                if (webcam_ != null) {
                    double frameRate = webcam_.getFrameRate();

                    if (frameRate > 0.0) {
                        frameDelay = (1000 / (long) frameRate);
                    } else {
                        frameDelay = 1000 / CAP_PROP_FPS;
                    }
                }

                while (isPlay_ && (webcam_ != null)) {
                    frame = webcam_.grab();
                    if (frame != null) {
                        if (frame.image != null) {
                            webcamBufferedImage_ = java2DFrameConverter.convert(frame);

                            if (webcamStage_.isShowing()) {
                                webcamImage_ = SwingFXUtils.toFXImage(webcamBufferedImage_, null);
                                if (!isUpdateView_) {
                                    isUpdateView_ = true;

                                    Platform.runLater(() -> {
                                        if (webcamImage_ != null) {
                                            webcamImageView_.setImage(webcamImage_);
                                        }
                                        isUpdateView_ = false;
                                    });
                                }
                            }

                            if (funcResult_ != null) {
                                ImageIO.write(webcamBufferedImage_, webcamImageType_, arrayOutputStream);
                                webcamResultImage_ = "data:image/" + webcamImageType_ + ";base64," + Base64.getEncoder().encodeToString(arrayOutputStream.toByteArray());
                                arrayOutputStream.reset();

                                Platform.runLater(() -> {
                                    if (funcResult_ != null) {
                                        if (state_ == Worker.State.SUCCEEDED) {
                                            try {
                                                webEngine_.executeScript(funcResult_ + "('" + webcamResultImage_ + "');");
                                            } catch (JSException ex) {
                                                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                                            }
                                        }
                                    }
                                });
                            }
                            webcamBufferedImage_.flush();

                            try {
                                TimeUnit.MILLISECONDS.sleep(frameDelay);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                }
                return null;
            }
        };
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
        webEngine_ = webViewer_.webEngine();
    }

    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public void state(State state) {
        state_ = state;
    }

    @Override
    public void close() {
        if (webcam_ != null) {
            try {
                webcam_.close();
            } catch (FrameGrabber.Exception ex) {
            }
            webcam_ = null;
        }
        webcamStage_.close();
        webcamBufferedImage_ = null;
        webcamResultImage_ = null;
        this.cancel();
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
