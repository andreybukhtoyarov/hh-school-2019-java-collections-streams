package tasks;

import common.ApiPersonDto;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Задача 5
Расширим предыдущую задачу
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 implements Task {

  /**
   * Выдает список персон ApiPersonDto с проставленными areaId.
   * @param persons - список персон Person.
   * @param personAreaIds - ассоциативный массив, сопоставляющий id каждой персоны и id региона.
   * @return список персон ApiPersonDto.
   */
  private List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {
    return persons != null && personAreaIds != null ? persons.stream()
            .map(
                    person -> convert(
                            person, personAreaIds.get(person.getId())
                    )
            ).collect(Collectors.toList())
            : Collections.emptyList();
  }

  private static ApiPersonDto convert(Person person, Integer areaId) {
    ApiPersonDto dto = new ApiPersonDto();
    dto.setCreated(person.getCreatedAt().toEpochMilli());
    dto.setId(person.getId().toString());
    dto.setName(person.getFirstName());
    dto.setAreaId(areaId);
    return dto;
  }

  @Override
  public boolean check() {
    Person person1 = new Person(1, "Name", Instant.now());
    Person person2 = new Person(2, "Name", Instant.now());
    Map<Integer, Integer> personAreaIds = Map.of(1, 1, 2, 2);
    return List.of(convert(person1, 1), convert(person2, 2))
            .equals(convert(List.of(person1, person2), personAreaIds));
  }
}
