package com.xhu.bill.serviceimpl;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.dao.BillDao;
import com.xhu.bill.mapper.GroupMapper;
import com.xhu.bill.service.BillService;
import com.xhu.bill.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 18:01
 */
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillDao billDao;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public JsonResult<List<BillBean>> findBills(Integer group, int page, Long start, Long end) {

        List<BillBean> list = billDao.find(group, start, end, page, 50);

        return new JsonResult<>(list);
    }

    @Override
    public JsonResult deleteOneById(String id) {
        return null;
    }

    @Override
    public JsonResult insertOne(BillBean bill) {
        return null;
    }

    @Override
    public JsonResult<BillBean> findById(String id) {
        return null;
    }

    @Override
    public JsonResult updateOne(BillBean bill) {
        return null;
    }

//    private List<BillVO> caseTo(List<BillBean> bills, List<String> users) {
//        List<BillVO> list = new ArrayList<>();
//        bills.forEach(bill -> {
//            BillVO billVo = new BillVO().setDesc(bill.getDesc()).setId(bill.getId())
//                    .setRecordTime(bill.getRecordTime()).setSpeedTime(bill.getSpeedTime());
//
//            List<Map<String, String>> amount = new LinkedList<>();
//            users.forEach(user -> amount.add(JcfUtil.ofSingleMap(user, "")));
//
//            list.add(billVo);
//        });
//        return list;
//    }
}
