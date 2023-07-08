package ou.lhn.salon.db.repository.Branch;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Branch;

public class BranchRepositoryImpl implements BranchRepository {
    private static BranchRepositoryImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private BranchRepositoryImpl() {
    }

    public static BranchRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (BranchRepositoryImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new BranchRepositoryImpl();
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Branch> getAllBranches() {
        return null;
    }

    @Override
    public ArrayList<Branch> getBranchesBySalonId(int salonId) {
        return null;
    }

    @Override
    public Branch getBranchById(int id) {
        return null;
    }

    @Override
    public boolean addBranch(Branch branch) {
        return false;
    }

    @Override
    public boolean updateBranch(Branch branch) {
        return false;
    }

    @Override
    public boolean deleteBranch(int id) {
        return false;
    }

    @Override
    public boolean softDeleteBranch(int id) {
        return false;
    }
}
