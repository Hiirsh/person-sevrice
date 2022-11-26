package telran.java2022.peronsevrice.person.service;

import telran.java2022.peronsevrice.person.dto.AddressDto;
import telran.java2022.peronsevrice.person.dto.CityPopulationDto;
import telran.java2022.peronsevrice.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);
	
	PersonDto findPersonById(Integer id);
	
	PersonDto removePerson(Integer id);
	
	PersonDto updatePersonName(Integer id, String name);
	
	PersonDto updatePersonAddress(Integer id, AddressDto addressDto);
	
	Iterable<PersonDto> findPersonsByCity(String city);
	
	Iterable<PersonDto> findPersonsByName(String name);
	
	Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge);
	
	Iterable<CityPopulationDto> getCitiesPopulation();
}