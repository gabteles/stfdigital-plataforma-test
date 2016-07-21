package br.jus.stf.core.framework.testing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

import java.io.Serializable;
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
	public static RequestPostProcessor oauthAuthentication(String login) {
		return authentication(getOauthTestAuthentication(login));
	}
	
	
	private static Authentication getOauthTestAuthentication(String principal) {
	    return new OAuth2Authentication(getOauth2Request(), getAuthentication(principal));
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
	
	private static Authentication getAuthentication(String principal) {
	    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

	    HashMap<String, Object> details = new HashMap<>();
	    details.put("login", principal);
	    details.put("pessoaId", new Integer(1));

	    TestingAuthenticationToken token = new TestingAuthenticationToken(principal, "N/A", authorities);
	    token.setAuthenticated(true);
	    token.setDetails(details);

	    return token;
	}
	
}
