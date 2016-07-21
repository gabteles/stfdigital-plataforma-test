package br.jus.stf.core.framework.testing;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.03.2016
 */
public class RemoteServiceTestsSupport {
	
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResultActions call(MockMvc mockMvc, String url, String content) throws Exception {
		ResultActions actions = mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(content));
		try {
			actions.andExpect(status().isOk());
		} catch (AssertionError e) {
			throw new IllegalStateException(e);
		}
		return actions;
    }
    
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2))
    public ResultActions query(MockMvc mockMvc, String url, String content) throws Exception {
		ResultActions actions = mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(content));
		try {
			actions.andExpect(status().isOk());
			actions.andExpect((jsonPath("$", Matchers.hasSize(1))));
	        actions.andExpect((jsonPath("$[0].processoId", Matchers.notNullValue())));
		} catch (AssertionError e) {
			throw new IllegalStateException(e);
		}
		return actions;
    }
    
}
