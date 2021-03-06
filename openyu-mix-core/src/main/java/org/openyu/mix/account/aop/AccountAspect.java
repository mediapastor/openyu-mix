package org.openyu.mix.account.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.openyu.mix.account.service.AccountLogService;
import org.openyu.mix.account.service.AccountService.ActionType;
import org.openyu.mix.account.service.AccountService.CoinType;
import org.openyu.mix.app.aop.supporter.AppAspectSupporter;
import org.openyu.mix.role.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Aspect
public class AccountAspect extends AppAspectSupporter {

	private static final long serialVersionUID = 2524265554035630063L;

	private static transient final Logger LOGGER = LoggerFactory.getLogger(AccountAspect.class);

	@Autowired
	@Qualifier("accountLogService")
	private transient AccountLogService accountLogService;

	public AccountAspect() {

	}

	/**
	 * 增加帳戶的儲值幣
	 * 
	 * AccountService
	 * 
	 * int increaseCoin(boolean sendable, String accountId, Role role, int coin,
	 * boolean accuable, CoinReason coinReason);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.account.service.AccountService.increaseCoin(..))", returning = "result")
	public void increaseCoin(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			int coin = (Integer) args[3];
			boolean accuable = (Boolean) args[4];
			CoinType coinReason = (CoinType) args[5];
			//
			int returnValue = safeGet((Integer) result);
			if (returnValue != 0 && coinReason != null) {
				accountLogService.recordIncreaseCoin(accountId, role, returnValue, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during increaseCoin()").toString(), e);
		}
	}

	/**
	 * 減少帳戶的儲值幣
	 * 
	 * AccountService
	 * 
	 * int decreaseCoin(boolean sendable, String accountId, Role role, int coin,
	 * CoinReason coinReason);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.account.service.AccountService.decreaseCoin(..))", returning = "result")
	public void decreaseCoin(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			int coin = (Integer) args[3];
			CoinType coinReason = (CoinType) args[4];
			//
			int returnValue = safeGet((Integer) result);
			if (returnValue != 0 && coinReason != null) {
				accountLogService.recordDecreaseCoin(accountId, role, returnValue, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during decreaseCoin()").toString(), e);
		}
	}

	/**
	 * 增減帳戶的儲值幣
	 * 
	 * AccountService
	 * 
	 * int changeCoin(boolean sendable, String accountId, Role role, int coin,
	 * boolean accuable, CoinAction coinAction, CoinReason coinReason);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.account.service.AccountService.changeCoin(..))", returning = "result")
	public void changeCoin(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			int coin = (Integer) args[3];
			boolean accuable = (Boolean) args[4];
			ActionType coinAction = (ActionType) args[5];
			CoinType coinReason = (CoinType) args[6];
			//
			int returnValue = safeGet((Integer) result);
			if (returnValue != 0 && coinAction != null && coinReason != null) {
				accountLogService.recordChangeCoin(accountId, role, returnValue, coinAction, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during changeCoin()").toString(), e);
		}
	}

	/**
	 * 重置帳戶儲值幣
	 * 
	 * AccountService
	 * 
	 * boolean resetCoin(boolean sendable, String accountId, Role role, boolean
	 * accuable, CoinReason coinReason);
	 */
	@AfterReturning(pointcut = "execution(public * org.openyu.mix.account.service.AccountService.resetCoin(..))", returning = "result")
	public void resetCoin(JoinPoint joinPoint, Object result) throws Throwable {
		try {
			String method = joinPoint.getSignature().getName();
			// 參數
			Object[] args = joinPoint.getArgs();
			boolean sendable = (Boolean) args[0];
			String accountId = (String) args[1];
			Role role = (Role) args[2];
			boolean accuable = (Boolean) args[3];
			CoinType coinReason = (CoinType) args[4];
			//
			boolean returnValue = safeGet((Boolean) result);
			if (returnValue && coinReason != null) {
				accountLogService.recordChangeCoin(accountId, role, 0, ActionType.RESET, coinReason);
			}
		} catch (Throwable e) {
			LOGGER.error(new StringBuilder("Exception encountered during resetCoin()").toString(), e);
		}
	}
}
