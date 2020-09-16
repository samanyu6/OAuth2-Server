package com.samanyu.security.oauth2.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

// WebSecConfig is extended to get access to Authentication Manager.

@Configuration
public class AuthorServerConfig extends WebSecurityConfigurerAdapter implements AuthorizationServerConfigurer {

//    We require AuthManager to validate grant types.
//    Since we cannot autowire AuthManager directly in newer version of spring, we create a new bean and autowire that into the file.
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

//    Autowiring the above bean, without the bean definition the authenticationManager bean cannot be found(as we don't have direct access to it).
//    Below Method is a Field Injection. This should be avoided, and hence defining a constructor based injection to ensure immutability for authManagerBean.
//    Can do a setter injection too, if mutability is the case for AuthManager.

//    @Autowired
//    AuthenticationManager authenticationManager;

//    @Lazy annotation generates mini beans only at the time they're needed. This is used to avoid cyclic dependencies.
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorServerConfig(@Lazy AuthenticationManager authMgr){
        this.authenticationManager = authMgr;
    }

    PasswordEncoder encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();

//    Configure security options
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {

    //  Similar to settings done in application.properties, where all the users can check the validity of their token.
//        Should be avoided during production.
        authorizationServerSecurityConfigurer.checkTokenAccess("permitAll()");
    }

//    Authorization values for client application here
    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        clientDetailsServiceConfigurer.inMemory().
                                    withClient("web").
                                    secret(encode.encode("webpass")).
                                    scopes("READ","WRITE").
                                      authorizedGrantTypes("password", "authorization_code");
    }

//    Configure endpoints
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {

//        Setting the AuthMgr for endpoints.
        authorizationServerEndpointsConfigurer.authenticationManager(authenticationManager);
    }
}
