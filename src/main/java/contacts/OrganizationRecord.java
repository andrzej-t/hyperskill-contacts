package contacts;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static contacts.Utility.scanner;


public class OrganizationRecord extends BaseRecord {

    private String organizationName;
    private String address;

    public OrganizationRecord(String organizationName, String address, String phoneNumber, LocalDateTime dateOfCreation, LocalDateTime dateOfLastEdit) {
        super(phoneNumber, dateOfCreation, dateOfLastEdit);
        this.organizationName = organizationName;
        this.address = address;
    }

    public OrganizationRecord() {
    }

    @Override
    public BaseRecord createRecord() {

        enterOrganizationName();
        enterAddress();
        enterPhone();

        return new OrganizationRecord(organizationName, address, getPhoneNumber(), LocalDateTime.now().withSecond(0).withNano(0), LocalDateTime.now().withSecond(0).withNano(0));
    }

    private void enterPhone() {
        System.out.println("Enter the phone number: ");
        setPhoneNumber(Utility.validateNumber(scanner.nextLine()));
        setDateOfLastEdit();
    }

    private void enterAddress() {
        System.out.println("Enter the address: ");
        String inputAddress = scanner.nextLine();
        if (inputAddress.isEmpty()) {
            System.out.println("Address can not be empty!");
            createRecord();
        }
        address = inputAddress;
    }

    private void enterOrganizationName() {
        System.out.println("Enter the organization name: ");
        String inputOrgName = scanner.nextLine();
        if (inputOrgName.isEmpty()) {
            System.out.println("Name can not be empty!");
            enterOrganizationName();
        } else {
            organizationName = inputOrgName;
            setDateOfLastEdit();
        }
    }

    @Override
    public String getFirstField() {
        return organizationName;
    }

    @Override
    public void editField(int index) {
        System.out.println("Select a field (name, address, number): ");

        switch (scanner.nextLine()) {
            case "name":
                enterOrganizationName();
                System.out.println("Saved");
                System.out.print(this);
                break;
            case "address":
                enterAddress();
                System.out.println("Saved");
                System.out.print(this);
                break;
            case "number":
                enterPhone();
                System.out.println("Saved");
                System.out.print(this);
                break;
            default:
                System.out.print("Wrong field");
                editField(index);
        }
    }

    @Override
    public String getAllFields() {
        return MessageFormat.format("{0} {1} {2} {3} {4}", organizationName, address, getPhoneNumber(), getDateOfCreation(), getDateOfLastEdit());
    }

    @Override
    public String toString() {
        return "Organization name: " + organizationName + '\n' +
                "Address: " + address + '\n' +
                "Number: " + getPhoneNumber() + '\n' +
                "Time created: " + getDateOfCreation() + '\n' +
                "Time last edit: " + getDateOfLastEdit() + '\n';
    }
}

