package br.jus.stf.core.framework.testing;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Centraliza o setup básico para os testes de integração.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.12.2015
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTestsSupport {

    @Autowired
    protected MockMvc mockMvc;
    
    @Autowired
	private DataSource dataSource;
    
    /**
     * Realiza a carga de scripts SQL.
     * 
     * @param scriptsSql O nome do script
     * @throws SQLException Quando ocorrer algum erro ao executar a carga
     */
    protected void loadDataTests(String... scriptsSql) throws SQLException {
    	Connection connection = null;
    	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		
    	for(String scriptSql : scriptsSql) {
    		populator.addScript(new ClassPathResource("/db/tests/" + scriptSql));
    	}
		
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			populator.populate(connection);
		} finally {
			if (connection != null) {
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}
	}
    
}
