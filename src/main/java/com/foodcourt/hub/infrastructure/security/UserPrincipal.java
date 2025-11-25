package com.foodcourt.hub.infrastructure.security;

import com.foodcourt.hub.domain.model.Role;

public record UserPrincipal(Long id, String email, Role role) {}
