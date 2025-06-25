package org.yearup.data.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
