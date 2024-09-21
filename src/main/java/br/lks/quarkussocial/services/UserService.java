package br.lks.quarkussocial.services;

import br.lks.quarkussocial.domain.User;
import br.lks.quarkussocial.domain.UserRepository;
import br.lks.quarkussocial.rest.dto.CreateUserRequest;
import br.lks.quarkussocial.rest.dto.UserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final Validator validator;

    @Inject
    public UserService(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional
    public CreateUserRequest create(CreateUserRequest userRequest) throws ConstraintViolationException {

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if(!violations.isEmpty()){
            throw new ErrorsValidatorException(violations);
        }

        Optional<User> userOpt = userRepository.findByUsername(userRequest.getUsername());
        if (userOpt.isPresent()) {
            throw new ErrorsValidatorException("Username already exists.");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setAge(userRequest.getAge());
        userRepository.persist(user);

        return new CreateUserRequest(user.getUsername(), user.getAge());
    }

    public List<UserRequest> listAllUsers() {
        PanacheQuery<User> query = userRepository.findAll();
        List<UserRequest> userRequests = new ArrayList<>();
        query.stream().forEach(q -> {
            userRequests.add(new UserRequest(q.getId(), q.getUsername(), q.getAge()));
        });
        return userRequests;
    }

    @Transactional
    public void delete (Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ErrorsValidatorException("User not found.");
        }
        userRepository.delete(user);
    }

    public UserRequest findById(Long id) {
        Optional<User> user = userRepository.findByIdOptional(id);
        return user.map(value -> new UserRequest(value.getId(), value.getUsername(), value.getAge())).orElse(null);
    }
}
