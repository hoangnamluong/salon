package ou.lhn.salon.db.service.Salon;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Salon;

public interface SalonService {
    ArrayList<Salon> getAllSalons();
    Salon getSalonById(int id);
    boolean addSalon(Salon salon);
    boolean updateSalon(Salon salon);
    boolean deleteSalon(int id);
    boolean softDeleteSalon(int id);
}
