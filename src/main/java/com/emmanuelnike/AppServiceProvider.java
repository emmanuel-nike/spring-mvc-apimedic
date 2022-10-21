package com.emmanuelnike;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppServiceProvider extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {MvcConfigProvider.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null; //new Class[] {AppConfigProvider.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
