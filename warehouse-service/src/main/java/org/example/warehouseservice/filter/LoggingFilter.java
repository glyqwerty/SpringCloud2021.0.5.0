package org.example.warehouseservice.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dubbo拦截器实现
 * 除了通过 @Activate 注解自动激活外，你还可以在配置文件或代码中动态地开启或关闭Filter。
 *
 * YAML方式开启：在 application.yml 中，通过 filter 属性可以全局开启（加号 +）或全局关闭（减号 -）Filter。
 *
 * yaml
 * dubbo:
 *   provider:
 *     filter: "loggingFilter,tpsFilter" # 为所有服务提供者开启
 *   consumer:
 *     filter: "-accesslog" # 为所有服务消费者关闭
 * 注解方式配置：使用 @DubboService(filter="loggingFilter") 为某个具体服务开启，或用 @DubboReference(filter="-loggingFilter") 为某个引用关闭
 */
@Activate(group = CommonConstants.PROVIDER, order = 1)
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.currentTimeMillis();
        String methodName = invocation.getMethodName();
        Object[] arguments = invocation.getArguments();

        logger.info("===> Dubbo 调用开始: 方法={}, 参数={}", methodName, arguments);

        try {
            Result result = invoker.invoke(invocation);
            long elapsed = System.currentTimeMillis() - startTime;
            logger.info("<=== Dubbo 调用结束: 方法={}, 耗时={}ms, 结果={}", methodName, elapsed, result.getValue());
            return result;
        } catch (RpcException e) {
            long elapsed = System.currentTimeMillis() - startTime;
            logger.error("<=== Dubbo 调用异常: 方法={}, 耗时={}ms, 异常信息={}", methodName, elapsed, e.getMessage(), e);
            throw e;
        }
    }
}