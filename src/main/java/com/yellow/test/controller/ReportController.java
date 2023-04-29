package com.yellow.test.controller;

import com.yellow.test.config.security.additional.TokenUuidExtractor;
import com.yellow.test.model.report.GetWeekReportDTO;
import com.yellow.test.model.report.ReportDTO;
import com.yellow.test.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/{offset}")
    public ResponseEntity<ReportDTO> create(@NotNull @Min(0) @PathVariable("offset") Integer offset,
                                            @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);

        GetWeekReportDTO dto = GetWeekReportDTO.builder()
                .weekOffset(offset)
                .userUuid(userUuid)
                .build();
        ReportDTO res = service.getReport(dto);
        return ResponseEntity.ok(res);
    }

}
