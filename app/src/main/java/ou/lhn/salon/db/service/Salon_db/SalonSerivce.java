package ou.lhn.salon.db.service.Salon_db;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Salon;

public interface SalonSerivce {
    ArrayList<Salon> getAllSalons();
    Salon getSalonById(int salonId);
    Salon getSalonByStaffId(int staffId);
    boolean addSalon(Salon salon);
    boolean updateSalon(Salon salon);
    boolean deleteSalon(int id);
    boolean softDeleteSalon(int id);
}
