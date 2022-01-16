package com.sbrf.reboot.collections;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsTest {


    /*
     * Задача.
     * Имеется список лучших студентов вуза.
     *
     * 1. Иванов
     * 2. Петров
     * 3. Сидоров
     *
     * В новом семестре по результатам подсчетов оценок в рейтинг на 1 место добавился новый студент - Козлов.
     * Теперь в рейтинге участвуют 4 студента.
     * (Предполагаем что в рейтинг можно попасть только получив достаточное количество балов, что бы занять 1 место).
     *
     * Вопрос.
     * Какую коллекцию из Collections framework вы предпочтете для текущего хранения и использования списка студентов?
     *
     * Проинициализируйте students, добавьте в нее 4 фамилии студентов что бы тест завершился успешно.
     */
    @Test
    public void addStudentToRating() {
        // Вопрос.
        //     * Какую коллекцию из Collections framework вы предпочтете для текущего хранения и использования списка студентов?

        // Ответ: в задаче требуется вставить элемент в начало коллекции,
        // для добавления элемента по индексу потребуется метод вставки по индексу, присутствующий в интерфейсе List
        // ArrayList - работает с массивами, а для вставки элемента в начало массива - это наихудший случай,
        // так как все остальные элементы придется смещать вправо, что займет O(n-1)
        // В данной задаче логичнее использовать LinkedList, так как вставка в начало осуществляется за O(1)
        List<String> students = new LinkedList<>();
        students.add("Иванов");
        students.add("Петров");
        students.add("Сидоров");
        students.add(0,"Козлов");
        //...

        assertEquals(4, students.size());
    }

    /*
     * Задача.
     * Вы коллекционируете уникальные монеты.
     * У вас имеется специальный бокс с секциями, куда вы складываете монеты в хаотичном порядке.
     *
     * Вопрос.
     * Какую коллекцию из Collections framework вы предпочтете использовать для хранения монет в боксе.
     *
     * Проинициализируйте moneyBox, добавьте в нее 10 монет что бы тест завершился успешно.
     */
    @Test
    public void addMoneyToBox() {
        //* Вопрос.
        //     * Какую коллекцию из Collections framework вы предпочтете использовать для хранения монет в боксе.
        // Ответ: Так как монеты уникальные, разумно использовать интерфейс Set,
        // а так как порядок не важен, для скорости работы будет оптимально использовать реализацию HashSet
        Set<Integer> moneyBox = new HashSet<>();
        for (int i = 0; i < 10; i++){
            moneyBox.add(i);
        }
        //...

        assertEquals(10, moneyBox.size());
    }

    /*
     * Задача.
     * Имеется книжная полка.
     * Периодически вы берете книгу для чтения, затем кладете ее на свое место.
     *
     * Вопрос.
     * Какую коллекцию из Collections framework вы предпочтете использовать для хранения книг.
     *
     * Проинициализируйте bookshelf, добавьте в нее 3 книги что бы тест завершился успешно.
     */
    @Test
    public void addBookToShelf() {
        //* Вопрос.
        //     * Какую коллекцию из Collections framework вы предпочтете использовать для хранения книг.
        // Ответ:
        // Если в рамках этой задачи предположить что к книгам будут обращаться по их месту на полке,
        // то нас удовлетворит ArrayList
        // Если же поставить задачу что к книгам будут обращаться по названию,
        // то логичнее использовать структуру Map<"Имя книги",Book>

        class Book {
            String name;
            Book(String name){
                this.name = name;
            }
        }
        Book book1 = new Book("Преступление и наказание");
        Book book2 = new Book("Thinking in Java");
        Book book3 = new Book("Техническая термодинамика");
        // option 1
        //List<Book> bookshelf = Arrays.asList(book1,book2,book3);
        // option 2
        Map<String,Book> bookshelf = new HashMap<>();
        bookshelf.put(book1.name,book1);
        bookshelf.put(book2.name,book2);
        bookshelf.put(book3.name,book3);
        //...

        assertEquals(3, bookshelf.size());
    }

    /*
    * Задача 5+.
    * Требуется хранить список контактов, у каждого контакта (ФИО) может быть сколько угодно номеров
    * Периодически трубется получать список номеров у любого из контактов, обращаясь по имени контакта
    * Имена контактов должны хранится в отсортированном по алфавиту (лексографически)
    *
    * Вопрос.
    * Какую коллекцию из Collections framework вы предпочтете для организации списка контактов.
    *
    * Ответ.
    * Так как имена требуется хранить в отсортированном виде,
    * а при обращении по имени нужно получить список номеров этого контакта (нет смысла в повторах номеров)
    * то оправданно использовать TreeMap<String (ФИО), Set<String> (номера)>
    *
    * */
    @Test
    public void addPhoneBook(){

        class Contact{
            String name;
            Set<String> numbers;
            Contact(String name){
                this.name = name;
            }
        }
        // init contacts
        Contact contact1 = new Contact("Vlad");
        Set<String> numContact1 = new HashSet<>();
        numContact1.add("+79998887766");
        numContact1.add("+78887776655");
        contact1.numbers = numContact1;

        Contact contact2 = new Contact("Alex");
        Set<String> numContact2 = new HashSet<>();
        numContact2.add("+77776665544");
        numContact2.add("+76665554433");
        contact2.numbers = numContact2;
        // save contacts to phoneBook
        Map<String,Set<String>> phoneBook = new TreeMap<>();
        phoneBook.put(contact1.name,contact1.numbers);
        phoneBook.put(contact2.name,contact2.numbers);

        assertEquals(2,phoneBook.size());
    }

}
