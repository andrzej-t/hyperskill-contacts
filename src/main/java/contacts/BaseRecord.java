package contacts;

import java.time.LocalDateTime;

public abstract class BaseRecord {

    private String phoneNumber;
    private final LocalDateTime dateOfCreation = LocalDateTime.now().withSecond(0).withNano(0);
    private LocalDateTime dateOfLastEdit = LocalDateTime.now().withSecond(0).withNano(0);

    public BaseRecord() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public LocalDateTime getDateOfLastEdit() {
        return dateOfLastEdit;
    }
    public void setDateOfLastEdit(LocalDateTime dateOfLastEdit) {
        this.dateOfLastEdit = dateOfLastEdit;
    }
}
