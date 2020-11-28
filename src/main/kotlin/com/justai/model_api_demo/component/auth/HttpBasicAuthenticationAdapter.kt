package com.justai.model_api_demo.component.auth

import com.justai.model_api_demo.config.AuthCredentialsConfigProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class HttpBasicAuthenticationAdapter(
        private val credsConfig: AuthCredentialsConfigProperties
) : WebSecurityConfigurerAdapter() {
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        // .authenticationProvider(authenticationProvider)
        auth.inMemoryAuthentication()
                .withUser(credsConfig.login).password(credsConfig.password)
                .authorities("ROLE_USER")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/version", "/healthCheck").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().realmName("ModelApi")
                .and()
                .csrf().disable()
    }
}
