package com.sbrf.reboot.equalshashcode;

import org.junit.jupiter.api.Assertions;
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
            return  maxSpeed == objCar.maxSpeed
                    && model.equals(objCar.model)
                    && color.equals(objCar.color)
                    && releaseDate.equals(objCar.releaseDate);
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


}
