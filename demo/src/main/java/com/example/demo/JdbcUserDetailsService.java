//package com.example.demo;
//
//import org.springframework.jdbc.object.MappingSqlQuery;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
////Класс для связи с БД -шаблон адаптер
//public class JdbcUserDetailsService extends MappingSqlQuery<UserDetails>
//implements UserDetailsService{
//
//    public JdbcUserDetailsService(DataSource ds) {
//        super(ds, """
//                select
//                *
//                """);
//    }
//
//    @Override
//    protected UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
//}
