package com.sgedts.wallet.model;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return null;
    }
}
