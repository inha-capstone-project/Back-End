package com.inha.capstone.Dto;

import com.inha.capstone.domain.FileCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

public class FIleDto {
  /**
   * 파일 메타데이터 저장을 위한 request DTO
   * @FileCategory fileCategory
   * @Long applicationId
   * @Long userId
   * @Long componentId
   */
  @Data
  public static class PostFileRequest {
    @NotBlank
    FileCategory fileCategory;
    @NotBlank
    Long applicationId;
    @NotBlank
    Long userId;
    @NotBlank
    Long componentId;
  }

  /**
   * 파일 저장 후 url 반환을 위한 response DTO
   * @String filePath
   */
  @Data
  public static class PostFileResponse {
    String filePath;
  }

  /**
   * 파일 제거를 위한 filePath 요청 request DTO
   * @String filePath
   */
  @Data
  public static class DeleteFileRequest {
    @NotBlank
    Long applicationId;
    @NotBlank
    Long userId;
    @NotBlank
    Long componentId;
  }

  /**
   * 애플리케이션의 파일 요청에 대한 response DTO
   * @String filePath
   * @Long componentId
   */
  @Data
  @AllArgsConstructor
  public static class GetFileResponse {
    String filePath;
    Long componentId;
  }
}
