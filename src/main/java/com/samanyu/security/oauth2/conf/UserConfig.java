package com.samanyu.security.oauth2.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig extends GlobalAuthenticationConfigurerAdapter {

    PasswordEncoder encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    Create users
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

//        Set two dummy users to test sec APIs.
        auth.inMemoryAuthentication().
                    withUser("samanyu").
                    password(encode.encode("sampass")).
                    roles("USER").
                    authorities("READ","WRITE").

                    and().

                    withUser("someone").
                    password(encode.encode("someoneelse")).
                    roles("ADMIN").
                    authorities("DELETE");
    }
}
