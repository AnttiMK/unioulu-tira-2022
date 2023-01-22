package oy.tol.tra;

public class Person implements Comparable<Person> {

    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    @Override
    public int compareTo(Person o) {
        return this.getFullName().compareTo(o.getFullName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return other.firstName.equals(this.firstName) && other.lastName.equals(this.lastName);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        for (int i = 0; i < firstName.length(); i++) {
            hash = ((hash << 5) + hash) + firstName.charAt(i);
        }
        for (int i = 0; i < lastName.length(); i++) {
            hash = ((hash << 5) + hash) + lastName.charAt(i);
        }
        return hash;
    }

}
