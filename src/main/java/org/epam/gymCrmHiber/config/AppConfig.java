package org.epam.gymCrmHiber.config;

import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "org.epam.gymCrmHiber")
public class AppConfig {

   @Autowired
   private Environment environment;

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("db.driver")));
      dataSource.setUrl(environment.getProperty("db.url"));
      dataSource.setUsername(environment.getProperty("db.username"));
      dataSource.setPassword(environment.getProperty("db.password"));
      return dataSource;
   }
   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
      bean.setDataSource(getDataSource());
      bean.setPackagesToScan("org.epam.gymCrmHiber.entity");
      Properties properties = new Properties();
      properties.setProperty("hibernate.format.sql", environment.getProperty("hibernate.format.sql"));
      properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
      properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
      properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
      bean.setHibernateProperties(properties);
      return bean;
   }

   @Bean
   public PlatformTransactionManager transactionManager() {
      JpaTransactionManager manager = new JpaTransactionManager();
      manager.setEntityManagerFactory(sessionFactory().getObject());
      return manager;
   }
}