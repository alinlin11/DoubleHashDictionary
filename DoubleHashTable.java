package com.company;

import java.util.function.Function;
import java.util.*;

//takes in a int key
//and returns a int index
public class DoubleHashTable<V extends Hashable> implements OpenAddressTable<V> {
    private ArrayList<V> array;
    private Function<Integer, Integer> h1;
    private Function<Integer, Integer> h2;
    private int maxSize;
    private int currentSize;

    //need functions
    public DoubleHashTable(int size, Function<Integer, Integer> h1, Function<Integer, Integer> h2) {
        this.maxSize = size;
        array = new ArrayList<>(maxSize);
        this.h1 = h1;
        this.h2 = h2;

        for(int i = 0; i < maxSize; i++) {
            array.add(null);
        }
    }



    public int hash(int key, int probenumber) {
        //returns index
        return (h1.apply(key) + (h2.apply(key) * probenumber - 1)) % maxSize;
    }

    private V getValue(V value) {
        return value;
    }

    public boolean isEmpty() {
        if(this.currentSize == 0)
            return true;
        else
            return false;
    }

    public void insert(V value) {

        if(value == null)
            throw new NullPointerException();

        int key = value.key();
        int index = hash(key, 1);

        if(array.get(index) == null)  {
            array.set(index, value);
            currentSize++;
        }

        else {
            int probenumber = 2;
            boolean noInsert = true;
            while(noInsert) {
                index = hash(key, probenumber);

                if(array.get(index) == null) {
                    array.set(index, value);
                    currentSize++;
                    noInsert = false;
                }
                probenumber++;
            }
        }


    }

    public V delete(V value) {
       if(value == null)
           throw new NullPointerException();

       else {
           int index = find(value);

           if (index != -1) {
               array.set(index, null);
               currentSize--;
               return value;
           }
       }

       return null;
    }

    public int find(V value) {
        for(int i = 0; i < maxSize; i++) {
            if(array.get(i) != null && array.get(i).equals(value) ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String s = "";

        for(int i = 0; i < maxSize; i ++) {
            String key = "";

            if(array.get(i) != null) {
                V v = getValue(array.get(i));
                key = "" + v.key();

                s += i + " --> " + "[" + key + ", " + array.get(i) + "]; ";
            }

            else {
                s += i + " --> " + "[" + array.get(i) + "]; ";
            }
        }
        return s;
    }





    //driver
    public static void main(String[] args) {

        Function<Integer, Integer> h11 = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer key) {
                return key * key;
            }
        };

        Function<Integer, Integer> h22 = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer key) {
                return ((key * key) / key) + 1;
            }
        };

        DoubleHashTable<HashableString> t = new DoubleHashTable<HashableString>(11, h11, h22);

//        System.out.println(t.isEmpty());
//        t.insert(new HashableString("apple"));
//        System.out.println(t.isEmpty());
//
//        HashableString dog = new HashableString("dog");
//        HashableString cat = new HashableString("cat2");
//        t.insert(dog);
//        t.insert(cat);
//        System.out.println("Index of dog: " + t.find(dog));
//        System.out.println("Index of cat: " + t.find(cat));
//        System.out.println(t.toString());
//        t.delete(dog);
//        System.out.println(t.toString());

        HashableString test = new HashableString("a");
        t.insert(test);
        System.out.println(t.toString());
        System.out.println(t.find(test));
        t.delete(test);
        System.out.println(t.toString());
        System.out.println(t.isEmpty());
    }

}
