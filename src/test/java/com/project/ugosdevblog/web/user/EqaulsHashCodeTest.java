package com.project.ugosdevblog.web.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class EqaulsHashCodeTest {

    @Test
    void EqhcTest(){
        ObjTest obj1 = new ObjTest(1L,1,2);
        ObjTest obj2 = new ObjTest(1L,2,1);

        Set<ObjTest> objSet = new HashSet<>();
        objSet.add(obj1);
        objSet.add(obj2);

        for (ObjTest objTest : objSet) {
            System.out.println("objTest = " + objTest);
        }
    }
    @DisplayName("eqhc_String_Proeprty.")
    @Test
    void test_eqhc_String_Proeprty(){
        TestUser tUser =new TestUser("ugo",123);
        TestUser tUser2 =new TestUser("ugo",1234);
        Set<TestUser> testUserSet = new HashSet<>();
        testUserSet.add(tUser);
        testUserSet.add(tUser2);


        for (TestUser testUser : testUserSet) {
            System.out.println("testUser = " + testUser);
        }
    }
}

class TestUser{
    private String username;
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestUser)) return false;
        TestUser testUser = (TestUser) o;
        return Objects.equals(username, testUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public TestUser(String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}

class ObjTest{

    public ObjTest(Long id, int num1, int num2) {
        this.id = id;
        this.num1 = num1;
        this.num2 = num2;
    }

    @Id
    private Long id;
    private int num1;
    private int num2;

    @Override
    public String toString() {
        return "ObjTest{" +
                "id=" + id +
                ", num1=" + num1 +
                ", num2=" + num2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjTest)) return false;
        ObjTest objTest = (ObjTest) o;
        return Objects.equals(id, objTest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ObjTest() {

    }
}

