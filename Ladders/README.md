# GcodeFXWebPlugins Ladders
## Overview 概要
 * Access Ladders.  
 Laddersにアクセス
## Functions 関数
 * Integer getHistoryGeneration();
 * void setHistoryGeneration(int historyGeneration);
 * Long getIdealCycleTime();
 * void setIdealCycleTime(long idealCycleTime);
 * void registerWeb();
 * void unregisterWeb();
 * Boolean registerSoemIn(String address, int slave, long bitsOffset, long bitsMask);
 * Boolean registerSoemOut(String address, int slave, long bitsOffset, long bitsMask);
 * void unregisterSoem();
 * Double getValue(String address);
 * void setValue(String address, double value);
 * Boolean connect();
 * Boolean run();
 * void stop();
 * Boolean fileNew();
 * Boolean fileOpen(String path);
 * Boolean fileSave();
 * String getFileName();
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
ladders.getValue('IN0');
ladders.setValue('OUT0', 3.14);
```
 
 
 * When linking with SOEM plugin  
 SOEMプラグインとリンクするとき  
 
e.g.  
```
soem.load('/path/to/file');
soem.init(4096);
soem.start('eth0', 1000);
ladders.registerSoemIn('IN0', 2, 0, 0x01);
ladders.registerSoemOut('OUT0', 3, 0, 0x01);
```
