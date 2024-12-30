package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine {
    Scanner scanner = new Scanner(System.in);
    List<Object> phoneBook = new ArrayList<>();
    Util util = new Util();

    public void run() {
        System.out.print("Enter action (add, remove, edit, count, info, exit): ");
        String action = scanner.nextLine();

        switch (action) {
            case "add":
                addRecord();
                run();
            case "remove":
                removeRecord();
                run();
            case "edit":
                edit();
                run();
            case "count":
                System.out.printf("The Phone Book has %s records.%n", phoneBook.size());
                System.out.println("\n");
                run();
            case "info":
                info();
                run();
            case "exit":
                System.exit(0);
            default:
                System.out.println("Wrong action!");
                run();
        }
    }

    private void removeRecord() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to remove!");
            System.out.println("\n");
        } else {
            showRecords();
            System.out.print("Select a record: ");
            String record = scanner.nextLine();
            if (util.isNumeric(record)) {
                int index = Integer.parseInt(record);
                if (index < 1 || index > phoneBook.size()) {
                    System.out.println("Invalid index!");
                    removeRecord();
                } else {
                    phoneBook.remove(index - 1);
                    System.out.println("The record removed!");
                    System.out.println("\n");
                }
            } else {
                System.out.println("Invalid index. Select one from displayed records!");
                removeRecord();
            }
        }
    }

    private void showRecords() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to show!");
            System.out.println("\n");
            run();
        } else {
            for (Object record : phoneBook) {
                if (record instanceof PersonRecord personRecord) {
                    System.out.println(phoneBook.indexOf(record) + 1 + ". " + personRecord.getName() + " " + personRecord.getSurname());
                } else {
                    OrganizationRecord organizationRecord = (OrganizationRecord) record;
                    System.out.println(phoneBook.indexOf(record) + 1 + ". " + organizationRecord.getOrganizationName());
                }
            }
        }
    }

    private void info() {
        showRecords();
        System.out.print("Enter index to show info: ");
        String index = scanner.nextLine();
        if (util.isNumeric(index)) {
            int indexOfRecord = Integer.parseInt(index) - 1;
            if (indexOfRecord >= 0 && indexOfRecord < phoneBook.size()) {
                Object record = phoneBook.get(indexOfRecord);
                if (record instanceof PersonRecord personRecord) {
                    System.out.println("Name: " + personRecord.getName());
                    System.out.println("Surname: " + personRecord.getSurname());
                    System.out.println("Birth date: " + personRecord.getBirth());
                    System.out.println("Gender: " + personRecord.getGender());
                    System.out.println("Number: " + personRecord.getPhoneNumber());
                    System.out.println("Time created: " + personRecord.getDateOfCreation());
                    System.out.println("Time last edit: " + personRecord.getDateOfLastEdit());
                    System.out.println("\n");
                } else if (record instanceof OrganizationRecord organizationRecord) {
                    System.out.println("Organization name: " + organizationRecord.getOrganizationName());
                    System.out.println("Address: " + organizationRecord.getAddress());
                    System.out.println("Number: " + organizationRecord.getPhoneNumber());
                    System.out.println("Time created: " + organizationRecord.getDateOfCreation());
                    System.out.println("Time last edit: " + organizationRecord.getDateOfLastEdit());
                    System.out.println("\n");
                }
            } else {
                System.out.println("Select one of the displayed records!");
                info();
            }
        } else {
            System.out.println("Wrong index!");
            info();
        }
    }

    private void edit() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to edit!");
            System.out.println("\n");
        } else {
            showRecords();
            System.out.print("Select a record: ");
            String record = scanner.nextLine();
            if (util.isNumeric(record)) {
                int index = Integer.parseInt(record);
                if (index < 1 || index > phoneBook.size()) {
                    System.out.println("Invalid index!");
                    edit();
                } else {
                    if (phoneBook.get(index - 1) instanceof PersonRecord personRecord) {
                        System.out.print("Select a field (name, surname, birth, gender, number): ");
                        String field = scanner.nextLine();
                        if (field.isEmpty()) {
                            System.out.println("Value should not be empty!");
                            edit();
                        }
                        if (!field.equals("name") && !field.equals("surname") && !field.equals("birth") && !field.equals("gender") && !field.equals("number")) {
                            System.out.println("Wrong field format.");
                            edit();
                        }
                        System.out.printf("Enter %s:  ", field);
                        String changedField = scanner.nextLine();
                        if (changedField.isEmpty()) {
                            System.out.println("Value should not be empty!");
                            edit();
                        } else {
                            switch (field) {
                                case "name":
                                    personRecord.setName(changedField);
                                    break;
                                case "surname":
                                    personRecord.setSurname(changedField);
                                    break;
                                case "birth":
                                    if (util.isLocalDate(changedField)) {
                                        personRecord.setBirth(util.parseLocalDate(changedField));
                                        break;
                                    } else {
                                        System.out.println("Wrong date format.");
                                        edit();
                                    }
                                case "gender":
                                    if (changedField.equals("M") || changedField.equals("F")) {
                                        personRecord.setGender(changedField);
                                        break;
                                    } else {
                                        System.out.println("Invalid gender!");
                                        edit();
                                    }
                                case "number":
                                    if (util.validatePhoneNumber(changedField)) {
                                        personRecord.setPhoneNumber(changedField);
                                        break;
                                    } else {
                                        System.out.println("Invalid phone number!");
                                        edit();
                                    }
                                default:
                                    System.out.println("Invalid field!");
                                    edit();
                            }
                            personRecord.setDateOfLastEdit(LocalDateTime.now().withSecond(0).withNano(0));
                            phoneBook.set(index - 1, personRecord);
                            System.out.println("Record updated!");
                            System.out.println("\n");
                            run();
                        }
                    } else if (phoneBook.get(index - 1) instanceof OrganizationRecord organizationRecord) {
                        System.out.print("Select a field (address, number): ");
                        String field = scanner.nextLine();
                        if (field.isEmpty()) {
                            System.out.println("Value should not be empty!");
                            edit();
                        }
                        if (!field.equals("address") && !field.equals("number")) {
                            System.out.println("Wrong field format.");
                            edit();
                        }
                        System.out.printf("Enter %s:  ", field);
                        String changedField = scanner.nextLine();
                        if (changedField.isEmpty()) {
                            System.out.println("Value should not be empty!");
                            edit();
                        } else {
                            switch (field) {
                                case "address":
                                    organizationRecord.setAddress(changedField);
                                    break;
                                case "number":
                                    if (util.validatePhoneNumber(changedField)) {
                                        organizationRecord.setPhoneNumber(changedField);
                                        break;
                                    } else {
                                        System.out.println("Invalid phone number!");
                                        edit();
                                    }
                                default:
                                    System.out.println("Invalid field!");
                                    edit();
                            }
                            organizationRecord.setDateOfLastEdit(LocalDateTime.now().withSecond(0).withNano(0));
                            phoneBook.set(index - 1, organizationRecord);
                            System.out.println("Record updated!");
                            System.out.println("\n");
                            run();
                        }
                    }
                }
            } else {
                System.out.println("Wrong number format!");
                edit();
            }
        }
    }

    private void addRecord() {
        boolean isPerson;
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine();

        if (!type.equals("person") && !type.equals("organization")) {
            System.out.println("Select \"person\" or \"organization\"!");
            addRecord();
        } else {
            isPerson = type.equals("person");

            if (isPerson) {
                PersonRecord record = new PersonRecord();
                System.out.print("Enter the name: ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Name can not be empty!");
                    addRecord();
                }
                record.setName(name);
                System.out.print("Enter the surname: ");
                String surname = scanner.nextLine();
                if (surname.isEmpty()) {
                    System.out.println("Surname can not be empty!");
                    addRecord();
                }
                record.setSurname(surname);
                System.out.print("Enter the birth date (dd-MM-yyyy): ");
                String date = scanner.nextLine();
                if (util.isLocalDate(date)) {
                    record.setBirth(LocalDate.parse(date, util.formatter));
                    System.out.print("Enter the gender (M, F): ");
                    String gender = scanner.nextLine();
                    if (!gender.equals("M") && !gender.equals("F")) {
                        System.out.println("Bad gender!");
                        gender = "[no data]";
                    }
                    record.setGender(gender);
                    System.out.print("Enter the number: ");
                    String number = scanner.nextLine();
                    if (util.validatePhoneNumber(number)) {
                        record.setPhoneNumber(number);
                    } else {
                        System.out.println("Wrong number format!");
                        record.setPhoneNumber("no number");
                    }
                    phoneBook.add(record);
                    System.out.println("The record added.");
                    System.out.println("\n");
                } else {
                    System.out.println("Wrong date format!");
                    run();
                }
            } else {
                OrganizationRecord record = new OrganizationRecord();
                System.out.print("Enter the organization name: ");
                String name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Organization name can not be empty!");
                    addRecord();
                } else {
                    record.setOrganizationName(name);
                    System.out.print("Enter the address: ");
                }
                String address = scanner.nextLine();
                if (address.isEmpty()) {
                    System.out.println("Address can not be empty!");
                    addRecord();
                } else {
                    record.setAddress(address);
                }
                System.out.print("Enter the number: ");
                String number = scanner.nextLine();
                if (util.validatePhoneNumber(number)) {
                    record.setPhoneNumber(number);
                } else {
                    System.out.println("Wrong number format!");
                    record.setPhoneNumber("no number");
                }
                phoneBook.add(record);
                System.out.println("The record added.");
                System.out.println("\n");
                run();
            }
        }
    }
}

