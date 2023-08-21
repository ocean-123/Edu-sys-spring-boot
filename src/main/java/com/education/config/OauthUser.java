//package com.education.config;
//
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Map;
//
//public class OauthUser implements OAuth2User {
//
//
//    private OAuth2User oAuth2User;
//
//    public OauthUser(OAuth2User oAuth2User) {
//        this.oAuth2User = oAuth2User;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
//        return Collections.singletonMap("authority", authority);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return oAuth2User.getAuthorities();
//    }
//
//    @Override
//    public String getName() {
//        return oAuth2User.getName();
//    }
//}
