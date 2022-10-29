package org.example.shard.model.shard;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findByFirstName(String firstName);
}
