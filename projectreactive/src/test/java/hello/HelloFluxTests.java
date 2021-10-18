/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package hello;

import org.junit.Test;

/**
 * created by wangsan on 2021/10/09.
 *
 * @author wangsan
 */
public class HelloFluxTests {
	HelloFlux hello = new HelloFlux();

	@Test
	public void map() {
		hello.map();
	}

	@Test
	public void flatMap() {
		hello.flatMap();
	}
}