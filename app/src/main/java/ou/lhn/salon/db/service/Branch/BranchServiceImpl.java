package ou.lhn.salon.db.service.Branch;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Branch;
import ou.lhn.salon.db.repository.Branch.BranchRepository;
import ou.lhn.salon.db.repository.Branch.BranchRepositoryImpl;

public class BranchServiceImpl implements BranchService {
    private static BranchServiceImpl INSTANCE;
    private final BranchRepository branchRepository = BranchRepositoryImpl.getInstance();
    private BranchServiceImpl() {
    }

    public static BranchServiceImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (BranchServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new BranchServiceImpl();
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
