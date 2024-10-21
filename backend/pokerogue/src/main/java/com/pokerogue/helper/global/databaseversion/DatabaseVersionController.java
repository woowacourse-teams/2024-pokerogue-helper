package com.pokerogue.helper.global.databaseversion;

import com.pokerogue.helper.util.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatabaseVersionController {

    private final DatabaseVersion databaseVersion;

    @GetMapping("/api/v1/database/version")
    public ApiResponse<DatabaseVersionResponse> databaseVersion() {
        return new ApiResponse<>("데이터베이스 버전 불러오기에 성공했습니다.", new DatabaseVersionResponse(databaseVersion));
    }
}
