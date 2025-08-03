package oauth2.Java;

import oauth2.Java.Component.CustomMailSender;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaApplication {
 private CustomMailSender customMailSender;

	public JavaApplication(CustomMailSender customMailSender) {
		this.customMailSender = customMailSender;
	}

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(JavaApplication.class, args);
				JavaApplication app = context.getBean(JavaApplication.class);
		app.run();

	}
	private void run() {
		customMailSender.sendMail("playbestgames11@gmail.com","Email from JavaSpringBoot","This email has been authorized");
	}

}
