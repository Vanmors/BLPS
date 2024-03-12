package com.example.lab1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    String usersXmlPath = "./Lab1/src/main/resources/user.xml";
    private UserDetails[] loadUserDetailsFromXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(usersXmlPath);

            NodeList userList = doc.getElementsByTagName("user");
            UserDetails[] userDetails = new UserDetails[userList.getLength()];
            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    String username = userElement.getElementsByTagName("username").item(0).getTextContent();
                    String password = userElement.getElementsByTagName("password").item(0).getTextContent();
                    String roles = userElement.getElementsByTagName("roles").item(0).getTextContent();
                    userDetails[i] = User.builder()
                            .username(username)
                            .password(passwordEncoder().encode(password))
                            .roles(roles.split(","))
                            .build();
                }
            }
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return new UserDetails[0];
        }
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails[] users = loadUserDetailsFromXml();

//        UserDetails user1 = User.builder()
//                .username("Ivan")
//                .password(passwordEncoder().encode("Adminman"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("Ilya")
//                .password(passwordEncoder().encode("Adminman"))
//                .roles("USER")
//                .build();
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/hotel").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/hotel").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/hotel").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/hotel").hasRole("ADMIN")
                .requestMatchers("/user").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new XmlUserDetailsService();
//    }
}
