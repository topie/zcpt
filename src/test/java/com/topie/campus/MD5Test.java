package com.topie.campus;

import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;

public class MD5Test {

	public MD5Test() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testMD5()
	{
		System.out.println(Md5Crypt.md5Crypt(">dE|6I=Y".getBytes()));
	}
}
