package com.sbrf.reboot.equalshashcode;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EqualsHashCodeTest {

     class Car {
        String model;
        String color;
        Calendar releaseDate;
        int maxSpeed;

        @Override
        public boolean equals(Object o) {
            //Рефлексивность: объект должен равняться самому себе
            if (o == this)
                return true;
            // Проверка на null
            if ( o == null)
                return false;
            // Проверка на соответствие классу
            if (o.getClass() != this.getClass())
                return false;
            Car objCar = (Car) o;
            //Симметричность: если x.equals(y) == true, то y.equals(x) == true;
            // сравнение полей объектов через == требуется чтобы избежать NPE
            return  maxSpeed == objCar.maxSpeed
                    && (model == objCar.model || model.equals(objCar.model))
                    && (color == objCar.color || color.equals(objCar.color))
                    && (releaseDate == objCar.releaseDate || releaseDate.equals(objCar.releaseDate));
        }
        @Override
         public int hashCode(){
            final int base = 13;
            int result = 1;
            // проверки на null
            int hashModel = (model == null) ? 0 : model.hashCode();
            int hashColor = (color == null) ? 0 : color.hashCode();
            int hashCalendar = (releaseDate == null) ? 0 : releaseDate.hashCode();
            // алгоритм сложения всех полей которые используются  методе equals()
            result = base * result + hashModel;
            result = base * result + hashColor;
            result = base * result + hashCalendar;
            result = base * result + maxSpeed;
            return result;
        }


     }

    @Test
    public  void assertTrueEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 0, 25);
        car2.maxSpeed = 10;


        Assertions.assertTrue(car1.equals(car2));
    }

    @Test
    public void assertFalseEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Audi";
        car2.color = "white";
        car2.releaseDate = new GregorianCalendar(2017, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertFalse(car1.equals(car2));
    }

    @Test
    public void successEqualsHashCode(){
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertEquals(car1.hashCode(),car2.hashCode());

    }

    @Test
    public void failEqualsHashCode(){
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 0, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Audi";
        car2.color = "white";
        car2.releaseDate = new GregorianCalendar(2017, 0, 25);
        car2.maxSpeed = 10;

        Assertions.assertNotEquals(car1.hashCode(),car2.hashCode());

    }

    @Test
    @DisplayName("Рефлексивность - equals")
    public void reflexivityEqualsTest(){
         //Объект должен равняться себе самому.
        Car car1 = new Car();
        car1.model = "BMW";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 2, 15);
        car1.maxSpeed = 100;

        Assertions.assertTrue(car1.equals(car1));
    }
    @Test
    @DisplayName("Симметрия - equals")
    public void symmetryEqualsTest(){
         //если a.equals(b) возвращает true, то b.equals(a) должен тоже вернуть true.
        Car car1 = new Car();
        car1.model = "BMW";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 2, 15);
        car1.maxSpeed = 100;

        Car car2 = new Car();
        car2.model = "BMW";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 2, 15);
        car2.maxSpeed = 100;

        Assertions.assertEquals(car1.equals(car2),car2.equals(car1));
    }
    @Test
    @DisplayName("Транзитивность - equals")
    public void transitivityEqualsTest(){
         //если a.equals(b) возвращает true и b.equals(c) тоже возвращает true,
        // то c.equals(a) тоже должен возвращать true.
        Car car1 = new Car();
        car1.model = "BMW";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 2, 15);
        car1.maxSpeed = 100;

        Car car2 = new Car();
        car2.model = "BMW";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 2, 15);
        car2.maxSpeed = 100;

        Car car3 = new Car();
        car3.model = "BMW";
        car3.color = "black";
        car3.releaseDate = new GregorianCalendar(2020, 2, 15);
        car3.maxSpeed = 100;

        Assertions.assertEquals(car1.equals(car2) == car2.equals(car3),car3.equals(car1));
    }
    @Test
    @DisplayName("Согласованность - equals")
    public void consistencyEqualsTest(){
         Car car1 = new Car();
        car1.model = "BMW";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 2, 15);
        car1.maxSpeed = 100;

        Car car2 = new Car();
        car2.model = "BMW";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, 2, 15);
        car2.maxSpeed = 100;

        // повторный вызов метода equals() должен возвращать одно и тоже значение до тех пор,
        // пока какое-либо значение свойств объекта не будет изменено.
        // То есть, если два объекта равны в Java, то они будут равны пока их свойства остаются неизменными.
        for (int i= 0; i < 100; i++){
            if (!car1.equals(car2))
                Assertions.assertNotEquals(car1, car2);
            if (i == 60)
                car2.maxSpeed = 200;
        }
    }
    @Test
    @DisplayName("Сравнение на null - equals")
    public void nullEqualsTest(){
         //объект должны быть проверен на null.
        // Если объект равен null, то метод должен вернуть false,
        // а не NullPointerException. Например, a.equals(null) должен вернуть false.
        Car car1 = new Car();
        car1.model = "BMW";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, 2, 15);
        car1.maxSpeed = 100;

        Assertions.assertFalse(car1.equals(null));
    }

    @Test
    @DisplayName("Равные hashCode для одинаковых объектов")
    public void sameObjectsHashCode(){
         //вызов метода hashCode один и более раз над одним и тем же объектом
        // должен возвращать одно и то же хэш-значение, при условии что поля объекта,
        // участвующие в вычислении значения, не изменялись.

        // вызов метода hashCode над двумя объектами должен всегда возвращать одно и то же число,
        // если эти объекты равны (вызов метода equals для этих объектов возвращает true).
         Car[] cars = new Car[10];
         // посчитанные хэш-коды объектов
         int[] hashCodes = new int[10];
         for (int i = 0; i < 10; i++){
             cars[i] = new Car();
             cars[i].maxSpeed = 100;
             hashCodes[i] = cars[i].hashCode();
         }

         Assertions.assertAll(()->{
             for (int i = 0; i < 10; i++){
                 Assertions.assertTrue(cars[i].equals(cars[0]) && hashCodes[i] == hashCodes[0]);
             }
         });

    }



}
