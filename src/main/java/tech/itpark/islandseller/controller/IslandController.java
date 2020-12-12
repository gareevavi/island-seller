package tech.itpark.islandseller.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.itpark.islandseller.manager.IslandManager;
import tech.itpark.islandseller.model.Island;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/islands")
public class IslandController {

    private final IslandManager manager;

    @RequestMapping
    public List<Island> getAll() {
        return manager.getAll();
    }

    @RequestMapping("/{id}")
    public Island getById(@PathVariable long id) {
        return manager.getById(id);
    }

    @RequestMapping("search/{name}")
    public Island search (@PathVariable String name) {
        return manager.search(name);
    }

    @RequestMapping("/country/{country_id}")
    public List<Island> getByCountryId(@PathVariable int country_id) {
        return manager.getByCountryId(country_id);
    }

    @RequestMapping("/region/{region_id}")
    public List<Island> getByRegionId(@PathVariable int region_id) {
        return manager.getByRegionId(region_id);
    }

    @RequestMapping("/{id}/save")
    public Island save(
            @PathVariable int id,
            @RequestParam String name,
            @RequestParam int regionId,
            @RequestParam int countryId,
            @RequestParam long price,
            @RequestParam int size
                       ) {
        return manager.save(new Island(id, name, regionId, countryId, price, size));
    }

    @RequestMapping("/{id}/remove")
    public Island removeById(@PathVariable int id) {
        return manager.removeById(id);
    }
}
