package org.papadeas;


import org.junit.jupiter.api.Test;
import org.papadeas.dto.MovieDto;
import org.papadeas.services.MovieService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieIntegrationTests extends BaseIntegrationTests {

    @MockBean
    private MovieService movieService;

    private final String URL = "/api/movie";


    @Test
    @WithMockUser("movieTester")
    public void createMovie() throws Exception {
        MovieDto movieDto = new MovieDto();
        mockMvc.perform(post(URL).content(asJsonString(movieDto)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(RESPONSE_OK).andReturn();
        verify(movieService, times(1)).create(any(MovieDto.class));
    }

}
