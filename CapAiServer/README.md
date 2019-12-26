# GcodeFXWebPlugins CapAiServer
## Overview 概要
 * Plug-in for communication with CapAi.  
 CapAiとの通信のためのプラグイン
## Functions 関数
 * void void licenses();
 * void setNotifyServerStart(String func) ;
 * void setNotifyServerStop(String func);
 * void setNotifyServerRequest(String func);
 * void setNotifyServerResponse(String func);
 * void setNotifyServerError(String func);
 * Boolean isLinkBoxServerRunning();
 * Boolean openLinkBoxServer();
 * Boolean closeLinkBoxServer();
 * String getLocalAddress();
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
if(!capaiserver.isLinkBoxServerRunning()) {
  capaiserver.openLinkBoxServer();
}
```
