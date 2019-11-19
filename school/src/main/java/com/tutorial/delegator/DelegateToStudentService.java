package com.tutorial.delegator;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class DelegateToStudentService {
    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //Hystrix Core Circuit breaker method
    @HystrixCommand(fallbackMethod = "serviceDownFallBackMethod")
    public String callStudentServiceAndGetData(String schoolName) {
        String response = restTemplate
                .exchange("http://localhost:8098/getStudentDetailsForSchool/{schoolname}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<String>() {
                        }, schoolName).getBody();

        System.out.println("Response Received as " + response + " -  " + new Date());

        return "NORMAL FLOW !!! - School Name -  " + schoolName + " :::  " +
                " Student Details " + response + " -  " + new Date();
    }


    private String serviceDownFallBackMethod(String schoolName) {
        System.out.println("Student Microservice is Down !!");
        System.out.println("Unable to fetch details for school name " + schoolName);
        return "" + new Date();
    }
}
