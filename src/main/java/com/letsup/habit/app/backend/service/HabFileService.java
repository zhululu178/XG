package com.letsup.habit.app.backend.service;

import com.letsup.habit.app.backend.base.GlobalException;
import org.springframework.web.multipart.MultipartFile;

public interface HabFileService {
    String urlUpload(String httpUrl) throws GlobalException;
    String upload(MultipartFile file) throws GlobalException;
}
