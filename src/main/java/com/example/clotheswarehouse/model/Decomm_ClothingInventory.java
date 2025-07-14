package com.example.clotheswarehouse.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Decomm_ClothingInventory {

    private List<Clothing> clothes = new ArrayList<>();
    private long nextId = 1;

    public void add(Clothing clothing) {
        clothing.setId(nextId++);
        clothes.add(clothing);
    }
}
