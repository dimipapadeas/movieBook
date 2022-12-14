package org.papadeas;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.papadeas.model.User;
import org.papadeas.repositories.MoviesRepository;
import org.papadeas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(classes = ApplicationBootstrap.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
    "spring.datasource.url=jdbc:tc:mariadb:10.3:///",
    "spring.jpa.database-platform=org.hibernate.dialect.MariaDB103Dialect",

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

  private static final MariaDBContainer mariadb;


  static {
    mariadb = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.5.5"));
    mariadb.start();
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mariadb::getJdbcUrl);
    registry.add("spring.datasource.username", mariadb::getUsername);
    registry.add("spring.datasource.password", mariadb::getPassword);
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
