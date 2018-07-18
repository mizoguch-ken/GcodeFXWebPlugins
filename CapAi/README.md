# GcodeFXWebPlugins CapAi
## Overview 概要
 * Plug-in for communication with CapAi.  
 CapAiとの通信のためのプラグイン
## Functions 関数
 * void void capai.licenses();
 * void setNotifyServerStart(String func) ;
 * void setNotifyServerStop(String func);
 * void setNotifyServerRequest(String func);
 * void setNotifyServerResponse(String func);
 * void setNotifyServerError(String func);
 * void setNotifyClientStart(String func);
 * void setNotifyClientStop(String func);
 * void setNotifyClientRequest(String func);
 * void setNotifyClientResponse(String func);
 * void setNotifyClientError(String func);
 * Boolean isLinkBoxServerRunning();
 * Boolean openLinkBoxServer();
 * Boolean closeLinkBoxServer();
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
 * Boolean reboot();
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
capai.setL1('DOWN', 'GREEN', 'LIGHT', 'OFF', 'NONE', 'OFF', 'OFF', 'OFF', [{unit: 2001, view: '3'}]);
```
