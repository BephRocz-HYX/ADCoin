﻿# 区块链SDK接口文档



- [区块链SDK接口文档](#区块链SDK接口文档)
	- [说明](#说明)
	- [SDK详细描述](#SDK详细描述)
		- [初始化相关](#初始化相关)
			- [初始化client](#初始化client)
			- [安装链码](#安装链码)
			- [初始化链码](#初始化链码)
		- [链码相关](#链码相关)
			- [查询](#查询)
			- [调用](#调用)
			- [列举所有节点的所有链码](#列举所有节点的所有链码)
		- [区块相关](#区块相关)
			- [根据区块号查询区块信息](#根据区块号查询区块信息)
			- [根据区块哈希查询区块信息](#根据区块哈希查询区块信息)
			- [根据交易ID查询区块信息](#根据交易ID查询区块信息)
		- [节点相关](#节点相关)
			- [获取当前所有节点状态等信息](#获取当前所有节点状态等信息)



## 说明

初始化client需要用到一份yaml配置文件。

```yaml
# fabric.yaml
caClient:
  name: ca.example.com
  url: http://localhost:7054
client:
  # 超时配置不是必须的
  # 超时配置的单位是毫秒
  proposalWaitTime: 30000
  transactionTimeout: 40000
  chaincodeProposalWaitTime: 120000
  chaincodeTransactionTimeout: 120000
  user:
    name: Admin
    mspId: Org1MSP
    # 若 cert 和 certPath 同时存在，优先取 cert
    cert: # 这里放证书内容
    certPath: # 这里放证书路径
    # 若 privateKey 和 privateKeyPath 同时存在，优先取 cert
    privateKey: # 这里放私钥内容
    privateKeyPath: # 这里放私钥路径
  channels:
  - name: mychannel
    peers:
    - name: peer0.org1.example.com
      url: grpc://localhost:7051
    orderers:
    - name: orderer.example.com
      url: grpc://localhost:7050
    # 不配置 EventHub 是无法获取交易结果的
    eventHubs:
    - name: peer0.org1.example.com
      url: grpc://localhost:7053
```

## SDK详细描述

### 初始化相关
#### 初始化client

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** newClient

**调用方式：** 

```java
// 示例,连接 mychannel, 使用 1.0 版本的 fabcar 链码
FabricClient fabricClient = new Yaml().loadAs(inputStream, FabricProperties.class)
  .newFactory()
  .newClient("mychannel", "fabcar", "1.0");
```

new一个FabricClient

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
channeName | 是 | 字符串 | 自定义通道名称（需要跟安装链码时定义的一样）
chaincodeName | 是 | 字符串 | 链码的名称
version | 是 | 字符串 | 链码的版本


**说明：**
 
FabricClient对象
用来执行后续操作


#### 安装链码

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** installChaincode

**调用方式：** 通过client对象调用（client.installChaincode()）

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
location | 是 | File | new File("src/test") 传入链码所在文件夹路径


**返回：** 
空，该方法就是将go语言写的链码文件安装在区块链服务器的各个节点上


#### 初始化链码

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** instantiationChaincode

**调用方式：** 通过client对象调用（client.instantiationChaincode()）

**参数：无**


**返回：**

空，该方法需在安装链码后立马调用，初始化链码将链码运行起来


### 链码相关
#### 查询

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** query

**调用方式：** 通过client对象调用（client.query()）

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
fcn | 是 | 字符串 | 需要查询的链码中的方法名称
parser | 是 | PayloadParser<T> | 可以将返回内容反序列化成需要的对象
args | 是 | Object...变长参数 | 需要查询的链码中的方法所需要的参数

**返回：**

 通过PayloadParser反序列化成自定义的对应链码返回的数据的对象
 
 
#### 调用

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** invoke

**调用方式：** 通过client对象调用（client.invoke()）

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
fcn | 是 | 字符串 | 需要调用的链码中的方法名称
args | 是 | Object...变长参数 | 需要调用的链码中的方法所需要的参数


**返回：**

 空，如果节点返回数据不一致 或者 有节点操作失败，会甩异常
 
 
#### 列举所有节点的所有链码

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** allChainCode

**调用方式：** 通过client对象调用（client.allChainCode()）

**参数：无**


**返回：**

 一个列表，List<QueryChaincodeResponse>；
 其中QueryChaincodeResponse对象包括节点名称和对应的List<ChaincodeInfo> chaincodes信息；
 其中，ChaincodeInfo对象包括链码名称、链码版本、链码路径。
 
 
 
### 区块相关
#### 根据区块号查询区块信息

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** queryBlockByNumber

**调用方式：** 通过client对象调用（client.queryBlockByNumber()）

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
number | 是 | long | 区块号

**返回：**

一个Block对象，其中包括：

成员 | 注释
----| ----
String channelId | 通道ID
long blockNumber | 区块号
String dataHash | 数据哈希值
String previousHash | 前一个区块哈希值
String hash | 当前区块哈希值
int envelopeCount | 交易数
List<Envelope> envelopes | 交易信息
	
其中，Envelope对象包括：

成员 | 注释
----| ----
String id | 交易ID
String timestamp | 交易时间戳
long epoch |  
	
若未找到改区块，返回null


#### 根据区块哈希查询区块信息

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** queryBlockByHash

**调用方式：** 通过client对象调用（client.queryBlockByHash()）

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
hash | 是 | String | 区块哈希值

**返回：**

 一个Block对象，其中包括：
 
成员 | 注释
----| ----
String channelId | 通道ID
long blockNumber | 区块号
String dataHash | 数据哈希值
String previousHash | 前一个区块哈希值
String hash | 当前区块哈希值
int envelopeCount | 交易数
List<Envelope> envelopes | 交易信息
	
其中，Envelope对象包括：

成员 | 注释
----| ----
String id | 交易ID
String timestamp | 交易时间戳
long epoch |  
	
若未找到改区块，返回null


#### 根据交易ID查询区块信息

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** queryBlockByTransactionId

**调用方式：** 通过client对象调用（client.queryBlockByTransactionId()）

**参数：**

参数名 | 是否必须 | 类型 | 说明
----| ---- | ---- | ----
transactionId | 是 | String | 交易ID

**返回：**

 一个Block对象，其中包括：

成员 | 注释
----| ----
String channelId | 通道ID
long blockNumber | 区块号
String dataHash | 数据哈希值
String previousHash | 前一个区块哈希值
String hash | 当前区块哈希值
int envelopeCount | 交易数
List<Envelope> envelopes | 交易信息
	
其中，Envelope对象包括：

成员 | 注释
----| ----
String id | 交易ID
String timestamp | 交易时间戳
long epoch |  
	
若未找到改区块，返回null



### 节点相关
#### 获取当前所有节点状态等信息

**创建版本号：** 1.0

**更新版本号：** 1.0

**方法名：** peerStatus

**调用方式：** 通过client对象调用（client.peerStatus()）

**参数：无**

**返回：**

 一个List<PeerStatus>列表，其中PeerStatus包括：
 
成员 | 注释
----| ----
String name | 节点名称
String url | 节点url
String status | 节点状态

节点的状态及详细信息。	