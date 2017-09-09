# GcodeFXWebPlugins Db
## Overview 概要
 * onnect to the database and execute SQL.  
 データベースへの接続し、SQLの実行
## Functions 関数
 * void set(String host, String port, String name, String user, String pass);
 * String(JSON) query(String sql);
 * Integer update(String sql);
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
 e.g.  
    db.set('192.168.1.100', '5432', 'db', 'postgres', 'postgres');
    var result = db.query('select id from tbl;');
    if (result) {
	  var data = JSON.parse(result);
    }