package az.abb.postservice.service.impl;

import az.abb.postservice.model.dto.UserDto;
import az.abb.postservice.model.dto.UserRespDto;
import az.abb.postservice.model.entity.UserEntity;
import az.abb.postservice.repository.UserRepository;
import az.abb.postservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void createUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

    @Override
    public UserRespDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> UserRespDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<UserRespDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserRespDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        var existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            var user = existingUser.get();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(userDto.getRole());

            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }

    }

}