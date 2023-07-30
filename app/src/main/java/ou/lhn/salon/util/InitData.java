package ou.lhn.salon.util;

import android.app.Application;
import android.content.Context;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Salon;
import ou.lhn.salon.db.model.Service;
import ou.lhn.salon.db.model.Stylist;
import ou.lhn.salon.db.service.Salon_db.SalonSerivce;
import ou.lhn.salon.db.service.Salon_db.SalonServiceImpl;
import ou.lhn.salon.db.service.Service_db.ServiceService;
import ou.lhn.salon.db.service.Service_db.ServiceServiceImpl;
import ou.lhn.salon.db.service.Stylist_db.StylistService;
import ou.lhn.salon.db.service.Stylist_db.StylistServiceImpl;

public class InitData {
    public static void initData(Context context) {
        SalonSerivce salonSerivce = SalonServiceImpl.getInstance(context);
        ServiceService serviceService = ServiceServiceImpl.getInstance(context);
        StylistService stylistService = StylistServiceImpl.getInstance(context);

        Salon salon1 = new Salon(1, "60 Shine", "111 Lê Đại Hành", "30 + 30 Shine", true, null);
        Salon salon2 = new Salon(1, "Ba Râu", "155 Minh Phụng", "Ba Râu", true, null);
        Salon salon3 = new Salon(1, "Hải Lé", "47/8 Nguyễn Tri Phương", "Hải Lé", true, null);

        salonSerivce.addSalon(salon1);
        salonSerivce.addSalon(salon2);
        salonSerivce.addSalon(salon3);

        Service service1 = new Service(1, "Cắt tóc", "Cắt tóc nghệ thuật", 60000, salon1);
        Service service2 = new Service(1, "Cạo râu", "Cắt râu nghệ thuật", 50000, salon1);
        Service service3 = new Service(1, "Chăm sóc da", "Chăm sóc da mặt, cơ thể", 100000, salon1);

        serviceService.addService(service1);
        serviceService.addService(service2);
        serviceService.addService(service3);

        Stylist stylist1 = new Stylist(1, "Johnny Deng", 3, true, salon1, null);
        Stylist stylist2 = new Stylist(1, "Chương Hý", 4, true, salon1, null);
        Stylist stylist3 = new Stylist(1, "Trang Moa", 4, true, salon1, null);

        stylistService.addStylist(stylist1);
        stylistService.addStylist(stylist2);
        stylistService.addStylist(stylist3);
    }
}
