package com.lh.frame.common.aspect;

import com.alibaba.fastjson.JSONObject;

import com.lh.frame.common.anno.SensitiveResponse;
import com.lh.frame.common.utils.EmptyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @ClassName SensitiveAspect.java
 * @Description TODO
 */
@Aspect
@Component
public class SensitiveAspect {

    @Around(value = "@annotation(sensitiveResponse)")
    public Object select(ProceedingJoinPoint joinPoint, SensitiveResponse sensitiveResponse) throws Throwable {
        // 执行目标方法
        Object result = joinPoint.proceed();
        // 获取目标方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取目标方法的泛型返回类型
        Type genericReturnType = method.getGenericReturnType();
        if (!EmptyUtil.isNullOrEmpty(result)) {
            // 进行脱敏处理
            String jsonResponse = JSONObject.toJSONString(result);
            String maskedResponse = maskSensitiveData(jsonResponse);
            // 将脱敏后的数据转换为实际返回类型
            return JSONObject.parseObject(maskedResponse, genericReturnType);
        }
        return result;
    }


    /**
     * 隐藏敏感数据的函数。
     * 该方法接收一个包含敏感信息的JSON字符串，然后通过正则表达式替换的方式，
     * 对身份证号码、姓名、银行卡号、银行名称和电话号码进行部分隐藏，以保护敏感信息。
     *
     * @param jsonData 待处理的JSON字符串，预期包含身份信息、姓名、银行卡号、银行名称和电话号码等敏感信息。
     * @return 处理后的JSON字符串，敏感信息部分被隐藏。
     */
    private String maskSensitiveData(String jsonData) {
        // 替换身份证号码，只显示第一个数字和最后两个数字，中间部分用星号隐藏
        return jsonData
                // 替换身份证号码
                .replaceAll("\"identityCard\"\\s*:\\s*\"(\\d{1})(\\d{15})(\\d{2})\"", "\"identityCard\":\"$1**************$3\"")
                // 替换真实姓名
                .replaceAll("\"nickName\"\\s*:\\s*\"(.).+?\"", "\"nickName\":\"$1*\"")
                // 替换银行卡号
                .replaceAll("\"bankCardNo\"\\s*:\\s*\"(.).+?\"", "\"bankCardNo\":\"$1*\"")
                // 替换电话号码
                .replaceAll("\"phonenumber\"\\s*:\\s*\"(\\d{3})(\\d{4})(\\d{4})\"", "\"phonenumber\":\"$1****$3\"");
    }

}
