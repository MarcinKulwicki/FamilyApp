package pl.marcinkulwicki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pl.marcinkulwicki.filter.FamilyFilter;

import javax.validation.Validator;
import java.util.Arrays;

@Configuration
@SpringBootApplication(scanBasePackages = "pl.marcinkulwicki")
//@ComponentScan("pl.marcinkulwicki")
@EnableJpaAuditing
public class ConfigurationSpringBoot extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ConfigurationSpringBoot.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConfigurationSpringBoot.class, args);
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    FilterRegistrationBean myFilterRegistration () {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new FamilyFilter());
        frb.setUrlPatterns(Arrays.asList("/child/*"));
        return frb;
    }
}
