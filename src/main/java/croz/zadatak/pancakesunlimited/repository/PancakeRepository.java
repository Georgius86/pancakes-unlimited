package croz.zadatak.pancakesunlimited.repository;

import croz.zadatak.pancakesunlimited.domain.Pancake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PancakeRepository extends JpaRepository<Pancake, Long> {
}
