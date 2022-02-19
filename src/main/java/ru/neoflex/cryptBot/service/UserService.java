package ru.neoflex.cryptBot.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import ru.neoflex.cryptBot.entity.User;
import ru.neoflex.cryptBot.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User findById(Long id) throws ServiceException {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public boolean isExist(Long id) {
        User user = findById(id);
        return user != null;
    }
}
