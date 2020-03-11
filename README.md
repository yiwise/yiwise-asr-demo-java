### 注意事项
1. 可在resource文件夹下的config.properties文件中修改相关配置；
2. resource文件夹下text.txt是用来训练自学习模型的样本文件，但是在demo中提供的accessKey不支持自学习模型，如有测试需要，可以联系商务；
3. 本Demo为Java客户端的Demo，内部包含了java的SDK，可使用SDK进行快速开发。由于服务端采用的是标准的HTTP+WebSocket进行交互，其它语言可参照**客户端服务端交互文档**进行对接开发。


### SDK的Maven地址
```xml
<dependency>
    <groupId>com.yiwise</groupId>
    <artifactId>asr-client-sdk</artifactId>
    <version>1.0.8-RELEASE</version>
</dependency>
```

### 客户端服务端交互文档
* [一知ASR实时语音识别使用说明文档](https://www.yuque.com/docs/share/d02243d2-c24e-4268-a7a8-3e1e090c4e03?#)
* [一知ASR录音文件识别使用说明文档](https://www.yuque.com/docs/share/a131e157-191b-4347-823c-c0ec1a515820?#)

### 实时流式语音识别使用方法
本demo所提供的账号最大支持3个并发
本demo在调用SDK使用实时语音识别功能时，采用了按用户实际说话语速读取文件中的语音二进制流，来模拟真实说话的流式场景。
可以使用maven将项目编译好，编译完成会产生一个tar.gz的压缩包，这个压缩包中包含了需要运行的程序及配置文件等，以下是运行命令。

1. 多线程版并发测试
    > java -classpath asr-client-demo-1.0.7-RELEASE.jar com.yiwise.asr.demo.MultiThreadAsrDemoBootstrap
2. 单线程版测试
    > java -classpath asr-client-demo-1.0.7-RELEASE.jar com.yiwise.asr.demo.SingleThreadAsrDemoBootstrap


也可在IDE上直接运行MultiThreadAsrDemoBootstrap.class、SingleThreadAsrDemoBootstrap.class等项目入口，开启识别

### 文件语音识别方法
本demo所提供账号每天最大支持2个小时的音频文件识别，可以在config.properties中修改需要识别的文件，如果是双声道文件，请以 ```xx_双声道.wav``` 命名音频文件。
运行项目中的 com.yiwise.asr.demo.recognizer.file.FileRecognizerDemoBootstrap 提交文件识别请求，提交后请记住提交后返回的fileRecognizerTaskId。
运行项目中的 com.yiwise.asr.demo.recognizer.file.QueryFileRecognizerResultDemoBootstrap 并修改此文件中的 fileRecognizerTaskId 为上次提交后返回的id进行查询，查询识别可能还未结束，可多次查询直至查询到最后识别结果。

