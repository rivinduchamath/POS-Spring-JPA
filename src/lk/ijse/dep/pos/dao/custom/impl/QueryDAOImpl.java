package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QueryDAOImpl implements QueryDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public CustomEntity getOrderInfo(int orderId) throws Exception {
        return null;
    }

    @Override
    public List<CustomEntity> getOrdersInfo(String query) throws Exception {

        List<Object[]> resultList = entityManager.createNativeQuery(
                "SELECT o.id, c.cusId , c.name, o.date, od.qty * od.unit_price  FROM Customer c " +
                        "JOIN `order` o ON c.cusId=o.customer_Id " +
                        "JOIN orderdetail od on o.id = od.order_id " +
                        "WHERE o.id LIKE ?1 OR " +
                        "c.cusId LIKE ?1 OR " +
                        "c.name LIKE ?1 OR " +
                        "o.date LIKE ?1 GROUP BY o.id"
        ).setParameter(1, query).getResultList();

        ArrayList<CustomEntity> list = new ArrayList<>();

        for (Object[] clo : resultList) {
            list.add(new CustomEntity((int) clo[0], (String) clo[1], (String) clo[2], (Date) clo[3], (Double) clo[4]));
        }
        return list;
    }
}
