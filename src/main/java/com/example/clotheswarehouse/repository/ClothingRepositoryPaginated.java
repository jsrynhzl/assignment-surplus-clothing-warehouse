package com.example.clotheswarehouse.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.clotheswarehouse.model.Clothing;

// Defines operations with the Clothing table in the database
// This interface will extend PagingAndSortingRepository, which will allow us to retrieve clothes in pages
@Repository
public interface ClothingRepositoryPaginated extends PagingAndSortingRepository<Clothing, Long> {
}
