/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.serial;

import com.google.gson.Gson;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;
import netscape.javascript.JSException;

/**
 *
 * @author mizoguch-ken
 */
public class Serial implements WebViewerPlugin, SerialPortEventListener {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "serial";

    private WebEngine webEngine_;
    private Worker.State state_;
    private SerialPort port_;
    private boolean owner_;
    private boolean boolDTR_, boolRTS_;
    private String funcDataAvailable_, funcOutputEmpty_, funcBreakInterrupt_, funcCarrierDetect_, funcCTS_, funcDSR_, funcFramingError_, funcOverrunError_, funcParityError_, funcRingIndicator_;
    private BlockingQueue<Integer> data_;
    private BufferedReader bufferedReader_;
    private BufferedWriter bufferedWriter_;
    private final Gson gson_ = new Gson();

    /**
     *
     */
    public Serial() {
        webEngine_ = null;
        state_ = Worker.State.READY;
        port_ = null;
        owner_ = false;
        boolDTR_ = false;
        boolRTS_ = false;
        funcDataAvailable_ = null;
        funcOutputEmpty_ = null;
        funcBreakInterrupt_ = null;
        funcCarrierDetect_ = null;
        funcCTS_ = null;
        funcDSR_ = null;
        funcFramingError_ = null;
        funcOverrunError_ = null;
        funcParityError_ = null;
        funcRingIndicator_ = null;
        data_ = null;
        bufferedReader_ = null;
        bufferedWriter_ = null;
    }

    /**
     *
     */
    public void licenses() {
        new Licenses().show();
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
    public SerialPort getSerialPort() {
        if (port_ != null) {
            port_.notifyOnDataAvailable(false);
            port_.notifyOnOutputEmpty(false);
            port_.notifyOnBreakInterrupt(false);
            port_.notifyOnCarrierDetect(false);
            port_.notifyOnCTS(false);
            port_.notifyOnDSR(false);
            port_.notifyOnFramingError(false);
            port_.notifyOnOverrunError(false);
            port_.notifyOnParityError(false);
            port_.notifyOnRingIndicator(false);
        }

        if (bufferedWriter_ != null) {
            try {
                bufferedWriter_.close();
                bufferedWriter_ = null;
            } catch (IOException ex) {
                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
            }
        }

        if (bufferedReader_ != null) {
            try {
                bufferedReader_.close();
                bufferedReader_ = null;
            } catch (IOException ex) {
                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
            }
        }

        if (data_ != null) {
            data_.clear();
            data_ = null;
        }
        return port_;
    }

    /**
     *
     * @return
     */
    public String getPorts() {
        String portName;
        boolean portOwned;

        List<String> retPortName = new ArrayList<>();
        Enumeration enm = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier cpi;

        while (enm.hasMoreElements()) {
            // get port of list
            cpi = (CommPortIdentifier) enm.nextElement();

            // get port name
            portName = cpi.getName();

            // true = used / false = unused
            portOwned = cpi.isCurrentlyOwned();

            // add port
            if (!portOwned) {
                retPortName.add(portName);
            }
        }
        return gson_.toJson(retPortName);
    }

    /**
     *
     * @param state
     */
    public void setDTR(boolean state) {
        boolDTR_ = state;
        if (owner_) {
            port_.setDTR(state);
        }
    }

    /**
     *
     * @param state
     */
    public void setRTS(boolean state) {
        boolRTS_ = state;
        if (owner_) {
            port_.setRTS(state);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyDataAvailable(String func) {
        funcDataAvailable_ = func;
        if (owner_) {
            port_.notifyOnDataAvailable(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyOutputEmpty(String func) {
        funcOutputEmpty_ = func;
        if (owner_) {
            port_.notifyOnOutputEmpty(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyBreakInterrupt(String func) {
        funcBreakInterrupt_ = func;
        if (owner_) {
            port_.notifyOnBreakInterrupt(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyCarrierDetect(String func) {
        funcCarrierDetect_ = func;
        if (owner_) {
            port_.notifyOnCarrierDetect(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyCTS(String func) {
        funcCTS_ = func;
        if (owner_) {
            port_.notifyOnCTS(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyDSR(String func) {
        funcDSR_ = func;
        if (owner_) {
            port_.notifyOnDSR(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyFramingError(String func) {
        funcFramingError_ = func;
        if (owner_) {
            port_.notifyOnFramingError(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyOverrunError(String func) {
        funcOverrunError_ = func;
        if (owner_) {
            port_.notifyOnOverrunError(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyParityError(String func) {
        funcParityError_ = func;
        if (owner_) {
            port_.notifyOnParityError(func != null);
        }
    }

    /**
     *
     * @param func
     */
    public void setNotifyRingIndicator(String func) {
        funcRingIndicator_ = func;
        if (owner_) {
            port_.notifyOnRingIndicator(func != null);
        }
    }

    /**
     *
     * @param name
     * @param baud
     * @param databits
     * @param stopbits
     * @param parity
     * @return
     * @throws java.util.TooManyListenersException
     * @throws java.io.IOException
     * @throws gnu.io.NoSuchPortException
     * @throws gnu.io.PortInUseException
     * @throws gnu.io.UnsupportedCommOperationException
     */
    public Boolean open(String name, String baud, String databits, String stopbits, String parity) throws TooManyListenersException, IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        if (!owner_) {
            if ((name != null) && (baud != null) && (databits != null) && (stopbits != null) && (parity != null)) {
                if ((!name.isEmpty()) && (!baud.isEmpty()) && (!databits.isEmpty()) && (!stopbits.isEmpty()) && (!parity.isEmpty())) {
                    CommPortIdentifier id = CommPortIdentifier.getPortIdentifier(name);
                    CommPort com = id.open("WebSerial", -1);
                    port_ = (SerialPort) com;

                    int[] param = new int[4];

                    param[0] = Integer.parseInt(baud.trim());
                    switch (databits.trim()) {
                        case "5":
                            param[1] = SerialPort.DATABITS_5;
                            break;
                        case "6":
                            param[1] = SerialPort.DATABITS_6;
                            break;
                        case "7":
                            param[1] = SerialPort.DATABITS_7;
                            break;
                        case "8":
                            param[1] = SerialPort.DATABITS_8;
                            break;
                        default:
                            param[1] = 0;
                            break;
                    }
                    switch (stopbits.trim()) {
                        case "1":
                            param[2] = SerialPort.STOPBITS_1;
                            break;
                        case "2":
                            param[2] = SerialPort.STOPBITS_2;
                            break;
                        case "1.5":
                            param[2] = SerialPort.STOPBITS_1_5;
                            break;
                        default:
                            param[2] = 0;
                    }
                    switch (parity.trim()) {
                        case "NONE":
                            param[3] = SerialPort.PARITY_NONE;
                            break;
                        case "ODD":
                            param[3] = SerialPort.PARITY_ODD;
                            break;
                        case "EVEN":
                            param[3] = SerialPort.PARITY_EVEN;
                            break;
                        case "MARK":
                            param[3] = SerialPort.PARITY_MARK;
                            break;
                        case "SPACE":
                            param[3] = SerialPort.PARITY_SPACE;
                            break;
                        default:
                            param[3] = 0;
                            break;
                    }

                    port_.setSerialPortParams(
                            param[0],
                            param[1],
                            param[2],
                            param[3]
                    );
                    port_.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                    bufferedReader_ = new BufferedReader(new InputStreamReader(port_.getInputStream(), "UTF-8"));
                    bufferedWriter_ = new BufferedWriter(new OutputStreamWriter(port_.getOutputStream(), "UTF-8"));
                    // input buffer clear
                    while (bufferedReader_.ready()) {
                        bufferedReader_.read();
                    }

                    port_.addEventListener(this);
                    port_.notifyOnDataAvailable(funcDataAvailable_ != null);
                    port_.notifyOnOutputEmpty(funcOutputEmpty_ != null);
                    port_.notifyOnBreakInterrupt(funcBreakInterrupt_ != null);
                    port_.notifyOnCarrierDetect(funcCarrierDetect_ != null);
                    port_.notifyOnCTS(funcCTS_ != null);
                    port_.notifyOnDSR(funcDSR_ != null);
                    port_.notifyOnFramingError(funcFramingError_ != null);
                    port_.notifyOnOverrunError(funcOverrunError_ != null);
                    port_.notifyOnParityError(funcParityError_ != null);
                    port_.notifyOnRingIndicator(funcRingIndicator_ != null);

                    port_.setDTR(boolDTR_);
                    port_.setRTS(boolRTS_);

                    data_ = new LinkedBlockingQueue<>();

                    owner_ = true;
                    return true;
                } else {
                    webViewer_.write(FUNCTION_NAME, "Incorrect serial argument", true);
                }
            } else {
                webViewer_.write(FUNCTION_NAME, "Incorrect serial argument", true);
            }
            return false;
        } else {
            webViewer_.write(FUNCTION_NAME, "Serial is already open", true);
            return false;
        }
    }

    /**
     *
     * @return
     */
    public Integer available() {
        if (data_ != null) {
            return data_.size();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Integer read() {
        if (data_ != null) {
            if (!data_.isEmpty()) {
                return data_.poll();
            }
        }
        return null;
    }

    /**
     *
     * @param text
     * @return
     * @throws java.io.IOException
     */
    public Boolean write(String text) throws IOException {
        if (bufferedWriter_ != null) {
            bufferedWriter_.write(text);
            bufferedWriter_.flush();
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Boolean isOwned() {
        return owner_;
    }

    /**
     *
     * @param spe
     */
    @Override
    public void serialEvent(SerialPortEvent spe) {
        switch (spe.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                dataAvailable(spe);
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                outputBufferEmpty(spe);
                break;
            case SerialPortEvent.BI:
                breakInterrupt(spe);
                break;
            case SerialPortEvent.CD:
                carrierDetect(spe);
                break;
            case SerialPortEvent.CTS:
                clearToSend(spe);
                break;
            case SerialPortEvent.DSR:
                dataSetReady(spe);
                break;
            case SerialPortEvent.FE:
                framingError(spe);
                break;
            case SerialPortEvent.OE:
                overrunError(spe);
                break;
            case SerialPortEvent.PE:
                parityError(spe);
                break;
            case SerialPortEvent.RI:
                ringIndicator(spe);
                break;
            default:
                break;
        }
    }

    /**
     * dataAvailable
     *
     * @param spe
     */
    protected void dataAvailable(SerialPortEvent spe) {
        if (funcDataAvailable_ != null) {
            if ((data_ != null) && (bufferedReader_ != null)) {
                try {
                    while (bufferedReader_.ready()) {
                        data_.offer(bufferedReader_.read());
                        if (!bufferedReader_.ready()) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(100);
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                } catch (IOException ex) {
                    webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                }
            }

            Platform.runLater(() -> {
                if (funcDataAvailable_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcDataAvailable_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * outputBufferEmpty
     *
     * @param spe
     */
    protected void outputBufferEmpty(SerialPortEvent spe) {
        if (funcOutputEmpty_ != null) {
            Platform.runLater(() -> {
                if (funcOutputEmpty_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcOutputEmpty_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * breakInterrupt
     *
     * @param spe
     */
    protected void breakInterrupt(SerialPortEvent spe) {
        if (funcBreakInterrupt_ != null) {
            Platform.runLater(() -> {
                if (funcBreakInterrupt_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcBreakInterrupt_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * carrierDetect
     *
     * @param spe
     */
    protected void carrierDetect(SerialPortEvent spe) {
        if (funcCarrierDetect_ != null) {
            Platform.runLater(() -> {
                if (funcCarrierDetect_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcCarrierDetect_ + "(" + port_.isCD() + ");");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * clearToSend
     *
     * @param spe
     */
    protected void clearToSend(SerialPortEvent spe) {
        if (funcCTS_ != null) {
            Platform.runLater(() -> {
                if (funcCTS_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcCTS_ + "(" + port_.isCTS() + ");");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * dataSetReady
     *
     * @param spe
     */
    protected void dataSetReady(SerialPortEvent spe) {
        if (funcDSR_ != null) {
            Platform.runLater(() -> {
                if (funcDSR_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcDSR_ + "(" + port_.isDSR() + ");");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * framingError
     *
     * @param spe
     */
    protected void framingError(SerialPortEvent spe) {
        if (funcFramingError_ != null) {
            Platform.runLater(() -> {
                if (funcFramingError_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcFramingError_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * overrunError
     *
     * @param spe
     */
    protected void overrunError(SerialPortEvent spe) {
        if (funcOverrunError_ != null) {
            Platform.runLater(() -> {
                if (funcOverrunError_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcOverrunError_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * parityError
     *
     * @param spe
     */
    protected void parityError(SerialPortEvent spe) {
        if (funcParityError_ != null) {
            Platform.runLater(() -> {
                if (funcParityError_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcParityError_ + "();");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * ringIndicator
     *
     * @param spe
     */
    protected void ringIndicator(SerialPortEvent spe) {
        if (funcRingIndicator_ != null) {
            Platform.runLater(() -> {
                if (funcRingIndicator_ != null) {
                    if (state_ == Worker.State.SUCCEEDED) {
                        try {
                            webEngine_.executeScript(funcRingIndicator_ + "(" + port_.isRI() + ");");
                        } catch (JSException ex) {
                            webViewer_.writeStackTrace(FUNCTION_NAME, ex);
                        }
                    }
                }
            });
        }
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
    public void state(Worker.State state) {
        state_ = state;
    }

    @Override
    public void close() {
        if (port_ != null) {
            port_.setRTS(false);
            port_.setDTR(false);

            port_.notifyOnDataAvailable(false);
            port_.notifyOnOutputEmpty(false);
            port_.notifyOnBreakInterrupt(false);
            port_.notifyOnCarrierDetect(false);
            port_.notifyOnCTS(false);
            port_.notifyOnDSR(false);
            port_.notifyOnFramingError(false);
            port_.notifyOnOverrunError(false);
            port_.notifyOnParityError(false);
            port_.notifyOnRingIndicator(false);
            port_.removeEventListener();
            port_.close();
            port_ = null;
        }

        if (bufferedWriter_ != null) {
            try {
                bufferedWriter_.close();
                bufferedWriter_ = null;
            } catch (IOException ex) {
                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
            }
        }

        if (bufferedReader_ != null) {
            try {
                bufferedReader_.close();
                bufferedReader_ = null;
            } catch (IOException ex) {
                webViewer_.writeStackTrace(FUNCTION_NAME, ex);
            }
        }

        if (data_ != null) {
            data_.clear();
            data_ = null;
        }

        owner_ = false;
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
