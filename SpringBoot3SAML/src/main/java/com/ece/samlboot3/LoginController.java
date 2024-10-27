package com.ece.samlboot3;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("ssologin")
@Slf4j
public class LoginController {

	// To Download Service provider Meta Data XML where "flux-idp" is the
	// registration ID
	// http://localhost:8080/saml2/service-provider-metadata/flux-idp

	@GetMapping("")
	public String ssoLogin(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
		
		log.error("logged in details {}",principal);
		model.addAttribute("name", principal.getName());
		model.addAttribute("emailAddress", principal.getFirstAttribute("email"));
		model.addAttribute("userAttributes", principal.getAttributes());
		return "home";

		// return "Loggin user" + principal.getName();
	}

	@GetMapping("fluxLogoutResp")
	public String serviceProvider() {
		return "logoutSuccess";

	}

}