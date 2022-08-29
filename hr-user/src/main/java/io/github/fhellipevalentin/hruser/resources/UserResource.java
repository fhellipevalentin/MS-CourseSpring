package io.github.fhellipevalentin.hruser.resources;


import io.github.fhellipevalentin.hruser.entities.User;
import io.github.fhellipevalentin.hruser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository repository;

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = repository.findById(id).get();
        return ResponseEntity.ok(obj);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        User obj = repository.findByEmail(email);
        return ResponseEntity.ok(obj);
    }
}

