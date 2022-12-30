package com.thiencode.springbootsecurityhibernatejwt.servie;

import com.thiencode.springbootsecurityhibernatejwt.model.CustomUserDetail;
import com.thiencode.springbootsecurityhibernatejwt.repository.UserRepository;
import com.thiencode.springbootsecurityhibernatejwt.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return new CustomUserDetail(user);
    }
}
