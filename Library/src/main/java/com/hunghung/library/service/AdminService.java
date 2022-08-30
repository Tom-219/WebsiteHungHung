package com.hunghung.library.service;

import com.hunghung.library.dto.AdminDto;
import com.hunghung.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
