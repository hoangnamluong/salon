package ou.lhn.salon.db.repository.Branch;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Branch;

public interface BranchRepository {
    ArrayList<Branch> getAllBranches();
    ArrayList<Branch> getBranchesBySalonId(int salonId);
    Branch getBranchById(int id);
    boolean addBranch(Branch branch);
    boolean updateBranch(Branch branch);
    boolean deleteBranch(int id);
    boolean softDeleteBranch(int id);
}
