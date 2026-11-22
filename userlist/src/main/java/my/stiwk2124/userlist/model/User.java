package my.stiwk2124.userlist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`users`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be at least 4 characters")
    @Column(name = "password_hash")
    private String password_hash;

    @NotBlank(message = "First name cannot be empty")
    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "affiliation")
    private String affiliation;

    @Column(name = "country")
    private String country;

    @Pattern(regexp = "student|academic|staff|other", message = "Category must be one of: student, academic, staff, or other")
    @Column(name = "category")
    private String category;

    @Column(name = "orcid")
    private String orcid;

    @Column(name = "is_email_verified")
    private boolean is_email_verified;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    // Default Constructor
    public User() {}

    // Lifecycle Callbacks to automatically handle timestamps
    @PrePersist
    protected void onCreate() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
        if (updated_at == null) {
            updated_at = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword_hash() { return password_hash; }
    public void setPassword_hash(String password_hash) { this.password_hash = password_hash; }

    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public String getAffiliation() { return affiliation; }
    public void setAffiliation(String affiliation) { this.affiliation = affiliation; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getOrcid() { return orcid; }
    public void setOrcid(String orcid) { this.orcid = orcid; }

    public boolean isIs_email_verified() { return is_email_verified; }
    public void setIs_email_verified(boolean is_email_verified) { this.is_email_verified = is_email_verified; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getUpdated_at() { return updated_at; }
    public void setUpdated_at(LocalDateTime updated_at) { this.updated_at = updated_at; }
}