package com.ijse.gdse.carwash.bo;

import com.ijse.gdse.carwash.bo.custom.impl.*;
import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.dao.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory==null?boFactory = new BOFactory():boFactory;
    }

    public enum BOType{
        CUSTOMER, BOOKING, EMPLOYEE, PAYMENT1, PAYMENT2, PRODUCT, SERVICETYPE, SUPPLIER, VEHICLE, BATCH
    }

    public SuperBO getBO(BOFactory.BOType boType) {
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case BATCH:
                return new BatchBOImpl();
            case BOOKING:
                return new BookingBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PAYMENT1:
                return new Payment1BOImpl();
            case PAYMENT2:
                return new Payment2BOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SERVICETYPE:
                return new ServiceTypeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            default:
                return null;
        }
    }
}
