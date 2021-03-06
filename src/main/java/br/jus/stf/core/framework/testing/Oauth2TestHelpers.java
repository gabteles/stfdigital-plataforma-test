package br.jus.stf.core.framework.testing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 * <p>Classe com métodos utilitários relacionados com a autenticação
 * via Oauth2 durante os testes de integração.</p>
 * 
 * <p>Essa estratégia de mock da autenticação foi baseada nos seguintes links:</p>
 * 
 * <ul>
 *     <li><a href="http://engineering.pivotal.io/post/faking_oauth_sso/">
 *     	            http://engineering.pivotal.io/post/faking_oauth_sso/</a></li>
 * 	   <li><a href="https://github.com/spring-projects/spring-security-oauth/issues/385">
 *                  https://github.com/spring-projects/spring-security-oauth/issues/385</a></li>
 * </ul>
 * 
 * @author Tomas.Godoi
 *
 */
public class Oauth2TestHelpers {

	public static final String TEST_ACCESS_TOKEN = "9d31c7ec-d4a1-4017-9e13-dc2832761e4e";
	
	/**
	 * <p>Constrói um RequestPostProcessor para realizar a autenticação mock nos testes.</p>
	 * 
	 * <p>Deve ser usado da seguinte forma:</p>
	 * <pre>
	 *     mockMvc.perform(post("/api/url")
	 *             .with(oauthAuthentication(login))
	 *             .contentType(APPLICATION_JSON).content(json))
	 * </pre>
	 * 
	 * @param login O login do usuário
	 * @return o RequestPostProcessor que injetará a autenticação
	 */
	public static RequestPostProcessor oauth2Authentication(String login) {
		return authentication(buildOauth2TestAuthentication(login, 1));
	}
	
	public static RequestPostProcessor oauth2Authentication(String login, Integer pessoaId) {
		return authentication(buildOauth2TestAuthentication(login, pessoaId));
	}
	
	public static Authentication buildOauth2TestAuthentication(String principal, Integer pessoaId, GrantedAuthority... authorities) {
	    return new OAuth2Authentication(getOauth2Request(), getAuthentication(principal, pessoaId, new String[] {}, authorities));
	}
	
	public static Authentication buildOauth2TestAuthentication(String principal, int pessoaId, String[] components, GrantedAuthority... authorities) {
	    return new OAuth2Authentication(getOauth2Request(), getAuthentication(principal, pessoaId, components, authorities));
	}
	
	private static OAuth2Request getOauth2Request () {
		boolean approved = true;
	    List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;
	    String clientId = null;
	    Map<String, Serializable> extensionProperties = Collections.emptyMap();
	    String redirectUri = null;
	    Map<String, String> requestParameters = Collections.emptyMap();
	    Set<String> resourceIds = Collections.emptySet();
	    Set<String> responseTypes = Collections.emptySet();
	    Set<String> scopes = Collections.emptySet();

	    OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities,
	        approved, scopes, resourceIds, redirectUri, responseTypes, extensionProperties);

	    return oAuth2Request;
	}
	
	private static Authentication getAuthentication(String principal, Integer pessoaId, String[] components, GrantedAuthority... authorities) {
		List<GrantedAuthority> authoritiesList;
		if (authorities.length == 0) {
			authoritiesList = AuthorityUtils.createAuthorityList("ROLE_USER");
		} else {
			authoritiesList = Arrays.asList(authorities);
		}

	    HashMap<String, Object> details = new HashMap<>();
	    details.put("login", principal);
	    details.put("pessoaId", pessoaId);
	    details.put("componentes", Arrays.asList(components));

	    TestingAuthenticationToken token = new TestingAuthenticationToken(principal, "N/A", authoritiesList);
	    token.setAuthenticated(true);
	    token.setDetails(details);

	    return token;
	}

}
