package ou.lhn.salon.db.repository.Salon;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Salon;

public class SalonRepositoryImpl implements SalonRepository {
    private static SalonRepositoryImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private SalonRepositoryImpl() {
    }

    public static SalonRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (SalonRepositoryImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new SalonRepositoryImpl();
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
