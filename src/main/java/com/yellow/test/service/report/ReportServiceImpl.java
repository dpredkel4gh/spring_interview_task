package com.yellow.test.service.report;

import com.yellow.test.entity.Running;
import com.yellow.test.mapper.Mapper;
import com.yellow.test.model.report.GetWeekReportDTO;
import com.yellow.test.model.report.ReportDTO;
import com.yellow.test.model.report.WeekLocalDateBorders;
import com.yellow.test.repository.RunningRepository;
import com.yellow.test.util.ld.LocalDateFormatter;
import com.yellow.test.util.ld.WeekBordersResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private RunningRepository runningRepository;

    @Autowired
    private Mapper<List<Running>, ReportDTO> mapper;

    @Override
    @Transactional(readOnly = true)
    public ReportDTO getReport(GetWeekReportDTO dto) {
        WeekLocalDateBorders borders = WeekBordersResolver.getByOffset(dto.getWeekOffset());
        List<Running> list = runningRepository.findByDateIntervalAndUserUuid(borders.getBegin(), borders.getEnd(), dto.getUserUuid());

        ReportDTO report = list.isEmpty() ? new ReportDTO() : mapper.map(list);
        report.setBegin(LocalDateFormatter.format(borders.getBegin()));
        report.setEnd(LocalDateFormatter.format(borders.getEnd()));
        return report;
    }
}
