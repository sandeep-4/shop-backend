package spring.java.io.shop.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.clouddirectory.model.DeleteObjectResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;

import spring.java.io.shop.configs.AppConfig;
import spring.java.io.shop.tracelogged.EventLogManager;

@Component
public class AmazonS3Util {

	@Autowired
	AppConfig appConfig;

	// Amazon S3 Client
	private AmazonS3 s3Client;
	private String accessKey = "";
	private String secretKey = "";
	String bucketName = "";

	public AmazonS3Util() {

	}

	private void AmazonS3SetUp() {
		if (s3Client == null) {
			AWSCredentials credintials = null;
			try {
				accessKey = appConfig.getValueOfKey("amazons3.accesskey");
				secretKey = appConfig.getValueOfKey("amazons3.secretkey");
				bucketName = appConfig.getValueOfKey("amazons3.bucket");
				credintials = new BasicAWSCredentials(accessKey, secretKey);
				s3Client = new AmazonS3Client(credintials);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean downLoadLocalServer(String targetPath, String keyName, String fileName) {
		try {
			AmazonS3SetUp();
//			EventLogManager.getInstance().info("Print the downlaod of server in local");
			S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
			object.getObjectContent();
			String targetFilePath = targetPath + fileName;
			File targetFile = new File(targetFilePath);
			FileUtils.copyInputStreamToFile(object.getObjectContent(), targetFile);

			// check pdg and make thumnaail
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getUrlDownload(String keyname) {
		String urlString = "";
		try {
			AmazonS3SetUp();
			java.util.Date expiration = new java.util.Date();
			long milliSeconds = expiration.getTime();
			milliSeconds += 3000 * 60 * 60;
			expiration.setTime(milliSeconds);
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(accessKey,
					urlString);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(expiration);

			URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
			urlString = url.toString();

			EventLogManager.getInstance().info("Generating pre-signed URL Amazon=" + urlString);
		} catch (Exception e) {
			EventLogManager.getInstance().error(e.getMessage());
			e.printStackTrace();
		}
		return urlString;
	}

	long partSize = 5 * 1024 * 1024;

	public boolean UploadFile(String filePath, String keyName) {
		try {
			EventLogManager.getInstance().info("Process upload to Amazon key name=" + keyName);
			AmazonS3SetUp();

			List<PartETag> partETags = new ArrayList<PartETag>();

			InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName);
			InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

			File file = new File(filePath);
			long contentLength = file.length();

			try {
				long filePosition = 0;
				for (int i = 1; filePosition < contentLength; i++) {
					long _partSize = Math.min(partSize, (contentLength - filePosition));

					UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(bucketName)
							.withKey(keyName).withUploadId(initResponse.getUploadId()).withPartNumber(i)
							.withFileOffset(filePosition).withFile(file).withPartSize(_partSize);

					partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
					filePosition += _partSize;
				}
				CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, keyName,
						initResponse.getUploadId(), partETags);

				s3Client.completeMultipartUpload(compRequest);
				return true;
			} catch (Exception e) {
				s3Client.abortMultipartUpload(
						new AbortMultipartUploadRequest(bucketName, keyName, initResponse.getUploadId()));
				EventLogManager.getInstance().error(e.getMessage());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			EventLogManager.getInstance().error(e.getMessage());
			return false;
		}
	}

	public boolean deleteFile(String keyName) {
		try {
			AmazonS3SetUp();
			s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
			EventLogManager.getInstance().info("Process delete file from Amazon key name=" + keyName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteFiles(List<String> keyNames) {
		AmazonS3SetUp();
		DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName);
		List<KeyVersion> keys = new ArrayList<KeyVersion>();
		for (String keyItem : keyNames) {
			keys.add(new KeyVersion(keyItem));
		}
		multiObjectDeleteRequest.setKeys(keys);
		try {
			DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
			System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());

		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
