package com.zto56.freight.common.expand.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.zto56.freight.common.core.enums.ApiCodeEnum;
import com.zto56.freight.common.core.exception.BizException;
import com.zto56.freight.common.core.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张庆福
 * @description 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义业务异常,状态是200,由前端自行处理
     * 使用自定义业务异常时,直接在代码中throw new BizException();
     * @param e {@link BizException}
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse<?> handleBizException(BizException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}自定义业务异常 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.customFailed(e.getMessage());
    }

    /**
     * 非法参数异常
     * @param e IllegalArgumentException
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse<?> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}非法参数异常 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.PARAMETER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 参数校验失败(POST)
     * @param e MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        // 将所有的错误提示使用";"拼接起来并返回
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
        log.error("url:{}参数校验失败 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.PARAMETER_ERROR.getCode(), errorMsg);
    }

    /**
     * 任意方法的参数校验
     * https://github.com/fashionbrot/mars-validated
     * @param e ValidatedException
     */
    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse<?> handleValidatedException(ValidatedException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        List<MarsViolation> violations = e.getViolations();
        String msg = e.getMsg();
        if (ObjectUtil.isNotEmpty(violations)) {
            if (violations.size() == 1) {
                msg = violations.get(0).getMsg();
            } else {
                msg = violations.stream().map(MarsViolation::getMsg).collect(Collectors.joining(","));
            }
        }
        log.error("url:{}参数校验失败 msg:{} e:{}", url, msg, Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.PARAMETER_ERROR.getCode(), msg);
    }

    /**
     * 参数校验失败(GET)
     * @param e ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse<?> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        String errorMsg = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        log.error("url:{}参数校验失败 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.PARAMETER_ERROR.getCode(), errorMsg);
    }

    /**
     * 参数绑定异常
     * @param e BindException
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResponse<?> handleBindException(BindException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(";"));
        log.error("url:{}参数校验失败 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.PARAMETER_ERROR.getCode(), errorMsg);
    }


    /**
     * 空指针异常
     * @param e NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}空指针异常 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.SYSTEM_ERROR.getCode(), "空指针");
    }

    /**
     * 缺少服务器请求参数异常
     * @param e MissingServletRequestParameterException
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}缺少必要的请求参数 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.PARAMETER_ERROR.getCode(), e.getMessage());
    }

    /**
     * Json序列化失败
     * @param e HttpMessageNotReadableException
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}JSON序列化失败 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.BAD_REQUEST.getCode(), ApiCodeEnum.BAD_REQUEST.getMsg());
    }

    /**
     * 接口不存在
     * 此异常未能成功捕获
     * @param e NoHandlerFoundException
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BaseResponse<?> handlerNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}资源不存在 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.NOT_FOUND.getCode(), ApiCodeEnum.NOT_FOUND.getMsg());
    }

    /**
     * 不支持的Method
     * @param e HttpRequestMethodNotSupportedException
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseResponse<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}不支持:{}请求类型 msg:{} e:{}", url, e.getMethod(), e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.METHOD_NOT_ALLOWED.getCode(), StrUtil.format("不支持:{}请求", e.getMethod()));
    }

    /**
     * Rpc异常
     * @param e RpcException
     */
    @ExceptionHandler(value = RpcException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleRpcException(RpcException e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}Rpc异常 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }


    /**
     * 其他系统预期以外的异常都在此处理
     * @param e Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleUnexpectedException(Exception e, HttpServletRequest request) {
        String url = request.getRequestURI();
        log.error("url:{}系统发生异常 msg:{} e:{}", url, e.getMessage(), Arrays.toString(e.getStackTrace()));
        return BaseResponse.failed(ApiCodeEnum.SYSTEM_ERROR.getCode(), ApiCodeEnum.SYSTEM_ERROR.getMsg());
    }


}