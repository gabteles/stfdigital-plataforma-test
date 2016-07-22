package br.jus.stf.core.framework.testing.oauth2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Anotação adaptada do spring-security-test para mockar
 * um usuário do oauth2.
 *
 * @author Tomas.Godoi
 * @since 0.0.1
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockOauth2UserSecurityContextFactory.class)
public @interface WithMockOauth2User {
	/**
	 * Convenience mechanism for specifying the username. The default is "user". If
	 * {@link #username()} is specified it will be used instead of {@link #value()}
	 * @return
	 */
	String value() default "user";

	/**
	 * The username to be used. Note that {@link #value()} is a synonym for
	 * {@link #username()}, but if {@link #username()} is specified it will take
	 * precedence.
	 * @return
	 */
	String username() default "";

	/**
	 * <p>
	 * The roles to use. The default is "USER". A {@link GrantedAuthority} will be created
	 * for each value within roles. Each value in roles will automatically be prefixed
	 * with "ROLE_". For example, the default will result in "ROLE_USER" being used.
	 * </p>
	 * <p>
	 * If {@link #authorities()} is specified this property cannot be changed from the default.
	 * </p>
	 *
	 * @return
	 */
	String[] roles() default { "USER" };

	/**
	 * <p>
	 * The authorities to use. A {@link GrantedAuthority} will be created for each value.
	 * </p>
	 *
	 * <p>
	 * If this property is specified then {@link #roles()} is not used. This differs from
	 * {@link #roles()} in that it does not prefix the values passed in automatically.
	 * </p>
	 *
	 * @return
	 */
	String[] authorities() default {};

}