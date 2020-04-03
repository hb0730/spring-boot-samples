package cn.hb0730.spring.security.sample.service;

import cn.hb0730.spring.security.sample.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        if ("Administrator".equals(username)) {
//            User.UserBuilder user = User.builder();
//            user.authorities("select:query", "ROLE_ADMIN");
//            user.username("Administrator");
//            user.password(new BCryptPasswordEncoder().encode("123456"));
            LoginUser user = new LoginUser(username, new BCryptPasswordEncoder().encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("select:query,ROLE_ADMIN"));
            return user;
        }
        return null;
    }
}
