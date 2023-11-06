package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreetingsRestController {

    //1 способ поприветствовать пользователя
    @GetMapping("/api/v1/greeting")
    public ResponseEntity<Map<String,String>> getGreetings(){
        //Получаем авторизованного пользователя
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
//приветствие
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting","Hello v1, %s!"
                        .formatted(userDetails.getUsername())));
    }


    //2 способ поприветствовать пользователя
    @GetMapping("/api/v2/greeting")
    public ResponseEntity<Map<String,String>> getGreetings2(HttpServletRequest request){
        //Получаем авторизованного пользователя
        UserDetails userDetails= (UserDetails) ((Authentication) request.getUserPrincipal())
                .getPrincipal();
//приветствие
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting","Hello v2, %s!"
                        .formatted(userDetails.getUsername())));
    }


    //3 способ поприветствовать пользователя
    @GetMapping("/api/v3/greeting")
    public ResponseEntity<Map<String,String>> getGreetings3(
//@AuthenticationPrincipal-данная аннотация сразу извлекает из текущей аутенфикации
//,сохраненной в контексте безопасности, principal и кастует ее к userDetails
           @AuthenticationPrincipal UserDetails userDetails){ //Получаем авторизованного пользователя

//приветствие
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting","Hello v3, %s!"
                        .formatted(userDetails.getUsername())));
    }

    //5 способ поприветствовать пользователя
    @GetMapping("/api/v5/greeting")
    public ResponseEntity<Map<String,String>> getGreetings4(
            UsernamePasswordAuthenticationToken principal){ //Получаем авторизованного пользователя
//приветствие
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("greeting","Hello v5, %s!"
                        .formatted(principal.getName())));
    }



}
