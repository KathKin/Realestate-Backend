package com.example.Laba7.repository;

import com.example.Laba7.model.Property;
import com.example.Laba7.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByCity(String city);
    List<Property> findByCityAndPriceLessThanEqual(String city, BigDecimal maxPrice);
    List<Property> findByType(PropertyType type);
    List<Property> findByRooms(Integer rooms);
    List<Property> findByAgentId(Long agentId);

    @Query("SELECT p FROM Property p WHERE " +
            "(:city IS NULL OR p.city = :city) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:rooms IS NULL OR p.rooms = :rooms)")
    List<Property> searchProperties(@Param("city") String city,
                                    @Param("minPrice") BigDecimal minPrice,
                                    @Param("maxPrice") BigDecimal maxPrice,
                                    @Param("rooms") Integer rooms);
}