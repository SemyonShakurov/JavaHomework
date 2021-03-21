package Forms;

import Annotations.*;

import java.util.Set;

@Constrained
public class RegistrationForm {

    @NotNull
    String name;

    @Positive
    int age;

    @Negative
    Integer parkingFloor;

    @NotBlank
    String surname;

    @NotEmpty
    Set<String> contacts;

    @Size(min = 5 ,max =  20)
    String email;

    @InRange(min = 100, max = 250)
    int growth;

    @AnyOf(value = {"Cat", "Dog"})
    String animal;

    public RegistrationForm(String name, int age,
                            Integer parkingFloor, String surname,
                            Set<String> contacts, String email,
                            int growth, String animal) {
        this.name = name;
        this.age = age;
        this.parkingFloor = parkingFloor;
        this.surname = surname;
        this.contacts = contacts;
        this.email = email;
        this.growth = growth;
        this.animal = animal;
    }
}
