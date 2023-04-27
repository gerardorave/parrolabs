package co.parrolabs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.yml"})
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper();}



}
