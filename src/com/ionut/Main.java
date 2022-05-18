package com.ionut;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Tutor tutor = new Tutor(lock);
        Student student = new Student(tutor, lock);
        tutor.setStudent(student);

        Thread tutorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                tutor.studyTime();
            }
        });

        Thread studentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                student.handInAssignment();
            }
        });

        tutorThread.start();
        studentThread.start();
    }
}

class Tutor {
    private final Lock lock;
    private Student student;

    public Tutor(Lock lock) {
        this.lock = lock;
    }

    public synchronized void setStudent(Student student) {
        this.student = student;
    }

    public synchronized void studyTime() {
        System.out.println("Tutor has arrived");
        try {
            // wait for student to arrive and hand in assignment
            Thread.sleep(300);
        } catch (InterruptedException e) {

        }
        student.startStudy();
        System.out.println("Tutor is studying with student");
    }

    public synchronized void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class Student {

    private final Lock lock;
    private Tutor tutor;

    Student(Tutor tutor, Lock lock) {
        this.tutor = tutor;
        this.lock = lock;
    }

    public void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public synchronized void handInAssignment() {
        tutor.getProgressReport();
        System.out.println("Student handed in assignment");
    }
}