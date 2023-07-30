package ou.lhn.salon.db.service.Salon_db;

import android.database.Cursor;
import java.util.ArrayList;

import ou.lhn.salon.db.model.Salon;

public interface SalonSerivce {
    ArrayList<Salon> getAllSalons();
    Salon getSalonById(int salonId);
    ArrayList<Salon> getListSalonByName (String name);
    Salon getSalonByStaffId(int staffId);
    boolean addSalon(Salon salon);
    boolean updateSalon(Salon salon);
    boolean deleteSalon(int id);
    boolean softDeleteSalon(int id);
}
