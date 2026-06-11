//package org.example.billservice.demos.exception;
//
//import com.example.common.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.validation.ConstraintViolationException;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    /**
//     * 处理业务逻辑异常
//     */
//    @ExceptionHandler(IllegalArgumentException.class)
//    public Result<Void> handleIllegalArgument(IllegalArgumentException e) {
//        log.warn("业务参数异常: {}", e.getMessage());
//        return Result.fail(400, e.getMessage());
//    }
//
//    /**
//     * 处理参数校验异常 (@Valid)
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
//        // 获取第一个校验错误信息
//        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
//        log.warn("参数校验失败: {}", msg);
//        return Result.fail(400, msg);
//    }
//
//    /**
//     * 处理未知异常 (兜底)
//     */
//    @ExceptionHandler(Exception.class)
//    public Result<Void> handleException(Exception e) {
//        log.error("系统内部异常: ", e); // 生产环境注意脱敏，不要打印敏感数据
//        return Result.fail(500, "系统繁忙，请稍后重试");
//    }
//}