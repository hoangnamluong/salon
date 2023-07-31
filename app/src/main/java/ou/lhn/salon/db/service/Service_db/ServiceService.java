package ou.lhn.salon.db.service.Service_db;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Service;

public interface ServiceService {
    ArrayList<Service> getAllServices();
    ArrayList<Service> getAllServicesBySalonId(int salonId);
    ArrayList<Service> getServiceListByName(String name);
    ArrayList<Service> getServiceByAppointmentId(int appointmentId);
    Service getServiceById(int serviceId);
    boolean addService(Service service);
    boolean updateService(Service service);
    boolean deleteService(int serviceId);
}
