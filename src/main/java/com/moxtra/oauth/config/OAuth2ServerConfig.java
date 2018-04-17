package com.moxtra.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuth2ServerConfig {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
        		http.requestMatchers().antMatchers("/api/**")
        			.and()
        			.authorizeRequests()
        			.antMatchers("/api/**").authenticated();
        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;
        
        @Autowired(required = false)
        private TokenStore inMemoryTokenStore;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	        	clients.inMemory()
	            .withClient("demoApp")
	            .secret("demoAppSecret")
	            .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token",
	                    "password", "implicit")
	            .scopes("select")
	            .resourceIds("oauth2-resource")
	            .accessTokenValiditySeconds(43200)
	            .refreshTokenValiditySeconds(50000);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(inMemoryTokenStore)
                    .authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        		oauthServer
        			.realm("oauth2-resources")
        			.tokenKeyAccess("permitAll()")
        			.checkTokenAccess("isAuthenticated()")
        			.allowFormAuthenticationForClients();
        }

    }

}
