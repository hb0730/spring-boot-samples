# Spring Event 事件机制
Spring的事件为Bean与Bean之间的消息通信提供了支持，当一个Bean处理完一个任务之后，希望另外一个Bean知道并能做相应的处理，这时我们就需要让一个Bean监听当前Bean所发送的事件。
## 　一般Spring的事件需要遵循如下流程：
 * 1, 自定义事件，集成ApplicationEvent。
 * 2, 定义事件监听器，实现ApplicationListener。
 * 3, 使用容器发布事件。
## springboot进行事件监听有四种方式：
 * 1.手工向ApplicationContext中添加监听器
 * 2.将监听器装载入spring容器
 * 3.在application.properties中配置监听器
 * 4.通过@EventListener注解实现事件监听