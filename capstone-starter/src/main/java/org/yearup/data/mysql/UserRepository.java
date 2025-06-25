package org.yearup.data.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    AppUser getUserByUsername(String userName);

    AppUser getByUsername(String username);

    boolean existsByUsername(String username);
}
