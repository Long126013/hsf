package com.example.demo.model;

import com.example.demo.validation.ValidationGroups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User extends BaseEntity{

    @NotBlank(message = "Họ và tên không được để trống.", groups = {ValidationGroups.OnRegister.class, ValidationGroups.OnUpdate.class})
    @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự.", groups = {ValidationGroups.OnRegister.class, ValidationGroups.OnUpdate.class})
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Họ và tên chỉ được chứa chữ cái và khoảng trắng.", groups = {ValidationGroups.OnRegister.class, ValidationGroups.OnUpdate.class})
    @Column(name = "full_name", length = 100)
    @Nationalized
    private String fullName;

    @NotBlank(message = "Email không được để trống.", groups = ValidationGroups.OnRegister.class)
    @Email(message = "Email không đúng định dạng.", groups = ValidationGroups.OnRegister.class)
    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống.", groups = ValidationGroups.OnRegister.class)
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự.", groups = ValidationGroups.OnRegister.class)
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    @Column(name = "balance", nullable = false)
    private long balance = 0L;
}
