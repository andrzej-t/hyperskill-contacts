package contacts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static contacts.Utility.scanner;

public class Engine {
    public static String[] args;
    private String sourceFile = findSourceFile();
    private final List<BaseRecord> phoneBook = loadFromFile();
    private final BaseRecord personRecord = new PersonRecord();
    private final BaseRecord organizationRecord = new OrganizationRecord();

    private String findSourceFile() {
        sourceFile = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-source")) {
                sourceFile = args[i + 1];
            }
        }

        return sourceFile;
    }

    public void run() {
        System.out.println();
        System.out.print("Enter action (add, list, search, count, exit): ");
        String action = scanner.nextLine();

        switch (action) {
            case "add":
                addRecord();
                System.out.println("Size: " + phoneBook.size() + " phone book");
                run();
            case "list":
                listAllRecords();
                run();
            case "search":
                searchText();
                run();
            case "count":
                System.out.printf("The Phone Book has %s records.%n", phoneBook.size());
                run();
            case "exit":
                saveToFile(phoneBook);
                System.exit(0);
            default:
                System.out.println("Wrong action!");
                run();
        }
    }

    @SuppressWarnings("unchecked")
    private List<BaseRecord> loadFromFile() {
        List<BaseRecord> records;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sourceFile))) {
            records = (ArrayList<BaseRecord>) ois.readObject();
        } catch (Exception e) {
            records = new ArrayList<>();
            e.getStackTrace();
        }
        return records;
    }

    private void saveToFile(List<BaseRecord> phoneBook) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sourceFile))) {
                oos.writeObject(phoneBook);
            } catch (Exception e) {
                e.getStackTrace();
            }
    }

    private void searchText() {
        System.out.print("Enter search query: ");
        String search = scanner.nextLine();
        Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        List<BaseRecord> filteredRecords = new ArrayList<>();
        for (BaseRecord record : phoneBook) {
            Matcher matcher = pattern.matcher(record.getAllFields());
            if (matcher.find()) {
                filteredRecords.add(record);
            }
        }
        System.out.println("Found " + filteredRecords.size() + " results: ");
        for (int i = 0; i < filteredRecords.size(); i++) {
            System.out.println(i + 1 + ". " + filteredRecords.get(i).getFirstField());
        }
        System.out.println();
        selectFilteringAction(filteredRecords);
    }

    private void selectFilteringAction(List<BaseRecord> filteredRecords) {
        System.out.println("Enter action ([number], back, again): ");
        String action = scanner.nextLine();
        Integer index = null;
        if (Utility.isNumeric(action) && Integer.parseInt(action) - 1 >= 0 && Integer.parseInt(action) - 1 < filteredRecords.size()) {
            System.out.println(filteredRecords.get(Integer.parseInt(action) - 1).toString());
            index = Integer.parseInt(action) - 1;
        }
        if (action.equals("back")) {
            run();
        } else if (action.equals("again")) {
            searchText();
        } else if (index != null) {
            choseRecordAction(phoneBook.indexOf(filteredRecords.get(index)));
        } else {
            System.out.println("Wrong action!");
            run();
        }
    }

    private void removeRecord(int index) {
        phoneBook.remove(index);
        System.out.println("The record removed!");
    }

    private void listAllRecords() {
        showRecords();
        System.out.println("Enter action ([number], back): ");
        String action = scanner.nextLine();
        Integer index = null;
        if (Utility.isNumeric(action) && Integer.parseInt(action) - 1 >= 0 && Integer.parseInt(action) - 1 < phoneBook.size()) {
            System.out.println(phoneBook.get(Integer.parseInt(action) - 1).toString());
            index = Integer.parseInt(action) - 1;
        }
        if (action.equals("back")) {
            run();
        } else if (index != null) {
            choseRecordAction(index);
        } else {
            System.out.println("Wrong action!");
            run();
        }
    }

    private void choseRecordAction(int index) {
        System.out.println("Enter action (edit, delete, menu): ");
        String action = scanner.nextLine();
        switch (action) {
            case "edit":
                editRecord(index);
                run();
            case "delete":
                removeRecord(index);
                run();
            case "menu":
                run();
            default:
                System.out.println("Wrong action!");
                run();
        }
    }

    private void editRecord(int index) {
        BaseRecord record = phoneBook.get(index);
        record.editField(index);
        run();
    }

    private void showRecords() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to show!");
            run();
        } else {
            for (int i = 0; i < phoneBook.size(); i++) {
                System.out.println(i + 1 + ". " + phoneBook.get(i).getFirstField());
            }
            System.out.println();
        }
    }

    private void addRecord() {
        System.out.println("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        switch (type) {
            case "person":
                phoneBook.add(personRecord.createRecord());
                System.out.println("The record added.");
                break;
            case "organization":
                phoneBook.add(organizationRecord.createRecord());
                System.out.println("The record added.");
                break;
            default:
                System.out.println("Wrong type!");
                addRecord();
        }
    }
}

