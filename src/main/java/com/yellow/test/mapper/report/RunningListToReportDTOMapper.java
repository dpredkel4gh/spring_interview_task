package com.yellow.test.mapper.report;

import com.yellow.test.constant.NumberConstant;
import com.yellow.test.entity.Running;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.report.ReportDTO;
import com.yellow.test.util.bd.BigDecimalFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class RunningListToReportDTOMapper implements Mapper<List<Running>, ReportDTO> {

    @Override
    public ReportDTO map(List<Running> value) {
        if (value == null || value.isEmpty())
            return null;

        int allTime = NumberConstant.ZERO;
        int allDistance = NumberConstant.ZERO;
        for (Running running : value) {
            allTime += running.getDuration();
            allDistance += running.getDistance();
        }

        BigDecimal bdAs = BigDecimalFactory.create(allDistance).divide(BigDecimalFactory.create(allTime), BigDecimalFactory.MATH_CONTEXT);
        BigDecimal bdAt = BigDecimalFactory.create(allTime).divide(BigDecimalFactory.create(value.size()), BigDecimalFactory.MATH_CONTEXT);
        BigDecimal bdTd = BigDecimalFactory.create(allDistance);
        return ReportDTO.builder()
                .averageSpeed(bdAs)
                .averageTime(bdAt)
                .totalDistance(bdTd)
                .build();
    }
}
