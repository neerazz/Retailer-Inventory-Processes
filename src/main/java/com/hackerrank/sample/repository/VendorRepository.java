package com.hackerrank.sample.repository;

import com.hackerrank.sample.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface VendorRepository  extends JpaRepository<Vendor, Long> {
}
