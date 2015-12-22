import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.openyu.mix.app.dao.AppLogDao;
import org.openyu.mix.app.service.AppLogService;
import org.openyu.commons.dao.supporter.CommonDaoSupporter;
import org.openyu.commons.junit.supporter.BaseTestSupporter;
import org.openyu.commons.service.AsyncService;
import org.openyu.commons.service.LogService;
import org.openyu.commons.thread.ThreadHelper;

public class ApplicationContextDatabaseLogTest extends BaseTestSupporter {

	private static ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext(new String[] { //
				"applicationContext-init.xml", //
				"applicationContext-bean.xml", //
				"applicationContext-acceptor.xml", //
				"applicationContext-database-log.xml",//
		});
	}

	@Test
	public void logDataSource() throws Exception {
		DataSource bean = (DataSource) applicationContext.getBean("logDataSource");
		System.out.println(bean);
		assertNotNull(bean);
		//
		System.out.println("connection: " + bean.getConnection());
		System.out.println("autoCommit: " + bean.getConnection().getAutoCommit());
		System.out.println("transactionIsolation: " + bean.getConnection().getTransactionIsolation());
	}

	@Test
	public void logSessionFactory() throws Exception {
		SessionFactory bean = (SessionFactory) applicationContext.getBean("logSessionFactory");
		System.out.println(bean);
		assertNotNull(bean);
		//
		Session session = bean.openSession();
		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				System.out.println("connection: " + connection);
				System.out.println("autoCommit: " + connection.getAutoCommit());
				System.out.println("transactionIsolation: " + connection.getTransactionIsolation());
			}
		});
	}

	// @Test
	// public void logTxAdvice() {
	// TransactionInterceptor bean = (TransactionInterceptor)
	// applicationContext.getBean("logTxAdvice");
	// System.out.println(bean);
	// assertNotNull(bean);
	// }

	@Test
	public void logTx() {
		HibernateTransactionManager bean = (HibernateTransactionManager) applicationContext.getBean("logTx");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void logDaoSupporter() {
		CommonDaoSupporter bean = (CommonDaoSupporter) applicationContext.getBean("logDaoSupporter");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void appLogDaoSupporter() {
		AppLogDao bean = (AppLogDao) applicationContext.getBean("appLogDaoSupporter");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void logServiceSupporter() {
		LogService bean = (LogService) applicationContext.getBean("logServiceSupporter");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void appLogServiceSupporter() {
		AppLogService bean = (AppLogService) applicationContext.getBean("appLogServiceSupporter");
		System.out.println(bean);
		assertNotNull(bean);
	}

	@Test
	public void logAsyncService() {
		AsyncService bean = (AsyncService) applicationContext.getBean("logAsyncService");
		System.out.println(bean);
		assertNotNull(bean);
		//
		// ThreadHelper.sleep(3 * 1000);
		// BeanDefinitionRegistry factory = (BeanDefinitionRegistry)
		// applicationContext.getAutowireCapableBeanFactory();
		// factory.removeBeanDefinition("logAsyncService");
		// ThreadHelper.sleep(3 * 1000);
	}
}
