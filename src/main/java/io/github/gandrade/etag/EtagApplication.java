package io.github.gandrade.etag;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CustomShallowEtagHeaderFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class EtagApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtagApplication.class, args);
    }

    @Bean
    public CustomShallowEtagHeaderFilter customShallowEtagHeaderFilter() {
        CustomShallowEtagHeaderFilter customShallowEtagHeaderFilter = new CustomShallowEtagHeaderFilter();
        // shallowEtagHeaderFilter.setWriteWeakETag(true);
        return customShallowEtagHeaderFilter;
    }
}

@RestController
class EtagController {


    @Autowired
    CustomShallowEtagHeaderFilter customShallowEtagHeaderFilter;

    @PutMapping("/test")
    public Hello hi(@RequestBody Hello hello) {
        hello.setName("Hi " + hello.getName());
        return hello;
    }

    @GetMapping("/{name}")
    public Hello hello(@PathVariable String name) {
        Hello hello = new Hello();
        hello.setName("Hello World, " + name);
        return hello;
    }
}

class Hello {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
