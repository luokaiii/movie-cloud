package cn.luokaiii.auth.provider.config.redis;

import cn.luokaiii.auth.api.config.RedisObjectSerializer;
import cn.luokaiii.user.api.model.ModuleResources;
import cn.luokaiii.user.api.model.MovieRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisAuthConfiguration {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean(name = "roleTemplate")
    public RedisTemplate<String, MovieRole> roleTemplate() {
        RedisTemplate<String, MovieRole> template = new RedisTemplate<String, MovieRole>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    @Bean(name = "resourcesTemplate")
    public RedisTemplate<String, ModuleResources> resourcesTemplate() {
        RedisTemplate<String, ModuleResources> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new RedisObjectSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
