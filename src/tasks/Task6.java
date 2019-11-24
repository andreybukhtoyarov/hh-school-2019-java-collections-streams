package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько,
 таких строк так же будет несколько
 */
public class Task6 implements Task {
  /**
   * Выдает множество строк вида "Имя - регион".
   * @param persons - коллекция персон Person.
   * @param personAreaIds - ассоциативный массив, сопоставляющий каждой персоне множество id регионов
   * @param areas - коллекция всех регионов.
   * @return множество строк вида "Имя - регион".
   */
  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {
    Set<String> result = new HashSet<>();
    if (persons != null && personAreaIds != null && areas != null) {
      persons.forEach(
              person -> personAreaIds.get(person.getId()).forEach(
                      personAreaId -> result.add(
                              String.format(
                                      "%s - %s",
                                      person.getFirstName(),
                                      areas.stream()
                                              .filter(area -> area.getId().equals(personAreaId))
                                              .findFirst().orElse(new Area(-1, "noExistArea"))
                                              .getName()
                              )
                      )
              )
      );
    }
    return result;
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
            new Person(1, "Oleg", Instant.now()),
            new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
            .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
