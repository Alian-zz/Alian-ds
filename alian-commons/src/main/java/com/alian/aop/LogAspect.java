package com.alian.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
public class LogAspect {

    @Autowired
    private HttpServletRequest request;


    //@Pointcut("execution(* com.zzl.user.controller.*.*(..))")
    @Pointcut("@annotation(com.alian.aop.LogFilter)")//在所有标记指定注解的方法上拦截
    public void logPointCut() {

    }

    @Around("logPointCut()")
    //@Around("logPointCut() && @annotation(logsAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj;
        //Logs logs = new Logs();

        //获取类名
        String className = joinPoint.getSignature().getDeclaringTypeName();
        //获取目标方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("------------------------" + methodName + "-------------------------------");
     /*   logs.setClassName(className);
        logs.setMethodName(methodName);*/



        //System.out.println(DeviceUtil.getdevice(device));


        //获取目标方法里的参数
        //String args = JSON.toJSONString(joinPoint.getArgs());
        //logs.setArgs(args);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取方法参数的名称
        String[] parameterNames = signature.getParameterNames();
        System.out.println(JSON.toJSON(parameterNames));

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

        //System.out.println(JSON.toJSON(argList));输出参数名

  //      System.out.println(IpUtil.getIpAddr(IpUtil.getRequest()));//输出参数名

        LogFilter logFilter = method.getAnnotation(LogFilter.class);
        String value = logFilter.value();
     //logs.setDescribeInfo(logFilter.value());
       // logs.setCreateTime(new Date());
        Long startTime = System.currentTimeMillis();
        try {

           // logs.setType((short) 1);
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
       //     logs.setType((short) 2);
            throwable.printStackTrace();
      //      logs.setErrorInfo(exception(throwable));
            throw throwable;
        } finally {
            /*TSysUser user = SubjectUtil.getSubject();
            logs.setUserId(user.getId());
            logs.setRealName(user.getRealName());*/
     //       logs.setRunTime(System.currentTimeMillis() - startTime);
            /* logsService.addLogs(logs);*/

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
