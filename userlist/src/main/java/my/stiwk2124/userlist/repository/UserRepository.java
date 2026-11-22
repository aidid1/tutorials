package my.stiwk2124.userlist.repository;

import my.stiwk2124.userlist.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM \"users\"",
            countQuery = "SELECT count(*) FROM \"users\"",
            nativeQuery = true)
    Page<User> findAllPaged(Pageable pageable);

    @Query(value = "SELECT * FROM \"users\" WHERE category = :category",
            countQuery = "SELECT count(*) FROM \"users\" WHERE category = :category",
            nativeQuery = true)
    Page<User> findByCategoryPaged(@Param("category") String category, Pageable pageable);
}