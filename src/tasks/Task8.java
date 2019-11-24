package tasks;

import common.Person;
import common.Task;

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
   * Ну тут, вроде, норм, но можно чуток подправить.
   *
   * @param persons - список персон List<Person>
   * @return список имен пресон без имени фальшивой персоны
   */
  public List<String> getNames(List<Person> persons) {
    return persons != null && !persons.isEmpty() ? persons.stream()
            .skip(1).map(Person::getFirstName).collect(Collectors.toList())
            : Collections.emptyList();
  }

  /**
   * ну и различные имена тоже хочется
   *
   * Так как мы сохраняем во множество - не может быть дубликатов,
   * значит distinct() - лишний. Тем более что можно просто persons передать в конструктор Set.
   * Не было проверки входных параметров.
   *
   * @param persons - список персон List<Person>
   * @return список имен пресон без дубликатов
   */
  public Set<String> getDifferentNames(List<Person> persons) {
    return persons != null && !persons.isEmpty() ? new HashSet<>(getNames(persons)) : Collections.emptySet();
  }

  /**
   * Для фронтов выдадим полное имя, а то сами не могут
   *
   * Убираем конкатенацию, "причесывам" имена для удобства фронтов
   * (подгоняем шрифт под строчные буквы и делаем заглавными первые буквы слов).
   *
   * @param person - объект Персона
   * @return Полное имя персоны.
   */
  public String convertPersonToString(Person person) {
    String name = "";
    if (person != null) {
      name = String.format("%s %s %s",
              person.getSecondName() != null ? person.getSecondName() : "",
              person.getFirstName() != null ? person.getFirstName() : "",
              person.getMiddleName() != null ? person.getMiddleName() : ""
      ).trim().toLowerCase();
    }
    return person != null ? Stream.of(name.split(" "))
            .map(
                    word -> word.replaceFirst(
                            "[а-я|a-z]{1}",
                            String.valueOf(Character.toUpperCase(word.charAt(0)))
                    )
            )
            .collect(Collectors.joining(" "))
            : "";
  }

  /**
   * словарь id персоны -> ее имя
   *
   * Зачем в мапе начальная вместимость 1? Это наверняка опечатка!
   * Можно задать начальную вместимость - длина коллекции + 25%.
   * equals класса Person содержит проверку по id, значит можно воспользоваться Set-ом,
   * вместо проверок в цикле if(!map.containsKey(person.getId()).
   * Таким образом уберем вложенность - улучшим читабельность текста программы.
   *
   * @param persons - коллекция объектов типа Person.
   * @return словарь id персоны -> ее имя.
   */
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>();
    if (persons != null && !persons.isEmpty()) {
      Set<Person> uniquePersons = new HashSet<>(persons);
      uniquePersons.forEach(person -> map.put(person.getId(), convertPersonToString(person)));
    }
    return map;
  }

  /**
   * есть ли совпадающие в двух коллекциях персоны?
   *
   * Ну тут разве что "бряк" добавить да проверку на налл.
   *
   * @param persons1 - коллекция типа Person
   * @param persons2 - коллекция типа Person
   * @return true если есть хоть один совпадающий елемент.
   */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    boolean has = false;
    if (persons1 != null && persons2 != null) {
      for (Person person1 : persons1) {
        for (Person person2 : persons2) {
          if (person1.equals(person2)) {
            has = true;
            break;
          }
        }
        if (has) {
          break;
        }
      }
    }
    return has;
  }

  /**
   * Выглядит вроде неплохо...
   *
   * Ну почти неплохо, добвать .count() в конец стрима и совсем будет хорошо.
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
