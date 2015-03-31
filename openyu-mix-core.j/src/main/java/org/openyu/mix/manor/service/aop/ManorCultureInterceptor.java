package org.openyu.mix.manor.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.manor.service.ManorLogService;
import org.openyu.mix.manor.service.ManorService;
import org.openyu.mix.manor.service.ManorService.CultureAllResult;
import org.openyu.mix.manor.service.ManorService.CultureResult;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.lang.ClassHelper;

/**
 * 莊園耕種攔截器
 */
public class ManorCultureInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ManorCultureInterceptor.class);

	@Autowired
	@Qualifier("manorLogService")
	protected transient ManorLogService manorLogService;

	/**
	 * 耕種
	 * 
	 * CultureResult culture(boolean sendable, Role role, int cultureValue, int
	 * farmIndex, int gridIndex, String seedUniqueId);
	 */
	private static final Method culture = ClassHelper.getDeclaredMethod(
			ManorService.class, "culture",
			new Class[] { boolean.class, Role.class, int.class, int.class,
					int.class, String.class });

	/**
	 * 耕種所有
	 * 
	 * CultureAllResult cultureAll(boolean sendable, Role role, int
	 * cultureValue);
	 */
	private static final Method cultureAll = ClassHelper.getDeclaredMethod(
			ManorService.class, "cultureAll", new Class[] { boolean.class,
				Role.class, int.class });

	public ManorCultureInterceptor() {
	}

	/**
	 * ManorService
	 * 
	 * CultureResult culture(boolean sendable, Role role, int cultureValue, int
	 * farmIndex, int gridIndex, String seedUniqueId);
	 */
	public Object invoke(MethodInvocation methodInvocation, Method method,
			Class<?>[] paramTypes, Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------
			if (method.equals(culture)) {
				// 傳回值
				CultureResult ret = (CultureResult) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				int cultureValue = (Integer) args[2];
				int farmIndex = (Integer) args[3];
				int gridIndex = (Integer) args[4];
				String seedUniqueId = (String) args[5];
				//
				if (ret != null) {
					manorLogService.recordCulture(role, ret.getCultureType(),
							farmIndex, gridIndex, ret.getSeed(),
							ret.getSpendItems(), ret.getSpendCoin());
				}

			} else if (method.equals(cultureAll)) {
				// 傳回值
				CultureAllResult ret = (CultureAllResult) result;
				// 參數
				boolean sendable = (Boolean) args[0];
				Role role = (Role) args[1];
				int cultureValue = (Integer) args[2];
				//
				if (ret != null) {
					// TODO 有分不同耕種類型
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}

}