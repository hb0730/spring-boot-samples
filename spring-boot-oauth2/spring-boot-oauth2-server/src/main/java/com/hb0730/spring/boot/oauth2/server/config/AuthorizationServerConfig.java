package com.hb0730.spring.boot.oauth2.server.config;

import com.hb0730.spring.boot.oauth2.server.error.WebResponseExceptionTranslator;
import com.hb0730.spring.boot.oauth2.server.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * <p>
 * 认证服务器配置
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);
    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    private AuthenticationManager authenticationManager;
    private RedisConnectionFactory redisConnectionFactory;
    private DataSource dataSource;
    private WebResponseExceptionTranslator webResponseExceptionTranslator;
    private UserDetailsServiceImpl userDetailsService;
    private PasswordEncoder passwordEncoder;
    public AuthorizationServerConfig(AuthenticationManager authenticationManager, RedisConnectionFactory redisConnectionFactory,
                                     DataSource dataSource, WebResponseExceptionTranslator webResponseExceptionTranslator,
                                     UserDetailsServiceImpl userDetailsService,PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.dataSource = dataSource;
        this.webResponseExceptionTranslator = webResponseExceptionTranslator;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder=passwordEncoder;
    }

    /**
     * redis 存储
     */
    @Bean
    public TokenStore tokenStore() {
        // token 相关信息保存在 redis 中
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 客户端信息配置在数据库
     */
    @Bean
    public JdbcClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);

    }

    /**
     * tokenKey的访问权限表达式配置
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * 授权端点开放
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
        logger.info("================AuthorizationServerSecurityConfigurer 已启动================");
    }

    /**
     * 配置客户端详情信息(Client Details),客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。<br>
     * clientId：（必须的）用来标识客户的Id。<br>
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。<br>
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。<br>
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。<br>
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）<br>
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 客户端信息保存在数据库中：表 oauth_client_details
        // 如果oauth_client_details表中将autoApprove设置为true，
        // 这样我们就不会重定向和提升为手动批准任何范围
        clients.withClientDetails(clientDetails());
//        clients.inMemory()//配置内存中，也可以是数据库
//                .withClient("awbeci")//clientid
//                .secret("awbeci-secret")
//                .accessTokenValiditySeconds(3600)//token有效时间  秒
//                .authorizedGrantTypes("refresh_token", "password", "authorization_code")//token模式
//                .scopes("all")//限制允许的权限配置
        logger.info("======================ClientDetailsServiceConfigurer 已启动=============================");;
    }

    /**
     *  用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)<br>
     *  tokenStore：采用redis储存<br>
     *  authenticationManager:身份认证管理器, 用于"password"授权模式<br>
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //开启密码授权类型
        endpoints.authenticationManager(authenticationManager);
        //配置token存储方式
        endpoints.tokenStore(tokenStore());
        //要使用refresh_token的话，需要额外配置userDetailsService
        endpoints.userDetailsService(userDetailsService);
        //自定义登录或者鉴权失败时的返回信息
        endpoints.exceptionTranslator(webResponseExceptionTranslator);
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        // 自定义TokenServices
//        endpoints.tokenServices();
        // 配置JwtAccessToken转换器
//        endpoints.accessTokenConverter();
        logger.info("===============AuthorizationServerEndpointsConfigurer已启动=============");
    }
}
