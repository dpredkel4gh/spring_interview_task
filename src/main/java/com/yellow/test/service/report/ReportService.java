package com.yellow.test.service.report;

import com.yellow.test.model.report.GetWeekReportDTO;
import com.yellow.test.model.report.ReportDTO;

public interface ReportService {
    ReportDTO getReport(GetWeekReportDTO dto);
}
