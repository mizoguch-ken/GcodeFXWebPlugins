# GcodeFXWebPlugins Gcode
## Overview 概要
 * Access Gcode.  
 Gcodeにアクセス
## Functions 関数
 * void licenses();
 * void setNotifyStart(String func);
 * void setNotifyAbnormalEnd(String func);
 * void setNotifyErrorEnd(String func);
 * void setNotifyNormalEnd(String func);
 * void setNotifyVariables(String func);
 * void setNotifyExComment(String func);
 * Boolean script(String codes);
 * Double getVariable(int address);
 * void setVariable(int address, Double value);
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
gcode.script('G91G28Z0');
```
