package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BaseRecord implements Serializable {

    private String phoneNumber;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastEdit;

    public BaseRecord(String phoneNumber, LocalDateTime dateOfCreation, LocalDateTime dateOfLastEdit) {
        this.phoneNumber = phoneNumber;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastEdit = dateOfLastEdit;
    }

    public BaseRecord() {
    }

    public abstract BaseRecord createRecord();

    public abstract String getFirstField();

    public abstract void editField(int index);

    public abstract String getAllFields();

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    protected LocalDateTime getDateOfLastEdit() {
        return dateOfLastEdit;
    }

    protected void setDateOfLastEdit() {
        this.dateOfLastEdit = LocalDateTime.now().withSecond(0).withNano(0);
    }
}
