package org.papadeas;


import org.junit.jupiter.api.Test;
import org.papadeas.dto.UserDto;
import org.papadeas.services.UserService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTests extends BaseIntegrationTests {

    @MockBean
    private UserService userService;

    private final String URL = "/api/user";


    @Test
    @WithMockUser("movieTester")
    public void testGetUserById() throws Exception {
        String id = "admin";
        mockMvc.perform(get(URL + "/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(RESPONSE_OK).andReturn();
        verify(userService, times(1)).getUserById(id);
    }

    @Test
    @WithMockUser("movieTester")
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON)).andExpect(RESPONSE_OK).andReturn();
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @WithMockUser("movieTester")
    public void testCreateNewUser() throws Exception {
        mockMvc.perform(get(URL + "/_draft").accept(MediaType.APPLICATION_JSON))
                .andExpect(RESPONSE_OK).andReturn();
        verify(userService, times(1)).createDraftUser();
    }


    @Test
    @WithMockUser("movieTester")
    public void testUpdate() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId("admin");
        userDto.setFirstName("administrator");
        mockMvc.perform(put(URL).content(asJsonString(userDto)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(RESPONSE_OK).andReturn();
        verify(userService, times(1)).updateUser(any(UserDto.class));
    }
}
