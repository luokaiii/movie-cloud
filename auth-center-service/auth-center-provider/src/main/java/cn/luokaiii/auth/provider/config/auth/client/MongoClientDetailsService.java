package cn.luokaiii.auth.provider.config.auth.client;

import cn.luokaiii.user.api.model.MovieClientDetails;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MongoClientDetailsService implements ClientDetailsService {

    private Map<String, MovieClientDetails> clientDetailsMap;

    public MongoClientDetailsService(MongoTemplate mongoTemplate) {
        clientDetailsMap = mongoTemplate.findAll(MovieClientDetails.class)
                .stream().collect(Collectors.toMap(MovieClientDetails::getClientId, val -> val));
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        MovieClientDetails details = clientDetailsMap.get(clientId);
        if (details == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return new CustomClientDetails(details);
    }

}
