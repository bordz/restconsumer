package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

@Controller
public class GreetingController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        Greeting greetResponse = GreetingService.getGreeting(name);
        model.addAttribute("greetResponse", greetResponse);
        return "greeting";
    }

}

class GreetingService {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static Greeting getGreeting(String name) {
    String endpoint = System.getenv("RESTAPI_ENDPOINT");
    endpoint = name == null ? endpoint : endpoint+"?name="+name;

    RestTemplate restTemplate = new RestTemplate();
    Greeting quote = restTemplate.getForObject(endpoint, Greeting.class);

    log.info(quote.toString().concat(endpoint == null ? "not set" : endpoint));

    return quote;
  }
}


@JsonIgnoreProperties(ignoreUnknown = true)
class Greeting {

    private Integer id;
    private String content;

    public Greeting() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id='" + id + '\'' +
                ", content=" + content +
                '}';
    }
}
