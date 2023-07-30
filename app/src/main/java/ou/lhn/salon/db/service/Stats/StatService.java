package ou.lhn.salon.db.service.Stats;

import java.util.ArrayList;

import ou.lhn.salon.db.model.RevenueStats;

public interface StatService {
    ArrayList<RevenueStats> getTop5SalonRevenue(int month, int year);
    int getAmountUser();
    int getAmountSalon();
    int getAmountOrder();
}
