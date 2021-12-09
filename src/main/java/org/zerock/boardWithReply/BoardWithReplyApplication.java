package org.zerock.boardWithReply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardWithReplyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardWithReplyApplication.class, args);
	}

}
