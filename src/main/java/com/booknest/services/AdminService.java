package com.booknest.services;

import com.booknest.entities.Admin;
import com.booknest.enums.AccountStatus;
import com.booknest.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin createAdmin(Admin admin) {

        admin.setPassword(
                passwordEncoder.encode(admin.getPassword())
        );

        admin.setCreatedAt(LocalDateTime.now());

        if (admin.getStatus() == null) {
            admin.setStatus(AccountStatus.ACTIVE);
        }

        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Integer id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public Admin updateAdmin(Integer id, Admin admin) {

        Admin existing = getAdminById(id);

        existing.setUsername(admin.getUsername());
        existing.setEmail(admin.getEmail());
        existing.setStatus(admin.getStatus());

        if (admin.getPassword() != null &&
                !admin.getPassword().isBlank()) {

            existing.setPassword(
                    passwordEncoder.encode(admin.getPassword())
            );
        }

        return adminRepository.save(existing);
    }

    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }

    public void createDefaultAdmin() {

        if (adminRepository.findByUsername("admin").isEmpty()) {

            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setEmail("admin@booknest.com");
            admin.setPassword(
                    passwordEncoder.encode("Admin@123")
            );
            admin.setStatus(AccountStatus.ACTIVE);
            admin.setCreatedAt(LocalDateTime.now());

            adminRepository.save(admin);
        }
    }
}