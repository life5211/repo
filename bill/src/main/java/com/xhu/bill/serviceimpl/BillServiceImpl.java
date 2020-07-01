package com.xhu.bill.serviceimpl;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.dao.BillDao;
import com.xhu.bill.service.BillService;
import com.xhu.bill.util.JsonResult;
import com.xhu.bill.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 18:01
 */
@Slf4j
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillDao billDao;

    @Override
    public List<BillBean> findBills(Integer group, int page, Long start, Long end) {

        List<BillBean> list = billDao.find(group, start, end, page, 50);

        return (list);
    }

    @Override
    public int deleteOneById(String id) {
        return 0;
    }

    @Override
    public JsonResult<BillBean> insertOne(BillBean bill) {
        Map<String, String> investor = bill.getInvestor();
        Map<String, String> consumers = bill.getConsumers();
        boolean flag = investor.values().stream().allMatch(StringUtil::isNumberString)
                && consumers.values().stream().allMatch(s -> StringUtils.isBlank(s) || StringUtil.isNumberString(s));
        if (!flag) {
            log.info("illegal input");
            return JsonResult.err("illegal input", 4);
        }
        BigDecimal amount = investor.values().stream().map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
        bill.setAmount(amount.toString());

        BigDecimal consumersAmount = consumers.values().stream().filter(StringUtil::isNumberString).map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
        long blankCount = consumers.values().stream().filter(StringUtils::isBlank).count();
        int compare = amount.compareTo(consumersAmount);
        if (blankCount == 0 && compare == 0) {
            billDao.insertOne(bill);
            return JsonResult.jst(bill);
        }
        if (blankCount > 0 && compare > 0) {
            BigDecimal averageAmount = amount.subtract(consumersAmount)
                    .divide(BigDecimal.valueOf(blankCount), 8, BigDecimal.ROUND_HALF_UP);
            if (averageAmount.toString().endsWith("000000")) {

            }
            consumers.keySet().stream().filter(key -> StringUtils.isBlank(consumers.get(key))).forEach(key -> consumers.put(key, averageAmount.toString()));

            billDao.insertOne(bill);
            return JsonResult.jst(bill);
        }
        log.info("error number input");
        return JsonResult.err("error number input", 4);
    }

    @Override
    public BillBean findById(String id) {
        return null;
    }

    @Override
    public BillBean updateOne(BillBean bill) {
        return null;
    }

}
