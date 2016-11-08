package org.springframework.samples.portfolio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.samples.portfolio.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by thinhdd on 10/18/2016.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findUserByUserName(String name);

    User findUserByUserNameAndPassword(String userName, String password);
}
