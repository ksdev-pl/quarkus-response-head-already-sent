package pl.ksdev.qadmin.user;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import pl.ksdev.qadmin.shared.PasswordEncoder;
import pl.ksdev.qadmin.shared.StrictTransactional;
import pl.ksdev.qadmin.user.dto.UserDto;
import pl.ksdev.qadmin.user.dto.UserForm;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    List<UserDto> getAllUsers() {
        return userRepository.listAll(Sort.ascending("id")).stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }

    UserDto getUser(long id) {
        var user = getUserOrThrow(id);
        return new UserDto(user);
    }

    @StrictTransactional
    UserDto createUser(UserForm userForm) {
        var user = new User(
            userForm.username(),
            passwordEncoder.encode(userForm.password())
        );
        userRepository.persistAndFlush(user);
        return new UserDto(user);
    }

    @StrictTransactional
    UserDto updateUser(long id, UserForm userForm) {
        var user = getUserOrThrow(id);
        user.setUsername(userForm.username());
        user.setPassword(passwordEncoder.encode(userForm.password()));
        userRepository.persistAndFlush(user);
        return new UserDto(user);
    }

    @StrictTransactional
    void deleteUser(long id) {
        var user = getUserOrThrow(id);
        userRepository.delete(user);
    }

    private User getUserOrThrow(long id) {
        return userRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}
