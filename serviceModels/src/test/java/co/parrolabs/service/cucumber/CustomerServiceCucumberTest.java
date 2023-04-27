package co.parrolabs.service.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty" },
        glue = {"co.parrolabs.service.cucumber"},
        features = {"src/main/resources/test_customer_cucumber"})

public class CustomerServiceCucumberTest {
}
