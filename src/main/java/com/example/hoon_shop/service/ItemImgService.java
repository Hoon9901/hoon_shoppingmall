package com.example.hoon_shop.service;

import com.example.hoon_shop.entity.ItemImg;
import com.example.hoon_shop.repository.ItemImgRespotiory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRespotiory itemImgRespotiory;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String orgImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(orgImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, orgImgName,
                    itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        // 상품 이미지 정보 저장
        itemImg.updateItemImg(orgImgName, imgName, imgUrl);
        itemImgRespotiory.save(itemImg);
        itemImgRespotiory.save(itemImg);
    }
}
