package com.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by j-h.lee on 2015-12-30.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuserName(String userName);

}
