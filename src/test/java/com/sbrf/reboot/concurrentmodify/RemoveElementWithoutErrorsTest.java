package com.sbrf.reboot.concurrentmodify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveElementWithoutErrorsTest {

    @Test
    public void successConcurrentModificationException() {

        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
            add(3);
        }};

        assertThrows(ConcurrentModificationException.class, () -> {

            for (Integer integer : list) {
                list.remove(1);
            }

        });

    }

    @Test
    @DisplayName("Вариант-1 - removeIf")
    public void successRemoveElementWithoutErrors() {
        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
            add(3);
        }};
        list.removeIf(i -> i == 1);

        List<Integer> outList = new ArrayList() {{
            add(2);
            add(3);
        }};

        assertEquals(list, outList);
    }

    @Test
    @DisplayName("Вариант-2 - remove by addition List")
    public void successRemoveElementWithoutErrorsVariant2() {
        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
            add(3);
        }};
        // additional collection
        List<Integer> listForDelete = new ArrayList<>();

        list.forEach((x) -> {
            if (x == 1) {
                listForDelete.add(x);
            }
        });
        list.removeAll(listForDelete);

        List<Integer> outList = new ArrayList() {{
            add(2);
            add(3);
        }};

        assertEquals(list, outList);
    }
    @Test
    @DisplayName("Вариант-3 - remove by stream")
    public void successRemoveElementWithoutErrorsVariant3() {
        List<Integer> list = new ArrayList() {{
            add(1);
            add(2);
            add(3);
        }};
        list = list
                .stream()
                .filter((x)-> x != 1)
                .collect(Collectors.toList());

        List<Integer> outList = new ArrayList() {{
            add(2);
            add(3);
        }};

        assertEquals(list, outList);
    }
}
