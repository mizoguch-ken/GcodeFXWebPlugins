# GcodeFXWebPlugins Serial
## Overview 概要
 * Serial control.  
 シリアル制御
## Functions 関数
 * void licenses();
 * SerialPort getSerialPort();
 * String getPorts();
 * void setDTR(boolean state);
 * void setRTS(boolean state);
 * void setNotifyDataAvailable(String func);
 * void setNotifyOutputEmpty(String func);
 * void setNotifyBreakInterrupt(String func);
 * void setNotifyCarrierDetect(String func);
 * void setNotifyCTS(String func);
 * void setNotifyDSR(String func);
 * void setNotifyFramingError(String func);
 * void setNotifyOverrunError(String func);
 * void setNotifyParityError(String func);
 * void setNotifyRingIndicator(String func);
 * Boolean open(String name, String baud, String databits, String stopbits, String parity);
 * Integer available();
 * Integer read();
 * Boolean write(String text);
 * Boolean isOwned();
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
var data = '';
serial.open('COM1', '115200', '8', '1', 'EVEN');
while(serial.available() > 0) {
  var c = serial.read();
  data += String.fromCharCode(c);
}
```
