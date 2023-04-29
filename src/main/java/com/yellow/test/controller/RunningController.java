package com.yellow.test.controller;

import com.yellow.test.config.security.additional.TokenUuidExtractor;
import com.yellow.test.model.running.*;
import com.yellow.test.service.running.RunningService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/runnings")
public class RunningController {

    @Autowired
    private RunningService service;

    @PostMapping
    @ApiOperation("Add new running")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", dataType = "string", paramType = "body",
                    value = "Must be in format yyyyMMdd")
    })
    public ResponseEntity<RunningDTO> post(@Valid @RequestBody SaveRunningDTO dto, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);
        dto.setUserUuid(userUuid);

        RunningDTO saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping
    @ApiOperation("Update running")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", dataType = "string", paramType = "body",
                    value = "Must be in format yyyyMMdd")
    })
    public ResponseEntity<RunningDTO> put(@Valid @RequestBody UpdateRunningDTO dto, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);
        dto.setUserUuid(userUuid);
        RunningDTO updated = service.update(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    @ApiOperation("Get list of runnings")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page.")
    })
    public ResponseEntity<Page<RunningDTO>> get(@ApiIgnore Pageable pageable, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);
        GetRunningListDTO dto = GetRunningListDTO.builder()
                .userUuid(userUuid)
                .pageable(pageable)
                .build();
        Page<RunningDTO> page = service.findByUserUuid(dto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RunningDTO> get(@NotBlank @PathVariable("uuid") String uuid, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);
        GetRunningDTO dto = GetRunningDTO.builder()
                .uuid(uuid)
                .userUuid(userUuid)
                .build();
        RunningDTO result = service.findByUuidAndUserUuid(dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<HttpStatus> delete(@NotBlank @PathVariable("uuid") String uuid, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);
        DeleteRunningDTO dto = DeleteRunningDTO.builder()
                .uuid(uuid)
                .userUuid(userUuid)
                .build();
        service.deleteByUuidAndUserUuid(dto);
        return ResponseEntity.ok().build();
    }

}
