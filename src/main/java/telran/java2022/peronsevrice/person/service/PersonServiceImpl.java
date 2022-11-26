package telran.java2022.peronsevrice.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
// import java.util.Map;
// import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java2022.peronsevrice.person.dao.PersonRepository;
import telran.java2022.peronsevrice.person.dto.AddressDto;
import telran.java2022.peronsevrice.person.dto.CityPopulationDto;
import telran.java2022.peronsevrice.person.dto.PersonDto;
import telran.java2022.peronsevrice.person.dto.exeptions.PersonNotFoundExeption;
import telran.java2022.peronsevrice.person.model.Address;
import telran.java2022.peronsevrice.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  final PersonRepository personRepository;
  final ModelMapper modelMapper;

  @Override
  @Transactional
  public Boolean addPerson(PersonDto personDto) {
    if (personRepository.existsById(personDto.getId())) {
      return false;
    }
    personRepository.save(modelMapper.map(personDto, Person.class));
    return true;
  }

  @Override
  public PersonDto findPersonById(Integer id) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    return modelMapper.map(person, PersonDto.class);
  }

  @Override
  @Transactional
  public PersonDto removePerson(Integer id) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    personRepository.delete(person);
    return modelMapper.map(person, PersonDto.class);
  }

  @Override
  @Transactional
  public PersonDto updatePersonName(Integer id, String name) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    person.setName(name);
    // personRepository.save(person);
    return modelMapper.map(person, PersonDto.class);
  }

  @Override
  @Transactional
  public PersonDto updatePersonAddress(Integer id, AddressDto address) {
    Person person = personRepository.findById(id).orElseThrow(PersonNotFoundExeption::new);
    person.setAddress(modelMapper.map(address, Address.class));
    // personRepository.save(person);
    return modelMapper.map(person, PersonDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<PersonDto> findPersonsByCity(String city) {
    return personRepository.findByAddressCity(city)
        .map(p -> modelMapper.map(p, PersonDto.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<PersonDto> findPersonsByName(String name) {
    return personRepository.findByName(name)
        .map(p -> modelMapper.map(p, PersonDto.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
    LocalDate from = LocalDate.now().minusYears(minAge);
    LocalDate to = LocalDate.now().minusYears(maxAge);
    return personRepository.findByBirthDateBetween(from, to)
        .map(p -> modelMapper.map(p, PersonDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public Iterable<CityPopulationDto> getCitiesPopulation() {
    // Map<String, Long> population =
    // StreamSupport.stream(personRepository.findAll().spliterator(), false)
    // .collect(Collectors.groupingBy(p -> p.getAddress().getCity(),
    // Collectors.counting()));
    // return population.entrySet().stream()
    // .map(e -> new CityPopulationDto(e.getKey(), e.getValue()))
    // .collect(Collectors.toList());
    return personRepository.getCitiesPopulation();
  }

}
