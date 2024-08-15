package bg.softuni;

import javax.persistence.*;


@MappedSuperclass // --> Class doesn't have its own table & entity
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // --> AutoIncrement PRIMARY KEY
//    @GeneratedValue(strategy = GenerationType.TABLE) // --> Creating an Extra Table keeping track of the next ID (in case Of Multiple Inheritance)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE) // --> Creating an Extra Table keeping track of the next ID (in case Of Multiple Inheritance)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_generator") // --> AutoIncrement PRIMARY KEY with Custom Initial Value
//    @SequenceGenerator(name = "employee_seq_generator", sequenceName = "employee_seq", initialValue = 100, allocationSize = 50)
    private long id;

    @Column
    private String type;

    public Base() {
    }

    public Base(String type) {
        this.type = type;
    }
}
