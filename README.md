### 注意事项
1. 可在resource文件夹下的config.properties文件中修改相关配置
2. resource文件夹下text.txt是用来训练自学习模型的样本文件，但是在demo中提供的accessKey不支持自学习模型，如有测试需要，可以联系商务


### 使用方法
可以使用maven将项目编译好，编译完成会产生一个tar.gz的压缩包，这个压缩包中包含了需要运行的程序及配置文件等，以下是运行命令

1. 多线程版并发测试
    > java -classpath asr-client-demo-1.0.2-RELEASE.jar com.yiwise.asr.demo.MultiThreadAsrDemoBootstrap
2. 单线程版测试
    > java -classpath asr-client-demo-1.0.2-RELEASE.jar com.yiwise.asr.demo.SingleThreadAsrDemoBootstrap


也可在IDE上直接运行MultiThreadAsrDemoBootstrap.class、SingleThreadAsrDemoBootstrap.class等项目入口，开启识别