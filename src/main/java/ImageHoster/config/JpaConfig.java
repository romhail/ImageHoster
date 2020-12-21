package ImageHoster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * JpaConfig class This class handles the connection with the DB
 * 
 * @author Romil
 */
@Configuration
@EnableJpaRepositories("ImageHoster.repository")
public class JpaConfig {

	/**
	 * Method entityManagerFactory This is factory method to create entity
	 * 
	 * @return EntityManagerFactory
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
		emfb.afterPropertiesSet();
		return emfb.getObject();
	}

	/**
	 * Method dataSource
	 * 
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/imageHoster");
		ds.setUsername("postgres");
		ds.setPassword("admin");
		return ds;
	}
}
