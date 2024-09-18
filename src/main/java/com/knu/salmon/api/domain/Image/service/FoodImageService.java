package com.knu.salmon.api.domain.Image.service;


import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.knu.salmon.api.domain.Image.entity.FoodImage;
import com.knu.salmon.api.domain.Image.repository.FoodImageRepository;
import com.knu.salmon.api.domain.food.entity.Food;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodImageService {

    private final FoodImageRepository foodImageRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;

    private final Storage storage;

    public void uploadToBoardImages(MultipartFile[] files, Food food) {
        for (MultipartFile file : files) {
            String imageUrl = uploadImageToGcs(file);
            FoodImage image = FoodImage.builder()
                    .food(food)
                    .imageUrl(imageUrl)
                    .build();

           // food.getPhotoList().add(photo);
            foodImageRepository.save(image);
        }
    }

    public String uploadImageToGcs(MultipartFile file) {
        if (file.isEmpty()) {
            Random random = new Random();
            int number = random.nextInt(4) + 1;

            return "https://storage.googleapis.com/fromnow-bucket/basic_image_00" + number + ".png";
        }

        String uniqueFileName = createImageName(file);
        BlobId blobId = BlobId.of(bucketName, uniqueFileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        try (WriteChannel writer = storage.writer(blobInfo)) {
            byte[] imageDatas = file.getBytes();
            writer.write(ByteBuffer.wrap(imageDatas));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return createImageUrl(uniqueFileName);
    }

    public String createImageName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return UUID.randomUUID() + "_" + originalFilename;
    }

    public String createImageUrl(String uniqueFileName) {
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, uniqueFileName);
    }
}
