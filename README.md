# GRpc 
简单的rpc框架，包含服务提供和消费端
目前使用map作为注册中心，通过netty实现Rpc通讯，使用Kryo作为序列化工具
无需配置，注解扫描
后期改成zookeeper注册方式
