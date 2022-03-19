/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.reactive;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by wangsan on 2021/09/15.
 *
 * @author wangsan
 */
public class HelloFlux {
	public static void main(String[] args) throws InterruptedException {
		Flux.just("hello").subscribe(System.out::println);

		Flux.fromIterable(Arrays.asList("a", "b", "c", "d"))
				.delayElements(Duration.ofMillis(100))
				.doOnNext(x -> System.out.println(x + " emit "))
				.map(d -> d + "_***")
				.take(3)
				.onErrorResume(e -> {
					e.printStackTrace();
					return Flux.just("error");
				})
				.doAfterTerminate(() -> System.out.println("mission completed"))
				.subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(2);
	}

	public void map() {
		Flux.range(0, 5).map(i -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return i * 2;
		}).subscribe(System.out::println);
	}

	public void flatMap() {
		System.out.println("---flatMap 1---");
		Flux.range(0, 5).log().flatMap(i -> Mono.fromCallable(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return i * 2;
		})).subscribe(i -> {
			System.out.println(new Date() + "  " + i);
		});

		System.out.println("---flatMap 2---");
		Flux.range(0, 5).log()
				.flatMap(i -> Mono.just(i * 2).delayElement(Duration.ofSeconds(1)))
				.subscribe(i -> {
					System.out.println(new Date() + "  " + i);
				});
		try {
			TimeUnit.SECONDS.sleep(2);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
