package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.*;

import java.util.*;
import java.util.function.Predicate;

@Service
public class VikingLambdaService {

    public long countByAge(List<Viking> vikings, Predicate<Integer> condition) {
        return vikings.stream()
                .map(Viking::age)
                .filter(condition)
                .count();
    }

    public long countByAppearance(List<Viking> vikings, BeardStyle style, HairColor color) {
        return vikings.stream()
                .filter(v -> v.beardStyle() == style && v.hairColor() == color)
                .count();
    }

    public long countByAxes(List<Viking> vikings, int axeCount) {
        return vikings.stream()
                .filter(v -> v.equipment().stream()
                        .filter(item -> item.name().toLowerCase().contains("axe"))
                        .count() == axeCount)
                .count();
    }

    public Optional<Viking> getRandomTallViking(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> v.heightCm() > 180)
                .findAny();
    }

    public List<Viking> getLegendaryVikings(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> v.equipment().stream()
                        .anyMatch(item -> "Legendary".equalsIgnoreCase(item.quality())))
                .toList();
    }

    public List<Viking> getSortedRedBeards(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> v.hairColor() == HairColor.Red && v.beardStyle() != BeardStyle.CLEAN_SHAVEN)
                .sorted(Comparator.comparingInt(Viking::age))
                .toList();
    }

    public Integer getMaxId(List<Integer> ids) {
        return ids.stream()
                .max(Integer::compare)
                .orElse(0);
    }

    public List<Integer> getEvenIds(List<Integer> ids) {
        return ids.stream()
                .filter(id -> id % 2 == 0)
                .toList();
    }
}