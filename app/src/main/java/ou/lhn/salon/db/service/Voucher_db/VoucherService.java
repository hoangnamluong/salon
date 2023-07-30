package ou.lhn.salon.db.service.Voucher_db;

import java.util.ArrayList;
import java.util.Calendar;

import ou.lhn.salon.db.model.Voucher;

public interface VoucherService {
    ArrayList<Voucher> getAllVoucher();
    ArrayList<Voucher> getVoucherListByExpireDate(String expireDateStr);
    Voucher getVoucherById(int voucherId);
    Voucher getVoucherByCode(String code);
    boolean addVoucher(Voucher voucher);
    boolean updateVoucher(Voucher voucher);
    boolean deleteVoucher(int voucherId);
}
