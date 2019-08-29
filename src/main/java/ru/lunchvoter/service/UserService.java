package ru.lunchvoter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lunchvoter.AuthorizedUser;
import ru.lunchvoter.model.User;
import ru.lunchvoter.repository.user.UserRepositoryImpl;
import ru.lunchvoter.to.UserTo;
import ru.lunchvoter.util.UserUtil;

import java.util.List;

@Service("userAuthService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepositoryImpl repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepositoryImpl repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        return repository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        save(UserUtil.updateFromTo(user, userTo));
    }

    public void delete(int id) {
        Assert.isTrue(repository.delete(id), "User with id = " + id + " doesn't exist");
    }

    public User get(int id) {
        return repository.get(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found user with id = " + id));
    }

    public User getByEmail(String email) {
        return repository.getByEmail(email.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Not found user with email = " + email));
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with email " + email + " is not found"));
        return new AuthorizedUser(user);
    }
}
