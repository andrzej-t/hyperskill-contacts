package contacts;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static contacts.Utility.scanner;


public class PersonRecord extends BaseRecord {

    private String name;
    private String surname;
    private String birth;
    private String gender;

    public PersonRecord(String name, String surname, String birth, String gender, String phoneNumber, LocalDateTime dateOfCreation, LocalDateTime dateOfLastEdit) {
        super(phoneNumber, dateOfCreation, dateOfLastEdit);
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.gender = gender;
    }

    public PersonRecord() {
    }

    @Override
    public PersonRecord createRecord() {
        enterName();
        enterSurname();
        enterBirth();
        enterGender();
        enterPhone();

        return new PersonRecord(name, surname, birth, gender, getPhoneNumber(), LocalDateTime.now().withSecond(0).withNano(0), LocalDateTime.now().withSecond(0).withNano(0));
    }

    private void enterName() {
        System.out.println("Enter the name: ");
        String inputName = scanner.nextLine();
        if (inputName.isEmpty()) {
            System.out.println("Name can not be empty!");
            enterName();
        } else {
            name = inputName;
            setDateOfLastEdit();
        }
    }

    private void enterSurname() {
        System.out.println("Enter the surname: ");
        String inputSurname = scanner.nextLine();
        if (inputSurname.isEmpty()) {
            System.out.println("Surname can not be empty!");
            enterSurname();
        } else {
            surname = inputSurname;
            setDateOfLastEdit();
        }
    }

    private void enterPhone() {
        System.out.println("Enter the phone number: ");
        setPhoneNumber(Utility.validateNumber(scanner.nextLine()));
        setDateOfLastEdit();
    }

    private void enterGender() {
        System.out.println("Enter the gender (M, F): ");
        String inputGender = scanner.nextLine();
        if (inputGender.isEmpty() || (!inputGender.equals("M") && !inputGender.equals("F"))) {
            System.out.println("Bad gender!");
            gender = "[no data]";
            setDateOfLastEdit();
        } else {
            gender = inputGender;
            setDateOfLastEdit();
        }
    }

    private void enterBirth() {
        System.out.println("Enter the birth date (dd-MM-yyyy): ");
        String inputBirth = scanner.nextLine();
        if (inputBirth.isEmpty() || !Utility.isLocalDate(inputBirth)) {
            System.out.println("Wrong date format!");
            birth = "[no data]";
            setDateOfLastEdit();
        } else {
            birth = inputBirth;
            setDateOfLastEdit();
        }
    }

    @Override
    public String getFirstField() {
        return name;
    }

    @Override
    public void editField(int index) {
        System.out.println("Select a field (name, surname, birth, gender, number): ");

        switch (scanner.nextLine()) {
            case "name":
                enterName();
                System.out.println("Saved");
                System.out.print(this);
                break;
            case "surname":
                enterSurname();
                System.out.println("Saved");
                System.out.print(this);
                break;
            case "birth":
                enterBirth();
                System.out.println("Saved");
                System.out.print(this);
                break;
            case "gender":
                enterGender();
                System.out.println("Saved");
                System.out.print(this);
                break;
            case "number":
                enterPhone();
                System.out.println("Saved");
                System.out.print(this);
                break;
            default:
                System.out.println("Wrong field");
                editField(index);
        }
    }

    @Override
    public String getAllFields() {
        return MessageFormat.format("{0} {1} {2} {3} {4} {5} {6}", name, surname, birth, gender, getPhoneNumber(), getDateOfCreation(), getDateOfLastEdit());
    }

    @Override
    public String toString() {
        return "Name: " + name + '\n' +
                "Surname: " + surname + '\n' +
                "Birth date: " + birth + '\n' +
                "Gender: " + gender + '\n' +
                "Number: " + getPhoneNumber() + '\n' +
                "Time created: " + getDateOfCreation() + '\n' +
                "Time last edit: " + getDateOfLastEdit() + '\n';
    }
}

