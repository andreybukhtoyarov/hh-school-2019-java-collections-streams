package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, затем по имени, а потом по дате создания
 */
public class Task3 implements Task {

  /**
   * Сортирует, переданную в качестве параметра метода, коллекцию обобщенного типа Person
   * по фамилии, если фамилии совпадают - по имени, а если и имена совпадатю - по дате создания.
   * @param persons - коллекция обобщенного типа Person
   * @return список обобщенного типа Person
   */
  private List<Person> sort(Collection<Person> persons) {
    return persons != null ? persons.stream()
            .sorted(
                    Comparator
                            .comparing(Person::getSecondName)
                            .thenComparing(Person::getFirstName)
                            .thenComparing(Person::getCreatedAt)
            )
            .collect(Collectors.toList())
            : Collections.emptyList();
  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    List<Person> persons = List.of(
            new Person(1, "Oleg", "Ivanov", time),
            new Person(2, "Vasya", "Petrov", time),
            new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
            new Person(4, "Oleg", "Ivanov", time.plusSeconds(1))
    );
    List<Person> sortedPersons = List.of(
            new Person(1, "Oleg", "Ivanov", time),
            new Person(4, "Oleg", "Ivanov", time.plusSeconds(1)),
            new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
            new Person(2, "Vasya", "Petrov", time)
    );
    return sortedPersons.equals(sort(persons));
  }
}
