package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.base.BaseController;
import com.letsup.habit.app.backend.base.GlobalException;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.service.HabFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(description = "文件管理")
@RequestMapping("/app/file")
public class FileApp extends BaseController {
    @Autowired
    private HabFileService habFileService;

    @ApiOperation(value = "上传")
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestBody MultipartFile file) {
        try {
            return getResponse(ApiResultConstants.SUCCESS, "", habFileService.upload(file));
        } catch (GlobalException g) {
            return getResponse(g);
        }
    }
}
