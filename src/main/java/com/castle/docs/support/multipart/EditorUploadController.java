package com.castle.docs.support.multipart;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.castle.plugin.storage.support.FileService;

@Controller
@RequestMapping("/editor")
public class EditorUploadController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(@RequestPart("editormd-image-file") MultipartFile part) {
		String url = fileService.upload(part);

		Map<String, Object> result = new HashMap<>();
		result.put("url", url);
		result.put("success", 1);
		result.put("message", "");
		return result;
	}

}
