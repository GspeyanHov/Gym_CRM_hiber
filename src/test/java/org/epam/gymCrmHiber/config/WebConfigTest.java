package org.epam.gymCrmHiber.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebConfigTest {

    private MockServletContext servletContext;
    private AnnotationConfigWebApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        servletContext = new MockServletContext();
        applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setServletContext(servletContext);
        applicationContext.register(WebConfig.class);
        applicationContext.refresh(); // Manually refresh the application context
    }

    @Test
    void testServletContext() {
        assertNotNull(applicationContext.getServletContext());
    }
}
