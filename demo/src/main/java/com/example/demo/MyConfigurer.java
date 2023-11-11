package com.example.demo;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;



//2 параметра в дженерики-класс конфигурации и класс построителя безопасности
public class MyConfigurer extends AbstractHttpConfigurer<MyConfigurer, HttpSecurity> {

//Это хороший способ для информирования в логах о создании объекта
    private static final Logger LOGGER= LoggerFactory.getLogger(MyConfigurer.class);

    private String realmName="My realm";

    @Override
    public void init(HttpSecurity builder) throws Exception {
        LOGGER.info("AM in init: {}", builder.getSharedObject(AuthenticationManager.class));
        builder.httpBasic(httpBasic->httpBasic.realmName(this.realmName))
                .authorizeHttpRequests
                        (authorizeHttpRequests->
                                authorizeHttpRequests.anyRequest().authenticated());
    }

    @Override //в этом методе нельзя инициализировать конфигурации, это делается в методе init()
    public void configure(HttpSecurity builder) throws Exception {
        //лог покажет что к ТОЛЬКО к моменту выполнения метода configurer создастся менеджер аутенфикации
        LOGGER.info("AM in configure: {}", builder.getSharedObject(AuthenticationManager.class));

    }

    public MyConfigurer realName(String realmName){
        this.realmName=realmName;
        return this;
    }

}
