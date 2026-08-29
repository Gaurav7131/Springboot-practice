//Purpose"Eliminate the use of web.xml(deployment file descpt.) by registering DispatcherServletInitializer
package com.example.demo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebIntializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null; // Root appln_context
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };// points to our spring mvc config
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };// maps DispatcherServlet to catch all req.
    }
}
