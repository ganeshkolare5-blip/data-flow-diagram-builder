package com.internship.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internship.tool.entity.DfdRecord;

import java.util.List;

@Repository
public interface DfdRecordRepository extends JpaRepository<DfdRecord, Long> {

    List<DfdRecord> findByDeletedFalse();

    List<DfdRecord> findByTitleContainingIgnoreCaseAndDeletedFalse(String title);
}
