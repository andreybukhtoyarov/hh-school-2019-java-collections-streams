package tasks;

import common.Person;
import common.Task;
import static common.Util.emptyIfNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  /**
   * Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
   *
   * Избавляемся от ретерна в двух местах.
   * Тем более проверка, как я теперь знаю, на пустую коллекцию для стримов не нужна.
   *
   * @param persons - список персон List<Person>
   * @return список имен пресон без имени фальшивой персоны
   */
  public List<String> getNames(List<Person> persons) {
    return emptyIfNull(persons).stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
  }

  /**
   * ну и различные имена тоже хочется
   *
   * Так как мы сохраняем во множество - не может быть дубликатов,
   * значит distinct() - лишний. Тем более что можно просто persons передать в конструктор Set.
   *
   * @param persons - список персон List<Person>
   * @return список имен пресон без дубликатов
   */
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  /**
   * Для фронтов выдадим полное имя, а то сами не могут
   *
   * Убираем конкатенацию, проверка на налл теперь в одном месте, в целом читаемость улучшена.
   *
   * @param person - объект Персона
   * @return Полное имя персоны.
   */
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  /**
   * словарь id персоны -> ее имя
   *
   * Убираем вложенность, создаем словарь и втом же месте его изменяем и возвращаем из метода.
   *
   * @param persons - коллекция объектов типа Person.
   * @return словарь id персоны -> ее имя.
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return emptyIfNull(persons).stream().distinct().collect(Collectors.toMap(Person::getId, this::convertPersonToString));
  }

  /**
   * есть ли совпадающие в двух коллекциях персоны?
   *
   * Убираем вложенность.
   * У оригинала О(n^2), сейчас O(n).
   *
   * @param persons1 - коллекция типа Person
   * @param persons2 - коллекция типа Person
   * @return true если есть хоть один совпадающий елемент.
   */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> first = new HashSet<>(persons1);
    return persons2.stream().anyMatch(first::contains);
  }

  /**
   * Выглядит вроде неплохо...
   *
   * Улучшаем читабельность.
   *
   * @param numbers - стрим чисел.
   * @return количество четных чисел в стриме.
   */
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Не слабо!");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
