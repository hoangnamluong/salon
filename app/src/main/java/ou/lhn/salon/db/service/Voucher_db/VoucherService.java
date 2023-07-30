package ou.lhn.salon.db.service.Voucher_db;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Voucher;

public interface VoucherService {
    ArrayList<Voucher> getAllVoucher();
    Voucher getVoucherByCode(String code);
    Voucher getVoucherById(int voucherId);
    boolean addVoucher(Voucher voucher);
    boolean updateVoucher(Voucher voucher);
    boolean deleteVoucher(int voucherId);
}
