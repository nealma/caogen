package com.caogen.core.exception;

import com.caogen.core.web.MsgOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 全局异常处理类
 * Created by neal on 9/22/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({IOException.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOGGER.error("[error] --> {}", e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler({AccessDeniedException.class, AppException.class, BindException.class})
    @ResponseBody
    public MsgOut json(Exception ex) {
        //TODO:记录日志
        LOGGER.error("AppException", ex);

        return MsgOut.error(ex);
    }
}
