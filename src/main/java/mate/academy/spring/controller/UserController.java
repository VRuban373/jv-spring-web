package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.UserResponseDto;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserDtoMapper;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserDtoMapper userDtoMapper;

    @Autowired
    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/inject")
    public String injectMockUsers() {
        User john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        User emily = new User();
        emily.setFirstName("Emily");
        emily.setLastName("Stone");
        User hugh = new User();
        hugh.setFirstName("Hugh");
        hugh.setLastName("Dane");
        userService.add(john);
        userService.add(emily);
        userService.add(hugh);
        return "Users are injected!";
    }

    @GetMapping("/{userId}")
    public UserResponseDto get(@PathVariable Long userId) {
        return userDtoMapper.parse(userService.get(userId));
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService
                .getAll()
                .stream()
                .map(userDtoMapper::parse)
                .collect(Collectors.toList());
    }
}