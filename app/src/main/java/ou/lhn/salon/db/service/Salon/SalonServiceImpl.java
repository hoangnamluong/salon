package ou.lhn.salon.db.service.Salon;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.repository.Salon.SalonRepository;
import ou.lhn.salon.db.repository.Salon.SalonRepositoryImpl;

public class SalonServiceImpl implements SalonService {
    private static SalonServiceImpl INSTANCE;
    private final SalonRepository salonRepository = SalonRepositoryImpl.getInstance();

    private SalonServiceImpl() {
    }

    public static SalonServiceImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (SalonServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new SalonServiceImpl();
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Salon> getAllSalons() {
        return null;
    }

    @Override
    public Salon getSalonById(int id) {
        return null;
    }

    @Override
    public boolean addSalon(Salon salon) {
        return false;
    }

    @Override
    public boolean updateSalon(Salon salon) {
        return false;
    }

    @Override
    public boolean deleteSalon(int id) {
        return false;
    }

    @Override
    public boolean softDeleteSalon(int id) {
        return false;
    }
}
