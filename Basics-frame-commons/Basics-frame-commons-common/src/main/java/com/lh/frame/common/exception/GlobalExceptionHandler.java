package com.lh.frame.common.exception;


import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 全局异常处理器
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 认证异常
     */
    @ExceptionHandler(AuthException.class)
    public Result<String > authExceptionHandler(AuthException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生认证异常.", requestURI, e);
        return ResultBuild.failed(e.getMessage());
    }
    /**
     * 系统异常
     *
     */
    @ExceptionHandler(SystemException.class)
    public Result<String > systemExceptionHandler(SystemException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',系统异常.", requestURI, e);
        return ResultBuild.failed(e.getMessage());
    }
    /**
     * 参数校验异常
     */
    @ExceptionHandler(ValidationException.class)
    public Result<String > paramExceptionHandler(ValidationException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',参数校验异常.", requestURI, e);
        return ResultBuild.failed(e.getMessage());
    }
    /**
     *业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result<String > serviceExceptionHandler(ServiceException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',业务异常.", requestURI, e);
        return ResultBuild.failed(e.getMessage());
    }

    /**
     *文件操作
     */
    @ExceptionHandler(FileException.class)
    public Result<String > serviceExceptionHandler(FileException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',文件操作异常.", requestURI, e);
        return ResultBuild.failed(e.getMessage());
    }
    /**
     * 缓存异常
     */
    @ExceptionHandler(CacheException.class)
    public Result<String > cacheExceptionHandler(CacheException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',缓存操作异常.", requestURI, e);
        return ResultBuild.failed(e.getMessage());
    }
    /**
     * 运行时异常
     */
     @ExceptionHandler(RuntimeException.class)
    public Result<String > runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
         String requestURI = request.getRequestURI();
         log.error("请求地址'{}',运行时异常.", requestURI, e);
         return ResultBuild.failed(e.getMessage());
     }
}
