package com.example.clotheswarehouse.controller;

import com.example.clotheswarehouse.model.Clothing;
import com.example.clotheswarehouse.repository.ClothingRepository;
import com.example.clotheswarehouse.repository.ClothingRepositoryPaginated;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/summary")
public class SummaryController {

    private static final int PAGE_SIZE = 5;

    private final ClothingRepository clothingRepository;
    private final ClothingRepositoryPaginated clothingRepositoryPaginated;

    public SummaryController(ClothingRepository clothingRepository,
                             ClothingRepositoryPaginated clothingRepositoryPaginated) {
        this.clothingRepository = clothingRepository;
        this.clothingRepositoryPaginated = clothingRepositoryPaginated;
    }

    @GetMapping
    public String showSummaryPage() {
        return "summary";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        return "admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteClothing(@PathVariable Long id) {
        clothingRepository.deleteById(id);
        return "redirect:/summary/admin";
    }

    // Initial population of clothing list
    @ModelAttribute
    public void clothes(Model model) {
        var clothingPage = clothingRepositoryPaginated.findAll(PageRequest.of(0, PAGE_SIZE));
        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
    }

    @PostMapping("/filter")
    public String filterClothing(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "yearOfCreation", required = false) Integer year,
            Model model) {

        List<Clothing> filteredClothing;

        if (brand != null && !brand.isEmpty() && year != null) {
            filteredClothing = clothingRepository.findByBrandAndYearOfCreation(
                    Clothing.Brand.valueOf(brand), year);
        } else if (brand != null && !brand.isEmpty()) {
            filteredClothing = clothingRepository.findByBrand(
                    Clothing.Brand.valueOf(brand));
        } else if (year != null) {
            filteredClothing = clothingRepository.findByYearOfCreation(year);
        } else {
            filteredClothing = (List<Clothing>) clothingRepository.findAll();
        }

        model.addAttribute("clothingInventory", filteredClothing);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("sortOrder", null); // reset sort
        model.addAttribute("selectedBrand", brand); // For retaining dropdown
        model.addAttribute("selectedYear", year);   // For retaining input
        return "summary";
    }

    @PostMapping("/admin/filter")
    public String filterClothingInAdmin(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "yearOfCreation", required = false) Integer year,
            Model model) {

        List<Clothing> filteredClothing;

        if (brand != null && !brand.isEmpty() && year != null) {
            filteredClothing = clothingRepository.findByBrandAndYearOfCreation(
                    Clothing.Brand.valueOf(brand), year);
        } else if (brand != null && !brand.isEmpty()) {
            filteredClothing = clothingRepository.findByBrand(
                    Clothing.Brand.valueOf(brand));
        } else if (year != null) {
            filteredClothing = clothingRepository.findByYearOfCreation(year);
        } else {
            filteredClothing = (List<Clothing>) clothingRepository.findAll();
        }

        model.addAttribute("clothingInventory", filteredClothing);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("sortOrder", null); // reset sort
        model.addAttribute("selectedBrand", brand); // For retaining dropdown
        model.addAttribute("selectedYear", year);   // For retaining input
        return "admin";
    }

    // Pagination switch
    @GetMapping("/switchPage")
    public String switchPage(
            @RequestParam("pageToSwitch") Optional<Integer> pageToSwitch,
            @RequestParam(name = "sortOrder", required = false) String sortOrder,
            Model model) {

        int page = pageToSwitch.orElse(0);
        if (page < 0) page = 0;

        PageRequest pageRequest;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("brand").descending());
        } else if ("asc".equalsIgnoreCase(sortOrder)) {
            pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("brand").ascending());
        } else {
            pageRequest = PageRequest.of(page, PAGE_SIZE); // no sort
        }

        var clothingPage = clothingRepositoryPaginated.findAll(pageRequest);

        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
        model.addAttribute("sortOrder", sortOrder); // retain sort order for pagination links

        return "summary";
    }

    @GetMapping("/admin/switchPage")
    public String switchAdminPage(
            @RequestParam("pageToSwitch") Optional<Integer> pageToSwitch,
            @RequestParam(name = "sortOrder", required = false) String sortOrder,
            Model model) {

        int page = pageToSwitch.orElse(0);
        if (page < 0) page = 0;

        PageRequest pageRequest;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("brand").descending());
        } else if ("asc".equalsIgnoreCase(sortOrder)) {
            pageRequest = PageRequest.of(page, PAGE_SIZE, Sort.by("brand").ascending());
        } else {
            pageRequest = PageRequest.of(page, PAGE_SIZE); // no sort
        }

        var clothingPage = clothingRepositoryPaginated.findAll(pageRequest);

        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
        model.addAttribute("sortOrder", sortOrder); // retain sort order for pagination links

        return "admin";
    }

    @GetMapping("/sort")
    public String sortClothingByBrand(@RequestParam("order") String order, Model model) {
        Sort sort = "asc".equalsIgnoreCase(order)
                ? Sort.by("brand").ascending()
                : Sort.by("brand").descending();

        var clothingPage = clothingRepositoryPaginated.findAll(PageRequest.of(0, PAGE_SIZE, sort));

        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
        model.addAttribute("sortOrder", order); // remember sort order in pagination

        return "summary";
    }

    @GetMapping("/admin/sort")
    public String sortClothingByBrandInAdmin(@RequestParam("order") String order, Model model) {
        Sort sort = "asc".equalsIgnoreCase(order)
                ? Sort.by("brand").ascending()
                : Sort.by("brand").descending();

        var clothingPage = clothingRepositoryPaginated.findAll(PageRequest.of(0, PAGE_SIZE, sort));

        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
        model.addAttribute("sortOrder", order); // remember sort order in pagination

        return "admin";
    }

    @GetMapping("/clearFilters")
    public String clearFilters(Model model) {
        var clothingPage = clothingRepositoryPaginated.findAll(
                PageRequest.of(0, PAGE_SIZE, Sort.by("id").ascending()));
        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
        model.addAttribute("sortOrder", null);
        return "summary";
    }

    @GetMapping("/admin/clearFilters")
    public String clearAdminFilters(Model model) {
        var clothingPage = clothingRepositoryPaginated.findAll(PageRequest.of(0, PAGE_SIZE));
        model.addAttribute("clothingInventory", clothingPage.getContent());
        model.addAttribute("currentPage", clothingPage.getNumber());
        model.addAttribute("totalPages", clothingPage.getTotalPages());
        return "admin";
    }
}
