package org.epam.gymCrmHiber.config;

import java.util.Properties;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class AppConfigTest {

    @Mock
    private Environment environment;

    @Mock
    private LocalSessionFactoryBean sessionFactoryBean;

    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSessionFactory() {
        when(environment.getProperty("db.driver")).thenReturn("org.h2.Driver");
        when(environment.getProperty("db.url")).thenReturn("jdbc:h2:mem:testdb");
        when(environment.getProperty("db.username")).thenReturn("username");
        when(environment.getProperty("db.password")).thenReturn("password");
        when(environment.getProperty("hibernate.format.sql")).thenReturn("true");
        when(environment.getProperty("hibernate.show_sql")).thenReturn("true");
        when(environment.getProperty("hibernate.hbm2ddl.auto")).thenReturn("update");
        when(environment.getProperty("hibernate.dialect")).thenReturn("org.hibernate.dialect.H2Dialect");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.format.sql", "true");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        sessionFactoryBean.setDataSource(mock(DataSource.class));
        sessionFactoryBean.setPackagesToScan("org.epam.gymCrmHiber.entity");
        sessionFactoryBean.setHibernateProperties(hibernateProperties);

        verify(sessionFactoryBean).setDataSource(any(DataSource.class));
        verify(sessionFactoryBean).setPackagesToScan("org.epam.gymCrmHiber.entity");
        verify(sessionFactoryBean).setHibernateProperties(hibernateProperties);
    }
}
