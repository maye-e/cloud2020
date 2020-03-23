####公共实体
* cloud-api-commons
* 本机 host 配置
####Eureka注册中心(服务调用是restTemplate)、Ribbon轮询规则
* cloud-eureka-server7001
* cloud-eureka-server7002
* cloud-provider-payment8001
* cloud-provider-payment8002
* cloud-consumer-order80
####zookepper注册中心
* 注册中心服务在腾讯云
* cloud-provider-payment8004
* cloud-consumerzk-order80
####Consul注册中心
* 注册中心服务在本地
* cloud-providerconsul-payment8006
* cloud-consumerconsul-order80
####OpenFeign服务调用及超时,日志
* cloud-eureka-server7001
* cloud-eureka-server7002
* cloud-provider-payment8001
* cloud-provider-payment8002
* cloud-consumer-feign-order80
####Hystrix服务熔断,降级,监控
* cloud-eureka-server7001
* cloud-eureka-server7002
* cloud-provider-hystrix-payment8001
* cloud-consumer-feign-hystrix-order80
* cloud-consumer-hystrix-dashboard9001
####Gateway网关
* cloud-eureka-server7001
* cloud-eureka-server7002
* cloud-provider-payment8001
* cloud-provider-payment8002
* cloud-gateway-gateway9527
####config配置中心
* cloud-eureka-server7001
* cloud-eureka-server7002
* cloud-config-center-3344
* cloud-config-client-3355
