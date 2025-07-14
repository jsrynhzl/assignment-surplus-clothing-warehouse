package com.example.clotheswarehouse.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.clotheswarehouse.model.Clothing;
import com.example.clotheswarehouse.model.Clothing.Brand;

// Interface that defines operations with the fighter table in the database
@Repository
public interface ClothingRepository extends CrudRepository<Clothing, Long> {
    List<Clothing> findByBrand(Brand brand);
    List<Clothing> findByYearOfCreation(int yearOfCreation);
    List<Clothing> findByBrandAndYearOfCreation(Clothing.Brand brand, int year);
}