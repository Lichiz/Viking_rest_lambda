package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.model.Viking;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class VikingLambdaService {

    public long countByAgeGreater(List<Viking> vikings, int age) {
        return vikings.stream().filter(v -> v.age() > age).count();
    }

    public long countByAgeLess(List<Viking> vikings, int age) {
        return vikings.stream().filter(v -> v.age() < age).count();
    }

    public long countByAgeEqual(List<Viking> vikings, int age) {
        return vikings.stream().filter(v -> v.age() == age).count();
    }

    public long countByAgeInRange(List<Viking> list, int min, int max) {
        return list.stream().filter(v -> v.age() >= min && v.age() <= max).count();
    }

    public long countByAgeOutOfRange(List<Viking> list, int min, int max) {
        return list.stream().filter(v -> v.age() < min || v.age() > max).count();
    }

    public long countByBeardAndHair(List<Viking> vikings, BeardStyle beard, HairColor hair) {
        return vikings.stream()
                .filter(v -> v.beardStyle() == beard && v.hairColor() == hair)
                .count();
    }

    public long countByAxes(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> {
                    long axesCount = v.equipment().stream()
                            .filter(e -> e.name().equalsIgnoreCase("Axe"))
                            .count();
                    return axesCount == 1 || axesCount == 2;
                })
                .count();
    }

    public Viking findRandomVikingTaller_180(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> v.heightCm() > 180)
                .findAny().orElse(null);
    }

    public List<Viking> findVikingsWithLegendEquip(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> v.equipment().stream()
                        .anyMatch(e -> e.quality().equalsIgnoreCase("Legendary")))
                .toList();
    }

    public List<Viking> getSortedRedBeardedVikings(List<Viking> vikings) {
        return vikings.stream()
                .filter(v -> v.hairColor() == HairColor.Red && v.beardStyle() != BeardStyle.LONG)
                .sorted(Comparator.comparingInt(Viking::age))
                .toList();
    }

    public Integer findMaxId(Integer[] ids) {
        return Arrays.stream(ids)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    public List<Integer> findAllEvenIds(Integer[] ids) {
        return Arrays.stream(ids)
                .filter(id -> id % 2 == 0)
                .toList();
    }
}