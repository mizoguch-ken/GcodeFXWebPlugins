# GcodeFXWebPlugins Soem
## Overview 概要
 * SOEM control.  
 SOEM制御
## Functions 関数
 * Boolean load(String libraryPath);
 * Boolean init(int size);
 * Boolean po2so(int slave, long eep_man, long eep_id, String func);
 * Boolean start(String ifname, long cycletime);
 * Boolean start_redundant(String ifname, String ifname2, long cycletime);
 * void setNotifyErrorSafeOpError(String func);
 * void setNotifyErrorLost(String func);
 * void setNotifyWarningSafeOp(String func);
 * void setNotifyMessageReconfigured(String func);
 * void setNotifyMessageRecovered(String func);
 * void setNotifyMessageFound(String func);
 * void setNotifyMessageAllSlavesResumedOperational(String func);
 * Integer slavecount();
 * Integer state(int slave);
 * Boolean islost(int slave);
 * Boolean docheckstate();
 * Byte[] sdoRead(int slave, int index, int subIndex, int byteSize);
 * Integer sdoWrite(int slave, int index, int subIndex, int byteSize, Object value);
 * Long in(int slave, long bitsOffset, long bitsMask);
 * Long outs(int slave, long bitsOffset, long bitsMask);
 * Long out(int slave, long bitsOffset, long bitsMask, long value);
 * String find_adapters();
 * String slaveinfo(boolean printSDO, boolean printMAP);
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  

e.g.  
```
soem.load('/path/to/file');
soem.init(4096);
soem.start('eth0', 1000);
```


 * Find the Ethernet adapter  
 イーサネットアダプタを見つける  

e.g.  
```
soem.load('/path/to/file');
soem.find_adapters();
```


 * Acquire slave information  
 スレーブ情報を取得する  

e.g.
```
soem.load('/path/to/file');
soem.init(4096);
soem.start('eth0', 1000);
soem.slaveinfo(false, false);
```
