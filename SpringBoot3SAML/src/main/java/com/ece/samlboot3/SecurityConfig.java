package com.ece.samlboot3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.CustomizerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.RelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	@Lazy
	RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;
	/*
	 * protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * RelyingPartyRegistrationResolver relyingPartyRegistrationResolver = new
	 * DefaultRelyingPartyRegistrationResolver(
	 * this.relyingPartyRegistrationRepository); Saml2MetadataFilter filter = new
	 * Saml2MetadataFilter(relyingPartyRegistrationResolver, new
	 * OpenSamlMetadataResolver()); http.addFilterBefore(filter,
	 * Saml2WebSsoAuthenticationFilter.class);
	 * 
	 * http.authorizeRequests(authorize ->
	 * authorize.requestMatchers("/").permitAll().anyRequest().authenticated())
	 * .saml2Login(x ->
	 * x.loginPage("/login/saml2/sso")).logout().logoutSuccessUrl("/login/saml2/sso"
	 * ).and() .saml2Logout(Customizer.withDefaults());
	 * 
	 * // add auto-generation of ServiceProvider Metadata
	 * 
	 * }
	 */

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		RelyingPartyRegistrationResolver relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(
				this.relyingPartyRegistrationRepository);
		Saml2MetadataFilter filter = new Saml2MetadataFilter(relyingPartyRegistrationResolver,
				new OpenSamlMetadataResolver());
		http.addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class);

		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login/*,*/saml2/*,*/logout/*,**/sso/**").permitAll()
				.anyRequest().authenticated()).saml2Login(x -> x.defaultSuccessUrl("/ssologin", false))
		       // .logout((log) -> log.logoutSuccessUrl("/sso/saml"))
		        .saml2Logout(Customizer.withDefaults())
		;

		//http.logout((logout) -> logout.logoutSuccessUrl("/myLogoutSuccessUrl")).saml2Logout(Customizer.withDefaults());

		return http.build();
	}

}
