package com.example.demo.Repository;

import com.example.demo.Model.PrenotaCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotaCallRepository extends JpaRepository<PrenotaCall, Long> {
}
