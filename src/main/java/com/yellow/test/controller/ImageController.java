package com.yellow.test.controller;

import com.yellow.test.config.security.additional.TokenUuidExtractor;
import com.yellow.test.constant.WebConstant;
import com.yellow.test.exception.ServiceRuntimeException;
import com.yellow.test.model.image.*;
import com.yellow.test.service.image.ImageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.OutputStream;

@Validated
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping
    public ResponseEntity<ImageDTO> post(@RequestParam("file") MultipartFile file,
                                         @ApiIgnore OAuth2Authentication authentication) throws IOException {
        String userUuid = TokenUuidExtractor.extract(authentication);
        SaveImageDTO dto = SaveImageDTO.builder()
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(file.getBytes())
                .userUuid(userUuid)
                .build();

        ImageDTO saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @ApiOperation("Get list of images")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page.")
    })
    public ResponseEntity<Page<ImageDTO>> get(@ApiIgnore Pageable pageable, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);

        GetImageListDTO dto = GetImageListDTO.builder()
                .userUuid(userUuid)
                .pageable(pageable)
                .build();

        Page<ImageDTO> page = service.findByUserUuid(dto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> get(@NotBlank @PathVariable("uuid") String uuid, @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);

        GetImageDTO dto = GetImageDTO.builder()
                .uuid(uuid)
                .userUuid(userUuid)
                .build();

        DataImageDTO res = service.findByUuidAndUserUuid(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(res.getData().length);
        headers.setContentType(MediaType.valueOf(res.getContentType()));

        return new ResponseEntity<>(res.getData(), headers, HttpStatus.OK);
    }

    @GetMapping("/download/{uuid}")
    public void get(@NotBlank @PathVariable("uuid") String uuid,
                    @ApiIgnore HttpServletResponse response,
                    @ApiIgnore OAuth2Authentication authentication) {
        String userUuid = TokenUuidExtractor.extract(authentication);
        GetImageDTO dto = GetImageDTO.builder()
                .uuid(uuid)
                .userUuid(userUuid)
                .build();

        DataImageDTO result = service.findByUuidAndUserUuid(dto);

        response.setContentType(result.getContentType());
        response.setCharacterEncoding(WebConstant.CHARACTER_ENCODING);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, WebConstant.CONTENT_DISPOSITION + result.getFilename());
        response.setContentLength(result.getData().length);

        try (OutputStream out = response.getOutputStream()) {
            out.write(result.getData(), 0, result.getData().length);
            out.flush();
        } catch (IOException e) {
            throw new ServiceRuntimeException(e.getMessage(), e);
        }
    }

}
