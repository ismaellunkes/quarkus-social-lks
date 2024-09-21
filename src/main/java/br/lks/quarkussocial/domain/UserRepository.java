package br.lks.quarkussocial.domain;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findByUsername(String username) {
        var params = Parameters.with("user", username).map();
        PanacheQuery<User> query = find("username = :user", params);
        Optional<User> result = query.firstResultOptional();
        return result;
    }
}
