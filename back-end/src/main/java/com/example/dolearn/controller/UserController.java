package com.example.dolearn.controller;

import com.example.dolearn.dto.UserDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.response.ErrorResponse;
import com.example.dolearn.response.SuccessResponse;
import com.example.dolearn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ServletContext servletContext;

    @Value("${file.path}")
    String filePath;

    @PostMapping
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto userDto){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.signup(userDto)), HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.EMAIL_DUPLICATION), HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto reqUserDto) {
        try {
            UserDto userDto = userService.login(reqUserDto);
            String checkEmail = userDto.getEmail();
            String refreshToken = jwtTokenProvider.createRefreshToken(checkEmail);
            String accessToken = jwtTokenProvider.createAccessToken(refreshToken);
            return new ResponseEntity<>(new SuccessResponse(userService.updateToken(userDto, refreshToken, accessToken)), HttpStatus.OK);
        } catch (CustomException e){
            if (e.getErrorCode() == ErrorCode.INVALID_PASSWORD){
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_PASSWORD), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/access")
    public ResponseEntity<?> getAccessToken(@RequestHeader(value = "Authentication") String token) {
        try{
            String accessToken = jwtTokenProvider.createAccessToken(token);
            return new ResponseEntity<>(new SuccessResponse(accessToken), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_TOKEN), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.logout(id)), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserDto reqUserDto) {
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.updateInfo(reqUserDto)), HttpStatus.OK);
        } catch (CustomException e) {
            if (e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED){
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-img/{id}")
    public ResponseEntity<?> uploadProfileImg(HttpServletRequest request, @PathVariable("id") Long id, @RequestPart(value="profileImg", required = false) MultipartFile img) throws IOException {
        try{
            // 기존 파일 삭제
            UserDto userDto = userService.getInfo(id);
            if(userDto.getImgPath() != "" && !userDto.getImgPath().equals("")){
                logger.info("before: "+userDto.getImgPath());
                String prevFileName = userDto.getImgPath();
                new File(prevFileName).delete();
            }

            UserDto result;
            // 기본 이미지로 초기화
            if(img == null){
                result = userService.updateImgInfo(id, "", "");
                return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
            }

            // 저장될 파일 명명
            String originalFileName = img.getOriginalFilename();
            String saveFileName = System.nanoTime()+originalFileName.substring(originalFileName.lastIndexOf('.'));

            // 파일 저장
            byte[] bytes = img.getBytes();
            Path path = Paths.get(filePath + saveFileName);

            File folder = new File(filePath);
            if(!folder.exists())
                folder.mkdirs();
            Files.write(path, bytes);

            String contextPath = servletContext.getContextPath();
            String imgUrl = contextPath+"/profile-img/"+saveFileName;

            result = userService.updateImgInfo(id, path.toString(), imgUrl);

            return new ResponseEntity<>(new SuccessResponse(result), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile-img/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new FileSystemResource(filePath + filename);
        String mimeType = null;
        try {
            mimeType = Files.probeContentType(file.getFile().toPath());
        } catch (IOException e) {
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(file);
    }
    
    @PostMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable("email") @Email(message = "이메일 형식에 맞지 않습니다.") String email){
        try{
            userService.checkEmail(email);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.EMAIL_DUPLICATION), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new SuccessResponse(SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInfo(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getInfo(id)), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-id")
    public ResponseEntity<?> getAllId(){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getAllId()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/summary-info/{id}")
    public ResponseEntity<?> getSummaryInfo(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getSummaryInfo(id)), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try{
            userService.delete(id);
            return new ResponseEntity<>(new SuccessResponse(SUCCESS), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/point")
    public ResponseEntity<?> updatePoint(@RequestBody Map<String, Object> params){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.updatePoint(params)), HttpStatus.OK);
        } catch (CustomException e){
            if(e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/request-lecture/{id}")
    public ResponseEntity<?> getRequestLecture(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getRequestLecture(id)), HttpStatus.OK);
        } catch (CustomException e){
            if(e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/request-lecture/{id}/host")
    public ResponseEntity<?> getRequestLectureByHost(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getRequestLectureByHost(id)), HttpStatus.OK);
        } catch (CustomException e){
            if(e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/request-lecture/{id}/instructor")
    public ResponseEntity<?> getRequestLectureByInst(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getRequestLectureByInst(id)), HttpStatus.OK);
        } catch (CustomException e){
            if(e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/request-lecture/{id}/student")
    public ResponseEntity<?> getRequestLectureByStud(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getRequestLectureByStud(id)), HttpStatus.OK);
        } catch (CustomException e){
            if(e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fixed-lecture/{id}")
    public ResponseEntity<?> getFixedLecture(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getFixedLecture(id)), HttpStatus.OK);
        } catch (CustomException e){
            if(e.getErrorCode().getHttpStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT), HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_USER), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sort-point")
    public ResponseEntity<?> getAllSortedByPoint(){
        try{
            return new ResponseEntity<>(new SuccessResponse(userService.getAllSortByPoint()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
