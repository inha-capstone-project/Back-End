package com.inha.capstone.service;

import com.inha.capstone.Dto.Token;
import com.inha.capstone.config.BaseException;
import com.inha.capstone.config.BaseResponseStatus;
import com.inha.capstone.domain.JwtTokenProvider;
import com.inha.capstone.domain.User;
import com.inha.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void save(User user){
        if(userRepository.findByUserId(user.getUserId()).isPresent())
            throw new BaseException(BaseResponseStatus.DUPLICATED_USER);

        userRepository.save(user);
    }

    public User findByUserId(String userId) { return userRepository.findByUserId(userId).get();}

    @Transactional
    public Token login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        System.out.println(authenticationToken);

        if(userRepository.findByUserId(memberId).isEmpty())
            throw new BaseException(BaseResponseStatus.NOT_EXIST_USER);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println(authentication);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        Token tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    public List<User> findByUserIdContaining(String keyword) { return userRepository.findByUserIdContaining(keyword);}
    @Transactional
    public void removeUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new BaseException(BaseResponseStatus.NOT_EXIST_USER);
        userRepository.delete(user.get());
    }

    public User findOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new BaseException(BaseResponseStatus.NOT_EXIST_USER);

        return user.get();
    }

}
