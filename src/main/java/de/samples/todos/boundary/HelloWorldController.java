package de.samples.todos.boundary;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Profile("dev")
@Controller
@RequestMapping("hello")
@Tag(name = "sample", description = "Some simple samples.")
public class HelloWorldController {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    String sayHello(
      @RequestParam(value = "name", defaultValue = "World")
      String name
    ) {
        return String.format("Hello %s!", name);
    }

}
