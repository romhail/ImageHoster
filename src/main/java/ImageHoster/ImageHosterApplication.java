package ImageHoster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ImageHosterApplication class
 * @author Romil
 */
@ComponentScan(value={"ImageHoster.controller", "ImageHoster.config","ImageHoster.service","ImageHoster.repository"})
@SpringBootApplication
@EnableAutoConfiguration
public class ImageHosterApplication {
	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        SpringApplication.run(ImageHosterApplication.class, args);
    }
}
