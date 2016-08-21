/**
 * Copyright (c) 2012 YCSB contributors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */

/**
 * Redis client binding for YCSB.
 *
 * All YCSB records are mapped to a Redis *hash field*.  For scanning
 * operations, all keys are saved (by an arbitrary hash) in a sorted set.
 */

package com.yahoo.ycsb.db;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.Status;
import com.yahoo.ycsb.StringByteIterator;

import com.yahoo.ycsb.db.GianessaDB;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.LinkedList;


public class GianessaClient extends DB {

  //public static final String GIANESSA_IP = "127.0.0.1";
  //public static final int  GIANESSA_PORT= 8889;
  public static final String HOST_PROPERTY = "gianessadb.host";
  public static final String PORT_PROPERTY = "gianessadb.port";
  private GianessaDB gianessadb;

  public void init() throws DBException {
    Properties props = getProperties();
    int port;

    String portString = props.getProperty(PORT_PROPERTY);
    if (portString != null) {
      port = Integer.parseInt(portString);
    } else {
      port = 8889;
    }
    String host = props.getProperty(HOST_PROPERTY);

    gianessadb= new GianessaDB(host,port);
    gianessadb.connect();
  }

  public void cleanup() throws DBException {
    gianessadb.disconnect();
  }

  private LinkedList<String> buildValue(HashMap<String, ByteIterator> values){
    String chunk="";
    Iterator<HashMap.Entry<String, ByteIterator>> it = values.entrySet().iterator();
    while (it.hasNext()) {
    HashMap.Entry<String, ByteIterator> entry = it.next();
      chunk=chunk+entry.getValue().toString();
    }
    LinkedList<String> valueList = new LinkedList<String>();
    valueList.add(chunk);
    return valueList;
  }

  private LinkedList<String> buildKey(String key){
    LinkedList<String> keyList = new LinkedList<String>();
    keyList.add(key);
    return keyList;
  }




  @Override
  public Status read(String table, String key, Set<String> fields, 
    HashMap<String, ByteIterator> result) {
    
    LinkedList<String> keyList = new LinkedList<String>();
    keyList.add(key);
    if(gianessadb.get(keyList)!=0)
      return Status.OK;
    else
      return Status.ERROR;
  }

  @Override
  public Status insert(String table, String key, HashMap<String, ByteIterator> values) {
    
    LinkedList<String> keyList = buildKey(key);
    LinkedList<String> valueList = buildValue(values);
    if(gianessadb.set(keyList,valueList)!=0)
      return Status.OK;
    else
      return Status.ERROR;
  }

  @Override
  public Status delete(String table, String key) {

    LinkedList<String> keyList = buildKey(key);
    if(gianessadb.del(keyList)!=0)
      return Status.OK;
    else
      return Status.ERROR;
  }

  @Override
  public Status update(String table, String key, HashMap<String, ByteIterator> values) {
    
    LinkedList<String> keyList = buildKey(key);
    LinkedList<String> valueList = buildValue(values);
    if(gianessadb.set(keyList,valueList)!=0)
      return Status.OK;
    else
      return Status.ERROR;
  }

  @Override
  public Status scan(String table, String startkey, int recordcount,
      Set<String> fields, Vector<HashMap<String, ByteIterator>> result) {

    LinkedList<String> keyList = buildKey(startkey);
    if(gianessadb.get(keyList)!=0)
      return Status.OK;
    else
      return Status.ERROR;
  }

}