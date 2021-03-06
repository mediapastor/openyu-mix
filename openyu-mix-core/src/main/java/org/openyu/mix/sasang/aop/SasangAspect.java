package org.openyu.mix.sasang.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.sasang.service.SasangLogService;
import org.openyu.mix.sasang.service.SasangService.PlayResult;
import org.openyu.mix.sasang.service.SasangService.PutResult;
import org.openyu.mix.sasang.service.SasangService.PutType;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class SasangAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(SasangAspect.class);

	@Autowired
	@Qualifier("sasangLogService")
	private transient SasangLogService sasangLogService;

	public SasangAspect() {

	}

	/**
	 * 玩四象
	 * 
	 * SasangService
	 * 
	 * PlayResult play(boolean sendable, Role role, int playValue)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.sasang.service.SasangService.play(..))", returning = "result")
	public void play(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int playValue = (Integer) args[2];
			//
			PlayResult returnValue = (PlayResult) result;
			if (returnValue != null) {
				// 記錄玩的
				sasangLogService.recordPlay(role, returnValue.getPlayType(), returnValue.getPlayTime(),
						returnValue.getOutcome(), returnValue.getTotalTimes(), returnValue.getSpendGold(),
						returnValue.getSpendItems(), returnValue.getSpendCoin());

				// 記錄開出的結果,有成名的
				sasangLogService.recordFamous(role, returnValue.getPlayType(), returnValue.getPlayTime(),
						returnValue.getOutcomes());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during play()").toString(), e);
		}
	}

	/**
	 * 單擊獎勵放入包包
	 * 
	 * SasangService
	 * 
	 * PutResult putOne(boolean sendable, Role role, String itemId, int amount)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.sasang.service.SasangService.putOne(..))", returning = "result")
	public void putOne(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String itemId = (String) args[2];
			int amount = (Integer) args[3];
			//
			PutResult returnValue = (PutResult) result;
			//
			if (returnValue != null) {
				sasangLogService.recordPut(role, PutType.ONE, returnValue.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during putOne()").toString(), e);
		}
	}

	/**
	 * 所有中獎區獎勵放入包包
	 * 
	 * SasangService
	 * 
	 * PutResult putAll(boolean sendable, Role role)
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.sasang.service.SasangService.putAll(..))", returning = "result")
	public void putAll(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			//
			PutResult returnValue = (PutResult) result;
			//
			if (returnValue != null) {
				sasangLogService.recordPut(role, PutType.ALL, returnValue.getAwards());
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during putAll()").toString(), e);
		}
	}
}
