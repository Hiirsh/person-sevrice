package telran.java2022.peronsevrice.person.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import telran.java2022.peronsevrice.person.dto.CityPopulationDto;
import telran.java2022.peronsevrice.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

  @Query("select p from Person p where p.name=?1")
  Stream<Person> findByName(String name);

  @Query("select p from Person p where p.address.city=:city")
  Stream<Person> findByAddressCity(@Param("city") String city);

  Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

  @Query("select new telran.java2022.peronsevrice.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
  List<CityPopulationDto> getCitiesPopulation();
}
