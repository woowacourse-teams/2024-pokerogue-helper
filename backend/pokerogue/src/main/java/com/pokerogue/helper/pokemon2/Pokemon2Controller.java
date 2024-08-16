package com.pokerogue.helper.pokemon2;


import com.pokerogue.external.s3.client.S3ImageClient;
import com.pokerogue.helper.util.dto.ApiResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RestController
@RequiredArgsConstructor
public class Pokemon2Controller {

    private final Pokemon2Service pokemon2Service;

    @GetMapping("/api/v1/pokemons2")
    public ApiResponse<List<Map<Object, Object>>> findAll() {
        return new ApiResponse<>("포켓몬 리스트 불러오기에 성공했습니다.", pokemon2Service.findAll());
    }

    @GetMapping("/api/v1/pokemon2/{stringId}")
    public ApiResponse<Map<Object, Object>> findAll(@PathVariable("stringId") String stringId) {
        return new ApiResponse<>("포켓몬 정보 불러오기에 성공했습니다.", pokemon2Service.findById(stringId));
    }
}
