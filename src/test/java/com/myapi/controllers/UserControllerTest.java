/**
 * Created by : Alan Nascimento on 4/2/2022
 */
package com.myapi.controllers;


import com.myapi.dto.UserDTO;
import com.myapi.models.User;
import com.myapi.repository.UserRepository;
import com.myapi.security.services.RefreshTokenService;
import com.myapi.services.UserService;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerNoAuth.class)
public class UserControllerTest {

    @Autowired
    private UserControllerNoAuth uSerControllerNoAuth;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthenticationManager authenticationManager;

    private final String FIND_USER_PATH = "/api/noauth/user";
    private final String FIND_USER_PATH_ID = "/api/noauth/user/{id}";
    private final String SAVE_USER_PATH = "/api/noauth/user/save";
    private final String UPDATE_USER_PATH = "/api/noauth/user/update";


    @BeforeEach
    public void setup(){
        standaloneSetup(this.uSerControllerNoAuth);
    }

    @Test
    public void mustReturn200CodeWhenFindUserByBodyJson() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        when(this.userService.findUserById(1L)).thenReturn(new UserDTO(1l, "", "", "", ""));
        mockMvc.perform(
                get(FIND_USER_PATH)
                .content(String.valueOf(jsonObject))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void mustReturn404WhenNotFindUserByBodyJson() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "123");
        when(this.userService.findUserById(null)).thenReturn(new UserDTO(null, "", "", "", ""));
        mockMvc.perform(
                get(FIND_USER_PATH)
                        .content(String.valueOf(jsonObject))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void mustFindAnUserAndReturn200Code(){
        when(this.userService.findUserById(1L)).thenReturn(new UserDTO(1L, "", "", "", ""));
       given().accept(ContentType.JSON)
               .when()
               .get(FIND_USER_PATH_ID, 1L)
               .then()
               .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void mustReturn404WhenNotFindUser() throws Exception{
        when(this.userService.findUserById(123L)).thenReturn(null);
        mockMvc.perform(
                get(FIND_USER_PATH + "/123")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void mustSaveAnUserAndReturn201Code() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Unit test");
        jsonObject.put("lastName", "jUnit5");
        jsonObject.put("email", "junit@junit.com");
        jsonObject.put("phoneNumber", "555 555 555");
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        User user = Mockito.mock(User.class);
        when(this.userService.saveUser(userDTO)).thenReturn(user);
        mockMvc.perform(
                post(SAVE_USER_PATH)
                        .content(String.valueOf(jsonObject))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void mustUpdateAnUserAndReturn204Cod() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        jsonObject.put("lastName", "jUnit5");
        jsonObject.put("email", "junit@junit.com");
        jsonObject.put("phoneNumber", "555 555 555");
        UserDTO userDTO = Mockito.mock(UserDTO.class);

        userService.updateUser(userDTO);
        mockMvc.perform(
                put(UPDATE_USER_PATH)
                        .content(String.valueOf(jsonObject))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}
