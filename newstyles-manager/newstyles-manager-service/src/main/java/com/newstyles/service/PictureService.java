package com.newstyles.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.newstyles.common.utils.PictureResult;

public interface PictureService {
	PictureResult uploadFile(MultipartFile uploadFile)  throws Exception;
	boolean deleteFilesPic(String fileUrl) throws Exception;
}
