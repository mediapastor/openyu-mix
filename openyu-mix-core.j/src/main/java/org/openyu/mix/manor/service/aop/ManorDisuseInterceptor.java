package org.openyu.mix.manor.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService.DisuseResult;
import org.openyu.mix.role.vo.Role;

/**
 * 莊園休耕攔截器
 */
public class ManorDisuseInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorDisuseInterceptor.class);

	@Autowired
	@Qualifier("manorLogService")
	protected transient ManorLogService manorLogService;

	public ManorDisuseInterceptor() {
	}

	/**
	 * ManorService
	 * 
	 * DisuseResult disuse(boolean sendable, Role role, int farmIndex);
	 */
	public Object invoke(MethodInvocation methodInvocation, Method method,
			Class<?>[] paramTypes, Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int farmIndex = (Integer) args[2];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			DisuseResult ret = (DisuseResult) result;
			//
			if (ret != null) {
				manorLogService.recordDisuse(role, ret.getFarmIndex(),
						ret.getLand(), ret.getSpendGold());
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}

}