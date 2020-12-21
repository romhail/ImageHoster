package ImageHoster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ImageHosterApplication class This is the main class to start the Sprint boot
 * application
 * 
 * @author Romil
 */
@ComponentScan(value = { "ImageHoster.controller", "ImageHoster.config", "ImageHoster.service",
		"ImageHoster.repository" })
@SpringBootApplication
@EnableAutoConfiguration
public class ImageHosterApplication {
	/**
	 * main method to start the execution
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ImageHosterApplication.class, args);
	}
}
