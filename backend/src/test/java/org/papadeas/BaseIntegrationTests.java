package org.papadeas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.papadeas.model.User;
import org.papadeas.repositories.MoviesRepository;
import org.papadeas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)

@SpringBootTest(classes = ApplicationBootstrap.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "spring.datasource.url=jdbc:tc:mariadb:10.3.16:///?TC_MY_CNF=mariadb_conf_override",
    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseIntegrationTests {

  protected final ResultMatcher RESPONSE_OK = status().isOk();

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  UserRepository userRepository;

  @MockBean
  MoviesRepository moviesRepository;


  protected User user = new User("movieTester", "pass", "movieTester", "movieTester",
      new ArrayList<>(), new ArrayList<>());

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }


  /**
   * Utility method
   *
   * @param obj to be converted to json string
   */
  protected static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
