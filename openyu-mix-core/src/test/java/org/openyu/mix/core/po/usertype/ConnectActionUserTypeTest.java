package org.openyu.mix.core.po.usertype;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openyu.mix.core.po.usertype.ConnectActionUserType;
import org.openyu.mix.core.vo.Core.ConnectAction;
import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class ConnectActionUserTypeTest extends BaseTestSupporter {

	private static ConnectActionUserType userType = new ConnectActionUserType();

	@Test
	// 1000000 times: 101 mills.
	// 1000000 times: 116 mills.
	// 1000000 times: 100 mills.
	// verified
	public void marshal() {
		ConnectAction value = ConnectAction.CONNECT;
		//
		String result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.marshal(value, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		// 1
		System.out.println(result);
		//
		assertEquals("♥1♠1", result);
	}

	@Test
	// 1000000 times: 1358 mills.
	// 1000000 times: 1307 mills.
	// 1000000 times: 1339 mills.
	// verified
	public void unmarshal() {
		String value = "♥1♠1";
		//
		ConnectAction result = null;
		//
		int count = 1000000;

		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			result = userType.unmarshal(value, null, null);
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		assertEquals(ConnectAction.CONNECT, result);
	}
}
