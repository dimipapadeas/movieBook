package org.papadeas;


import org.junit.jupiter.api.Test;
import org.papadeas.dto.VoteDto;
import org.papadeas.enums.Reaction;
import org.papadeas.services.VoteService;
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
public class VoteIntegrationTests extends BaseIntegrationTests {


    @MockBean
    private VoteService voteService;

    private final String URL = "/api/vote";


    @Test
    @WithMockUser("movieTester")
    public void testVote() throws Exception {
        VoteDto voteDto = new VoteDto();
        voteDto.setVote(Reaction.POSITIVE);
        voteDto.setMovieId("movieId");
        voteDto.setUserId("admin");
        mockMvc.perform(post(URL).content(asJsonString(voteDto)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(RESPONSE_OK).andReturn();
        verify(voteService, times(1)).voteMovie(any(VoteDto.class));
    }
}
