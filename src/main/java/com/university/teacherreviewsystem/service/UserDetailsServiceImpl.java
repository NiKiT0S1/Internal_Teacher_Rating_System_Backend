/**
 * Назначение: Загрузка информации пользователей
 * берет имя пользователя, ищет соответствующего пользователя в базе данных,
 * и, если находит, создает объект UserDetails,
 * который Spring Security использует для аутентификации и авторизации пользователя
 */

package com.university.teacherreviewsystem.service;

import com.university.teacherreviewsystem.model.User;
import com.university.teacherreviewsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
