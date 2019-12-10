package com.mockito.tests;

import com.cyio.backend.model.Game;
import com.cyio.backend.model.User;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.repository.UserRepository;
import com.cyio.backend.security.UserPrincipal;
import com.cyio.backend.service.GameService;
import com.cyio.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class AdminTests {
    @InjectMocks
    UserService userService;

    @InjectMocks
    GameService gameController;

    @Mock
    UserRepository userRepo;

    @Mock
    GameRepository gameRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteUserAsRegularUser(){
        List<User> users= loadUsers();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(UserPrincipal.create(users.get(0))); //returns chuck
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepo.deleteUserByUserNameOrUserid("Tom", "Tom")).thenReturn(1);

        String result = userService.deleteUser("Tom", UserPrincipal.create(users.get(0)));
        assertEquals(result, "unauthorized user");
    }

    @Test
    public void deleteUserAsSuperUser(){
        List<User> users= loadUsers();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(UserPrincipal.create(users.get(2))); //returns root
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepo.deleteUserByUserNameOrUserid("Tom", "Tom")).thenReturn(1);

        String result = userService.deleteUser("Tom", UserPrincipal.create(users.get(2)));
        assertEquals(result, "Tom deleted");
    }

    @Test
    public void ToggleUserAsSuperUser(){
        List<User> users= loadUsers();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(UserPrincipal.create(users.get(2))); //returns root
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<User> single = loadUsers().subList(0,1);
        when(userRepo.findUserByUserNameOrUserid("Tom", "Tom")).thenReturn(single);

        String result = userService.toggleAdmin("Tom", UserPrincipal.create(users.get(2)));
        assertEquals(result, "Success");
    }

    @Test
    public void ToggleUserAsRegularUser(){
        List<User> users= loadUsers();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(UserPrincipal.create(users.get(0))); //returns root
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<User> single = loadUsers().subList(0,1);
        when(userRepo.findUserByUserNameOrUserid("Tom", "Tom")).thenReturn(single);

        String result = userService.toggleAdmin("Tom", UserPrincipal.create(users.get(0)));
        assertEquals(result, "unauthorized user");
    }

   @Test
   public void deleteGameAsRegularUser(){
       List<User> users= loadUsers();
       List<Game> answer = new ArrayList<Game>();
       answer.add(new Game("invader", "gameid", "Jasan Bern"));

       when(gameRepo.findGameByAboutContaining("invader")).thenReturn(answer);

       when(gameRepo.deleteGameByTitle("invader")).thenReturn(1);

       String result = gameController.deleteGames("invader", UserPrincipal.create(users.get(0)));
       assertEquals("Game does not exist or not authorized", result);
   }

    private List<User> loadUsers(){
        List<User> ret= new ArrayList<>();
        ret.add(new User("chuck", false));
        ret.add(new User("Tom", false));
        ret.add(new User("Root", true));
        return ret;
    }

}
