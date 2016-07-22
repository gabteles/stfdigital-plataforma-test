package br.jus.stf.core.framework.testing.oauth2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import br.jus.stf.core.framework.testing.Oauth2TestHelpers;

/**
 * Classe adaptado do spring-security-test para permitir mockar um usuário do oauth2 nos testes.
 *
 * @author Tomas.Godoi
 * @since 0.0.1
 * @see WithMockOauth2User
 */
final class WithMockOauth2UserSecurityContextFactory implements
		WithSecurityContextFactory<WithMockOauth2User> {

	public SecurityContext createSecurityContext(WithMockOauth2User withUser) {
		String username = StringUtils.hasLength(withUser.username()) ? withUser
				.username() : withUser.value();
		if (username == null) {
			throw new IllegalArgumentException(withUser
					+ " cannot have null username on both username and value properites");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (String authority : withUser.authorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}

		if(grantedAuthorities.isEmpty()) {
			for (String role : withUser.roles()) {
				if (role.startsWith("ROLE_")) {
					throw new IllegalArgumentException("roles cannot start with ROLE_ Got "
							+ role);
				}
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
		} else if(!(withUser.roles().length == 1 && "USER".equals(withUser.roles()[0]))) {
			throw new IllegalStateException("You cannot define roles attribute "+ Arrays.asList(withUser.roles())+" with authorities attribute "+ Arrays.asList(withUser.authorities()));
		}

		Authentication authentication = Oauth2TestHelpers.buildOauth2TestAuthentication(username, withUser.pessoaId(), grantedAuthorities.toArray(new GrantedAuthority[0]));
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}
}