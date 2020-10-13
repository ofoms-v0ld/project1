package com.example.service1;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class Service1Application {

	public static void main(String[] args) {
		SpringApplication.run(Service1Application.class, args);
	}

	@Scheduled(fixedDelay = 3000)
	public void reportCurrentTime() {

		Set<Thread> threads = Thread.getAllStackTraces().keySet();

		for (Thread t : threads) {
			String name = t.getName();
			Thread.State state = t.getState();
			int priority = t.getPriority();
			String type = t.isDaemon() ? "Daemon" : "Normal";
			System.out.printf("%-20s \t %s \t %d \t %s\n", name, state, priority, type);

		}
		System.out.println("=====================================================================");
	}

	@Bean
	public void asyncSchelduler(){
		Flux.interval(Duration.ofMillis(5000))
				.onBackpressureDrop()
				.flatMap(ignore -> doSomething())
				.subscribe();

	}

	private Publisher<?> doSomething() {
		System.out.println("@@@@@@@@@@@@@@@ "+ Thread.currentThread().getName());
		System.out.println("@@@@@@@@@@@@@@@ "+ Thread.currentThread().getState());
		System.out.println("@@@@@@@@@@@@@@@ "+ Thread.currentThread().getId());
		
		return Subscriber::onComplete;
	}


}
