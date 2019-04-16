package com.tools.http;

import com.tools.json.JSONUtils;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

	/**
	 *
	 * 功能描述: 获取切入点参数信息
	 *
	 * @param:
	 * @return:
	 * @auther: yin_q
	 * @date: 2019/4/16 17:09
	 */
	public static Map<String, Object> getJoinPointInfoMap(JoinPoint joinPoint){
		
		Map<String, Object> joinPointInfo = new HashMap<String, Object>();
		
		// 方法类路径
		String classPath = joinPoint.getTarget().getClass().getName();
		// 方法名
		String methodName = joinPoint.getSignature().getName();
		joinPointInfo.put("classPath", classPath);
		joinPointInfo.put("methodName", methodName);
		
		Class<?> clazz = null;
		CtMethod ctMethod = null;
		LocalVariableAttribute attr = null;
		
		int length = 0;
		int pos = 0;
		
		try {
			clazz = Class.forName(classPath);
			String clazzName = clazz.getName();
			ClassPool pool  = ClassPool.getDefault();
			ClassClassPath classClassPath = new ClassClassPath(clazz);
			pool.insertClassPath(classClassPath);
			CtClass ctClass = pool.get(clazzName);
			ctMethod = ctClass.getDeclaredMethod(methodName);
			MethodInfo methodInfo = ctMethod.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
			if(attr == null){
				return joinPointInfo;
			}
			length = ctMethod.getParameterTypes().length;
			pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取参数和参数值
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Object[] paramsArgsValues = joinPoint.getArgs();
		String[] paramsArgsNames = new String[length];
		for(int i = 0; i < length; i++){
			paramsArgsNames[i] = attr.variableName(i+pos);
			String paramsArgsName = attr.variableName(i + pos);
			if(paramsArgsName.equalsIgnoreCase("request") || paramsArgsName.equalsIgnoreCase("response") || paramsArgsName.equalsIgnoreCase("session")){
				break;
			}
			Object paramsArgsValue = paramsArgsValues[i];
			paramMap.put(paramsArgsName, paramsArgsValue);
		}
		joinPointInfo.put("paramMap", JSONUtils.obj2json(paramMap));
		return joinPointInfo;
	}

	/**
	 *
	 * 功能描述: 获取IP
	 *
	 * @param:
	 * @return:
	 * @auther: yin_q
	 * @date: 2019/4/16 17:09
	 */
	public static String getIp(HttpServletRequest request){
		if(request == null){
			return "HttpServletRequest对象为空";
		}
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
	}
	
	/**
	 *
	 * 功能描述: 获取请求方式：传统请求(同步)  AJAX请求(异步)
	 *
	 * @param:
	 * @return:
	 * @auther: yin_q
	 * @date: 2019/4/16 17:11
	 */
	public static Integer getRequestType(HttpServletRequest request){
		if(request == null){
			return -1;
		}
		String xRequestWith = request.getHeader("X-Requested-With");
		return xRequestWith == null ? 1 : 2;
	}

}
