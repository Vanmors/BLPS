package com.example.lab1.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
public class XmlUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("users.xml");

            NodeList userList = doc.getElementsByTagName("user");
            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    String xmlUsername = userElement.getElementsByTagName("username").item(0).getTextContent();
                    if (username.equals(xmlUsername)) {
                        String password = userElement.getElementsByTagName("password").item(0).getTextContent();
                        String roles = userElement.getElementsByTagName("roles").item(0).getTextContent();
                        return User.withUsername(username)
                                .password(password)
                                .roles(roles.split(","))
                                .build();
                    }
                }
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found", e);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
 