package com.alian.config.aop;

import com.alian.config.LogFilter;
import com.alian.ums.entity.SystemLogs;
import com.alian.ums.repository.LogsRepository;
import com.alian.utils.IpUtil;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Aspect
public class LogAspect {

    @Resource
    private LogsRepository logsRepository;

    @Resource
    private HttpServletRequest request;

    /**
     * 在所有标记指定注解的方法上拦截
     */
    @Pointcut("@annotation(com.alian.config.LogFilter)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj;
        SystemLogs systemLogs = new SystemLogs();

        //获取类名
        String className = joinPoint.getSignature().getDeclaringTypeName();
        //获取目标方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("------------------------" + methodName + "-------------------------------");
        systemLogs.setClassName(className);
        systemLogs.setMethodName(methodName);

        /**
         * 获取设备类型
         */
       /* System.out.println(DeviceUtil.getdevice(device));
        systemLogs.setDeviceName(DeviceUtil.getdevice(device));*/

        /**
         * 获取目标方法里的参数
         */
        /*String args = JSON.toJSONString(joinPoint.getArgs());
        systemLogs.setArgsName(args);*/

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取方法参数的名称
        String[] parameterNames = signature.getParameterNames();
        System.out.println("参数:"+JSON.toJSON(parameterNames));

        //循环参数
        List<Object> argList = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            // request/response无法使用toJSON
            if (arg instanceof HttpServletRequest) {
                argList.add("request");
            } else if (arg instanceof HttpServletResponse) {
                argList.add("response");
            } else {
                argList.add(JSON.toJSON(arg));
            }
        }
        //System.out.println("参数列表"+JSON.toJSON(argList));
        systemLogs.setArgsName(argList);

        /**
         *
         */

        String ip = request.getHeader("Authorization-ip");
        systemLogs.setIpAddr(ip);

        System.out.println(IpUtil.getIpAddr(IpUtil.getRequest()));

        //LogFilter logFilter = method.getAnnotation(LogFilter.class);
        //String value = logFilter.value();

        LogFilter logFilter = method.getAnnotation(LogFilter.class);
        systemLogs.setDescribeInfo(logFilter.value());
        systemLogs.setCreateTime(new Date());
        Long startTime = System.currentTimeMillis();
        try {
            //如果运行正常
            systemLogs.setType((short) 1);
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            //运行不正常
            systemLogs.setType((short) 2);
            throwable.printStackTrace();
            systemLogs.setErrorInfo(exception(throwable));
            throw throwable;
        } finally {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


            systemLogs.setRunTime(System.currentTimeMillis() - startTime);
            logsRepository.insert(systemLogs);

        }

        return obj;

    }

    /**
     * 将异常信息转化成字符串
     *
     * @param
     * @return
     * @throws IOException
     */
    private static String exception(Throwable t) throws IOException {
        if (t == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            t.printStackTrace(new PrintStream(baos));
        } finally {
            baos.close();
        }
        return baos.toString();
    }


}
