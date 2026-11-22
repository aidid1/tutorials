package my.stiwk2124.userlist.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import jakarta.validation.Valid;
import my.stiwk2124.userlist.model.User;
import my.stiwk2124.userlist.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // 1. READ all users
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // 2. READ one user by ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return repo.findById(id);
    }

    // 3. CREATE new user with Validation
    @PostMapping
    public User createUser(@Valid @RequestBody User newUser) {
        return repo.save(newUser);
    }

    // 4. UPDATE existing user with Validation
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        return repo.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword_hash(updatedUser.getPassword_hash());
                    user.setFirst_name(updatedUser.getFirst_name());
                    user.setLast_name(updatedUser.getLast_name());
                    user.setCountry(updatedUser.getCountry());
                    user.setCategory(updatedUser.getCategory());
                    user.setAffiliation(updatedUser.getAffiliation());
                    user.setOrcid(updatedUser.getOrcid());
                    user.setIs_email_verified(updatedUser.isIs_email_verified());
                    return repo.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID " + id));
    }

    // 5. DELETE a user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        repo.deleteById(id);
        return "User deleted successfully with ID: " + id;
    }

    @GetMapping("/page")
    public Page<User> getUsersPaged(
            @PageableDefault(page = 0, size = 5, sort = "user_id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return repo.findAllPaged(toNativePageable(pageable, "user_id"));
    }

    @GetMapping("/search")
    public Page<User> searchByCategory(
            @RequestParam String category,
            @PageableDefault(size = 5, sort = "user_id") Pageable pageable) {
        return repo.findByCategoryPaged(category, toNativePageable(pageable, "user_id"));
    }

    private Pageable toNativePageable(Pageable pageable, String defaultSortCol) {
        Sort sort = pageable.getSort().isUnsorted()
                ? JpaSort.unsafe(Sort.Direction.ASC, defaultSortCol)
                : Sort.by(pageable.getSort().stream()
                .map(order -> JpaSort.unsafe(order.getDirection(), order.getProperty())
                        .getOrderFor(order.getProperty()))
                .toList());
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

}