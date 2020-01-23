package cn.hb0730.spring.boot.oauth2.server.config;

import cn.hb0730.spring.boot.oauth2.server.service.impl.SecurityAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>安全服务配置（Spring Security http URL拦截保护）：<br>
 * URL强制拦截保护服务，可以配置哪些路径不需要保护，哪些需要保护。默认全都保护<br>
 * 继承了WebSecurityConfigurerAdapter之后，再加上几行代码，我们就能实现以下的功能：<br>
 * 1、要求用户在进入你的应用的任何URL之前都进行验证<br>
 * 2、创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户<br>
 * 3、启用HTTP Basic和基于表单的验证<br>
 * 4、Spring Security将会自动生成一个登陆页面和登出成功页面<br>
 * `@EnableWebSecurity注解以及WebSecurityConfigurerAdapter一起配合提供基于web的security。 继承了WebSecurityConfigurerAdapter之后，再加上几行代码，我们就能实现以下的功能：
 * 1、要求用户在进入你的应用的任何URL之前都进行验证
 * 2、创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
 * 3、启用HTTP Basic和基于表单的验证
 * 4、Spring Security将会自动生成一个登陆页面和登出成功页面
 * 默认页面：
 * 登录页面：/login
 * 注销页面：/login?logout
 * 错误页面：/login?error
 * 与ResourceServerConfigurerAdapter区别
 * 1、ResourceServerConfigurerAdapter被配置为不同的端点（参见antMatchers），而WebSecurityConfigurerAdapter不是。
 * 这两个适配器之间的区别在于，RealServServer配置适配器使用一个特殊的过滤器来检查请求中的承载令牌，以便通过OAuth2对请求进行认证。
 * WebSecurityConfigurerAdapter适配器用于通过会话对用户进行身份验证（如表单登录）
 * 2、WebSecurityConfigurerAdapter是默认情况下spring security的http配置，
 * ResourceServerConfigurerAdapter是默认情况下spring security oauth2的http配置
 * 在ResourceServerProperties中，定义了它的order默认值为SecurityProperties.ACCESS_OVERRIDE_ORDER - 1;是大于1的,
 * 即WebSecurityConfigurerAdapter的配置的拦截要优先于ResourceServerConfigurerAdapter，优先级高的http配置是可以覆盖优先级低的配置的。
 * 某些情况下如果需要ResourceServerConfigurerAdapter的拦截优先于WebSecurityConfigurerAdapter需要在配置文件中添加
 * security.oauth2.resource.filter-order=99
 * </p>
 *
 * @author bing_huang 
 * @since V1.1
 */
@Configuration
//@EnableWebSecurity  // 创建了一个WebSecurityConfigurerAdapter，且自带了硬编码的order=3,使用spring security而不是auth
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityAuthenticationProvider provider;

    /**
     * 如若需从数据库动态判断权限则实现 AccessDecisionManager
     * 允许对特定的http请求基于安全考虑进行配置,默认情况下，适用于所有的请求，
     * 但可以使用requestMatcher(RequestMatcher)或者其它相似的方法进行限制
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.requestMatchers().antMatchers("/**");
        // 无需认证
         http.authorizeRequests().antMatchers("/login", "/oauth/**","/actuator/**").permitAll()
                .anyRequest()
                // 指定任何经过身份验证的用户都允许URL。
                .authenticated();
        // 表单登录，无需权限
        http.formLogin().loginPage("/login").failureForwardUrl("/login?error=true").permitAll();
        //需要认证权限
        http.logout().invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout");
       // 异常处理
        http.exceptionHandling().accessDeniedPage("/403");

        // 设置跨域问题
        http.cors().and().csrf().disable();
        http.sessionManagement().invalidSessionUrl("/login");
        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);

    }


    /**
     * 如果有要忽略拦截校验的静态资源，在此处添加
     * 忽略任何以”/resources/”开头的请求，这和在XML配置http@security=none的效果一样
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    /**
     * 系统安全用户验证模式：
     * 1、使用内存模式创建验证
     * 2、使用数据库创建验证，实现userDetailsService接口即可
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 将验证过程交给自定义
        auth.authenticationProvider(provider);
        // auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        // 内存创建用户：写死不利于项目实际应用
        // 验证的时候就是通过创建的用户名、密码、角色进行验证的
        // 创建一个用户名是“user”，密码是“password”，角色是“ROLE_USER”的用户
        // 创建一个用户名是“admin”，密码是“123456”，角色是“ROLE_ADMIN以及ROLE_USER”的用户
        //auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")  // 在内存中的验证(memory authentication)叫作”user”的用户
        //.and().withUser("admin").password("123456").roles("ADMIN", "USER") ;                  // 在内存中的验证(memory authentication)叫作”admin”的管理员用户
    }

    /**
     * 不定义没有password grant_type即密码授权模式（总共四种授权模式：授权码、implicat精简模式、密码、client credentials）
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    /**
     * <p>
     * 密码加密器:将用户密码进行加密
     * </p>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt进行密码的hash
        return new BCryptPasswordEncoder();
    }

}
