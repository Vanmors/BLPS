package com.email.service.emailservice.repository;

import com.email.service.emailservice.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailMessage, Long> {
}
