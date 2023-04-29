package com.yellow.test.service.user.details;

import com.yellow.test.config.security.additional.CustomUserDetails;
import com.yellow.test.entity.User;
import com.yellow.test.exception.Code;
import com.yellow.test.exception.ServiceRuntimeException;
import com.yellow.test.repository.UserRepository;
import com.yellow.test.service.msg.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MsgService msgService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = this.userRepository.findByUsername(email);

        if (user == null)
            throw new ServiceRuntimeException(msgService.msg(Code.C_1001.getMsg(), email), Code.C_1001.getValue());

        return new CustomUserDetails(
                user.getUuid(),
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                AuthorityUtils.createAuthorityList(user.getAuthority().toString())
        );

    }

}
