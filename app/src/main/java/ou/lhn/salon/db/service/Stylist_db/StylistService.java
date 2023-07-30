package ou.lhn.salon.db.service.Stylist_db;

import java.util.ArrayList;
import java.util.Date;

import ou.lhn.salon.db.model.Stylist;

public interface StylistService {
    ArrayList<Stylist> getAllStylist();
    Stylist getStylistById(int stylistId);
    boolean addStylist(Stylist stylist);
    boolean updateStylist(Stylist stylist);
    boolean deleteStylist(int stylistId);
    int countCustomerToday(int stylistId);
}
