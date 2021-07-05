package spring.java.io.shop.api.controller.uploadFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.controller.AbstractBaseController;
import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.util.FileUtil;

@RestController
@RequestMapping(APIName.UPLOAD_FILE)
public class UploadFileController extends AbstractBaseController {

	@Value("${application.config.upload.basedir}")
	private String uploadPath;

	@RequestMapping(path = APIName.UPLOAD_FILE, method = RequestMethod.POST)
	public ResponseEntity<APIResponse> uploadFile(HttpServletRequest request,
			@RequestParam("file_name") String fileName, @RequestParam("file") MultipartFile file,
			@RequestParam(value = "name", required = false, defaultValue = "") String uploadName,
			@RequestParam(value = "chunk", required = false) String chunkRequest,
			@RequestParam(value = "chunks", required = false) String totalChunks) throws IOException {

		String filePathDirectory = new File(uploadPath).isAbsolute() ? uploadPath
				: request.getServletContext().getRealPath(uploadPath);
		Integer chunk = 0, chunks = 0, totalChunkIndex;
		if (null != chunkRequest && !chunkRequest.equals("")) {
			chunk = Integer.valueOf(chunkRequest);
		}
		if (null != totalChunks && !totalChunks.equals("")) {
			chunks = Integer.valueOf(totalChunks);
		}
		totalChunkIndex = chunks - 1;
		if (!file.isEmpty()) {
			String filePathName = uploadName;
			String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
			if (filePathName == null || "".equals(filePathName)) {
				filePathName = new Date().getTime() + "." + fileExtension;
			}

			File folder = new File(filePathDirectory);
			if (!folder.exists()) {
				folder.mkdir();
			}
			File destFile = new File(folder, filePathName);
			if (chunk == 0 && destFile.exists()) {
				destFile.delete();
				destFile = new File(folder, filePathName);
			}
			FileUtil.appendFile(file.getInputStream(), destFile);

			if (chunk.equals(totalChunkIndex) || totalChunkIndex == -1) {
				return responseUtil.sucessResponse(filePathName);
			} else {
				throw new ApplicationException(APIStatus.ERR_UPLOAD_FILE);
			}

		} else {
			throw new ApplicationException(APIStatus.ERR_UPLOAD_FILE);

		}
	}
}
