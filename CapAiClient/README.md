# GcodeFXWebPlugins CapAiClient
## Overview 概要
 * Plug-in for communication with CapAi.  
 CapAiとの通信のためのプラグイン
## Functions 関数
 * void void licenses();
 * void setNotifyClientStart(String func);
 * void setNotifyClientStop(String func);
 * void setNotifyClientRequest(String func);
 * void setNotifyClientResponse(String func);
 * void setNotifyClientError(String func);
 * Boolean isLinkBoxClientRunning();
 * Boolean setLinkBoxClientConfig(String address, int accNumber, int timeout);
 * Boolean openLinkBoxClient();
 * Boolean closeLinkBoxClient();
 * String getLocalAddress();
 * Integer getConnectionPort();
 * Boolean portClose(int port);
 * Boolean getPortInfo(int port);
 * Boolean initAK();
 * Boolean mnt(int ch);
 * Boolean addrAK(int unit);
 * Boolean setAutoATT();
 * Boolean clearAutoATT();
 * Boolean setL1(String normalDirection, String normalLed, String normalSeg, String normalBuz, String answerDirection, String answerLed, String answerSeg, String answerBuz, String jsonElement);
 * Boolean getAK();
 * Boolean startDev(String jsonElement);
 * Boolean demoAK(int unit, String view);
 * Boolean clearAK(String jsonElement);
 * Boolean lock(int unit);
 * Boolean unLock(int unit);
 * Boolean getLock(int unit);
 * Boolean clearLock(int unit);
 * Boolean getErrorCode();
 * Boolean clearErrorCode();
 * Boolean setIPAddr(String address, String mask, String gateway);
 * Boolean setHostAddr(String jsonElement);
 * Boolean reboot();
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
if(!capaiclient.isLinkBoxClientRunning()) {
  capaiclient.setLinkBoxClientConfig('192.168.10.51', '01', 30000);
  capaiclient.openLinkBoxClient();
}
capaiclient.setL1('DOWN', 'GREEN', 'LIGHT', 'OFF', 'NONE', 'OFF', 'OFF', 'OFF', JSON.stringify([{unit: 2001, view: '3'}]));
```
