# GcodeFXWebPlugins Serial2
## Overview 概要
 * Serial2 control.  
 シリアル2制御
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
 * Boolean open(String name, int baud, int databits, double stopbits, String parity);
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
serial2.open('COM1', 115200, 8, 1, 'EVEN');
while(serial2.available() > 0) {
  var c = serial2.read();
  data += String.fromCharCode(c);
}
```
