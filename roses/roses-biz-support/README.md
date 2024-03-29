# roses-biz-support 基础业务服务支撑

---
   
## 介绍
本项目为Roses系列微服务框架的模块之一，Roses基于`Spring Boot 2`和`Spring Cloud Finchley.RELEASE`，致力做更简洁的**分布式**和**服务化**解决方案，Roses拥有高效率的开发体验，提供可靠消息最终一致性分布式事务解决方案，提供基于调用链的服务治理，提供可靠的服务异常定位方案（Log + Trace）等等，**一个分布式框架不仅需要构建高效稳定的底层开发框架，更需要解决分布式带来的种种挑战**，请关注Roses微服务框架[https://gitee.com/naan1993/roses](https://gitee.com/naan1993/roses)

---

## 本项目模块介绍

| 模块名称 | 说明 | 端口 | 备注 |
| :---: | :---: | :---: | :---: |
| modular-dict | 字典模块 | 无 | 包含字典实际业务和字典的接口，灵活可拆卸 |
| modular-file | 文件模块 | 无 | 包含文件实际业务和字典的接口，灵活可拆卸 |
| modular-log | 日志模块 | 无 | 包含日志实际业务和字典的接口，灵活可拆卸 |


---

## 注意事项

> * 开发环境为jdk 1.8
> * maven推荐使用阿里云镜像，拉取jar包保证成功
> * 导入本模块后请执行`mvn clean install -Dmaven.test.skip=true`或者`deploy`本jar包到maven私服上

---

## 项目特点

基础业务服务支撑，包含文件，字典和日志，使用时，可以以jar包的方式供给别的服务使用，亦可以让业务服务只集成api包，通过编写Consumer
直接远程调用服务的基础服务。这样的话，既可以让每个业务服务直接集成这三块业务，也可以让让业务服务不集成基本服务，直接通过接口来调用远程的基本服务。

另外，每个业务模块包含了数据库初始化器DbInitializer
，当你引入不管是哪个业务模块，程序启动都会自动初始化表结构（如果数据库中没有这些表的话），并且框架还会校验数据库对应的实体是否和数据库表结构保持一致，如果不一致，则程序启动的时候会报错。数据库初始化器一般在`com.stylefeng
.roses.biz.xxx.core.db`包中。

DbInitializer的原理请看com.stylefeng.roses.core.db.DbInitializer和com.stylefeng.roses.core.db.listener.InitTableListener这两个类。
