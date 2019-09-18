package cn.htool.aspectjrt;

import cn.htool.annotation.ParamsNotNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     Params aop拦截
 * </p>
 *
 * @author bing_huang
 * @version V1.0
 * @date 2019/9/17 11:07
 */
@Component
@Aspect
@Order(0)
public class ParamsAop {
    @Pointcut("@annotation(cn.htool.annotation.ParamsNotNull)")
    public void annotationParamsNotNull() {
    }
    /**
     * 参数不为空
     * paramsNotNull
     *
     * @param point
     */
    @Before("annotationParamsNotNull()")
    public void nullFilter(JoinPoint point) throws ClassNotFoundException, NoSuchMethodException {
        // 参数值
        List<Object> args = Arrays.asList(point.getArgs());
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) point.getSignature();
        //方法
        Method method = signature.getMethod();
        //参数名
        List<String> paramsNames = Arrays.asList(pnd.getParameterNames(method));

        // 注解
        ParamsNotNull annotation = method.getAnnotation(ParamsNotNull.class);
        List<String> annotationNames = Arrays.asList(annotation.name());
        List<String> messages = Arrays.asList(annotation.message());
        annotationNames.stream().forEach(annotationName->{
            for (int i = 0; i < paramsNames.size(); i++) {
                if (paramsNames.get(i).equals(annotationName)) {
                    Object o = args.get(i);
                    if (StringUtils.isEmpty(o)) {
                        throw new IllegalArgumentException(messages.get(i));
                    }
                }
            }
        });
    }
}
