package ou.lhn.salon.db.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Branch {
    private int id;
    private String address;

    public Branch() {
    }

    public Branch(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
