/**
 * Created by : Alan Nascimento on 4/2/2022
 */
package com.myapi.services;

import com.myapi.ApplicationConfigTest;
import com.myapi.dto.UserDTO;
import com.myapi.models.User;
import com.myapi.repository.UserRepository;
import com.myapi.services.exceptions.UserBadRequestException;
import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;




import java.util.Optional;

import static org.mockito.Mockito.when;

@DisplayName("UserServiceTest")
public class UserServiceTest extends ApplicationConfigTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test()
    public void callBadRequestWhenFirstNameorLastNameIsEmpty()  {
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        Mockito.doThrow(new UserBadRequestException("")).when(userDTO).getFirstName();
        Mockito.doThrow(new UserBadRequestException("")).when(userDTO).getLastName();
    }

    @Test
    public void mustSaveAnUser()  {
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        when(userDTO.getFirstName()).thenReturn("Name test");
        when(userDTO.getLastName()).thenReturn("Last Name test");
        userService.saveUser(userDTO);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void mustUpdateAnUserButOnlyChangeLastNamePhoneAndEmail(){
        UserDTO userDTO = Mockito.mock(UserDTO.class);
        User user = Mockito.mock(User.class);
        when(userDTO.getId()).thenReturn(1L);
        when(userRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(Optional.of(user));
        userService.updateUser(userDTO);
        Mockito.verify(this.userRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.userRepository, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
    }
}
