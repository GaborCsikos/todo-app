package hu.gabor.csikos.todoapp;

import org.jumpmind.symmetric.common.ParameterConstants;
import org.jumpmind.symmetric.web.ServerSymmetricEngine;
import org.jumpmind.symmetric.web.SymmetricEngineHolder;
import org.jumpmind.symmetric.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.Properties;

@SpringBootApplication
public class TodoappApplication implements ApplicationListener<ApplicationReadyEvent> {
	private static final String SYNC_URL = "/sync/*";
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private DataSource dataSource;

	@Value("${symmetricds.properties}")
	private String serverProperties;

	@Value("${symmetricds.id}")
	private String symmetricDSID;

	@Value("${symmetricds.config.script}")
	private String symmetricdsDataScript;

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}


	@Override
	final public void onApplicationEvent(ApplicationReadyEvent event) {
		SymmetricEngineHolder holder = new SymmetricEngineHolder();
		Properties properties = new Properties();
		properties.put(ParameterConstants.EXTERNAL_ID, symmetricDSID);
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL url = classloader.getResource(serverProperties);
		ServerSymmetricEngine serverEngine = new ServerSymmetricEngine(new File(url.getPath()));
		serverEngine.setDeploymentType("server");
		holder.getEngines().put(properties.getProperty(ParameterConstants.EXTERNAL_ID), serverEngine);

		servletContext.setAttribute(WebConstants.ATTR_ENGINE_HOLDER, holder);

		serverEngine.setup();
		serverEngine.start();

		serverEngine.syncTriggers();
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource(symmetricdsDataScript));
		populator.setContinueOnError(true);
		DatabasePopulatorUtils.execute(populator, dataSource);
		serverEngine.syncTriggers();


	}
}