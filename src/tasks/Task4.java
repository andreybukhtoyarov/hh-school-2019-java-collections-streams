package tasks;

import common.ApiPersonDto;
import common.Person;
import common.Task;
import static common.Util.emptyIfNull;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны уже предоставлен
FYI - DTO = Data Transfer Object
 */
public class Task4 implements Task {

  /**
   * Конвертирует список персон класса Person в список ApiPersonDto
   * @param persons - список персон класса Person
   * @return список персон класса ApiPersonDto
   */
  private List<ApiPersonDto> convert(List<Person> persons) {
    return emptyIfNull(persons).stream().map(Task4::convert).collect(Collectors.toList());
  }

  private static ApiPersonDto convert(Person person) {
    ApiPersonDto dto = new ApiPersonDto();
    dto.setCreated(person.getCreatedAt().toEpochMilli());
    dto.setId(person.getId().toString());
    dto.setName(person.getFirstName());
    return dto;
  }

  @Override
  public boolean check() {
    Person person1 = new Person(1, "Name", Instant.now());
    Person person2 = new Person(2, "Name", Instant.now());
    return List.of(convert(person1), convert(person2))
            .equals(convert(List.of(person1, person2)));
  }
}
